package com.locuslink.logic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.Textract.AwsTextractBlockDTO;
import com.locuslink.dto.Textract.Relationship;

@Service
public class AzureNerLogic {
	
	private static final Logger logger = Logger.getLogger(AzureNerLogic.class);
	
	@Autowired
    private SecurityContextManager securityContextManager;
	
	// TODO: Create a constructor to determine which model is being used? Need to context switch for MTRs, nameplates, etc.
	@Value("${azure.language.mtr-ner.endpoint}")
	private String endpoint;
	
	@Value("${azure.language.mtr-ner.accessKey}")
	private String key;
	
	@Value("${azure.language.apiVersion}")
	private String apiVersion;
	
	@Value("${azure.language.languageCode}")
	private String languageCode;
	
	@Value("${azure.language.mtr-ner.projectName}")
	private String projectName;
	
	@Value("${azure.language.mtr-ner.deployedModelName}")
	private String deployedModelName;
	
	
	public JSONObject processAzureNER(JsonNode ocrResults) {		
		logger.debug("Starting processAzureNER() " );
		
		List<String> documentPages = new ArrayList<String>();
		try {
			documentPages  = generateDocumentPages(ocrResults);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		logger.debug("  Document pages generated. Submitting documents to Azure NER model.." + documentPages.size());
		
		
		JSONObject nerResults = null;
		try {
			nerResults = performNER(projectName, deployedModelName, documentPages);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String status =  nerResults == null ? "failed" : "succeeded";
		logger.debug("The NER job " + status);
		
		return nerResults;
	}
	
		
	private JSONObject performNER(String projectName, String deployedModelName, List<String> textDocuments) {

        // Initialize HTTP client for REST operations
        HttpClient client = HttpClient.newHttpClient();

        // POST request
        String getRequestURL = postNERTask(client, projectName, deployedModelName, textDocuments); // returns GET URL

        if (getRequestURL == null) {
        	logger.debug("POST request failed.");
            return null;
        }

        // GET request
        return getNERTask(client, getRequestURL);
    }
	
	
	private String postNERTask(HttpClient client, String projectName, String deployedModelName, List<String> textDocuments) {

        try {
            // Create POST request to Azure model
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("%s/language/analyze-text/jobs?api-version=%s".formatted(endpoint, apiVersion)))
                    .header("Ocp-Apim-Subscription-Key", key)
                    .POST(HttpRequest.BodyPublishers.ofString(Objects.requireNonNull(generatePostRequest(projectName, deployedModelName, textDocuments))))
                    .build();

            // Use the client to send the request
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            if (response.statusCode() == 202) { // If successful
                return response.headers().firstValue("operation-location").get(); // Return GET URL
            } else { // If unsuccessful
            	logger.debug("Error: HTTP Status Code " + response.statusCode());
            }
        } catch (InterruptedException | IOException e) {
        	logger.debug(e.getMessage());
            //System.exit(1);
        }
        return null;
    }
	
	private JSONObject getNERTask(HttpClient client, String jobURL) {

        boolean finished = false;
        JSONParser parser = new JSONParser();
        JSONObject responseBody;
        int index = 0;
        String status;

        try {
            // create a GET request
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(jobURL))
                    .header("Ocp-Apim-Subscription-Key", key)
                    .GET()
                    .build();
            
            HttpResponse<String> response = null;
            JSONObject taskStatus = null;
            
            do {
                // Use the client to send the request
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                responseBody = (JSONObject) parser.parse(response.body()); // Transform response body to JSON
                status = (String) responseBody.get("status");

                switch (status) {
                    case "succeeded", "completed" -> {

                        taskStatus = (JSONObject) responseBody.get("tasks");
                        if ((long) taskStatus.get("failed") > 0) {
                        	logger.debug(taskStatus.get("completed") + " task(s) completed. " + taskStatus.get("failed") +
                                    "/" + taskStatus.get("total") + " tasks failed.");
                        } else {
                        	logger.debug("All tasks completed successfully.");
                        }

                        return parseAzureResponse(responseBody); // Return NER results if successful/complete
                    }
                    case "failed" -> {
                    	logger.debug("Error: Job ID " + responseBody.get("jobId") + " failed.");
                    	logger.debug("Job Data: " + responseBody.get("tasks"));
                        finished = true;
                    }
                    case "canceled" -> {
                    	logger.debug("Error: Job ID " + responseBody.get("jobId") + " was canceled.");
                        finished = true;
                    }
                    default -> {
                    	logger.debug(index + " status is: " + status);
                        Thread.sleep(3000);
                        index++;
                    }
                }
            } while (!finished);

            logger.debug("GET request failed.");
            
        } catch (InterruptedException | IOException | ParseException e) {
        	logger.debug(e.getMessage());
            //System.exit(1);
        }
        return null; // Return null if NER job failed or was canceled
    }
	
	@SuppressWarnings("unchecked")
	private String generatePostRequest(String projectName, String deployedModelName, List<String> textDocuments) {

        if (textDocuments.isEmpty()) { // If the document list is empty
        	logger.debug("Error: Document List Is Empty");
            return null;
        } else {
        	
            // Body root
            JSONObject body = new JSONObject();
            JSONObject analysisInput = new JSONObject();
            JSONArray documents = new JSONArray();

            JSONObject document = new JSONObject();
            for (int i = 0; i < textDocuments.size(); i++) { // Add documents to document list
                document = new JSONObject();
                document.put("id", i);
                document.put("language", languageCode);
                document.put("text", textDocuments.get(i));
                documents.add(document);
            }
            analysisInput.put("documents", documents); // Add document list to analysisInput object

            // Task list array
            JSONArray taskList = new JSONArray();

            // Task object
            JSONObject task = new JSONObject();
            task.put("kind", "CustomEntityRecognition");
            task.put("taskName", "Entity Recognition");

            // Parameters object
            JSONObject parameters = new JSONObject();
            parameters.put("projectName", projectName);
            parameters.put("deploymentName", deployedModelName);

            task.put("parameters", parameters); // Put parameters into tasks
            taskList.add(task); // Add task to task list

            // Putting it all together
            body.put("displayName", "Extracting entities");
            body.put("analysisInput", analysisInput);
            body.put("tasks", taskList);

            return body.toJSONString();

        }
    }
	
	private JSONObject parseAzureResponse(JSONObject responseBody) {

        // Unpack "tasks" key -- dictionary
        JSONObject tasks = (JSONObject) responseBody.get("tasks");

        // Unpack "items" key -- array
        JSONArray items = (JSONArray) tasks.get("items");
        JSONObject task = (JSONObject) items.get(0); // Unpack "Recognize Entities" task

        // Unpack NER results
        /*
        returns a JSON object in the following form:
        {
            "documents": [
                {
                    "entities": [
                        {
                          "category": <String> (entity type),
                          "confidenceScore": <Float> (percentage, 2 decimal digits),
                          "length": <Integer> (entity text length),
                          "offset": <Integer> (entity text location within document),
                          "text": <String> (entity text)
                        }
                    ]
                    "id": <String> (id associated with the document -- assigned in generatePostRequest method)
                    "warnings": [<String?>]
                }
            ],
            "errors": [<String?>],
            "modelVersion": <String> (date generated by Azure AI Language service)
        }
         */
        return (JSONObject) task.get("results");
    }

	private static List<String> generateDocumentPages(JsonNode textractResults) {

        ArrayList<String> documentList = new ArrayList<>();

        for (int i = 1; i <= textractResults.size(); i++) {
            String document = processTextractToText(textractResults, i);
            documentList.add(document);
        }

        return documentList;
    }

    private static String processTextractToText(JsonNode textractResults, int pageNumber) {

    	logger.debug(" Starting  processTextractToText()");
    	
    	// C.Sparks   6-20-2024
        ObjectMapper mapper = new ObjectMapper()
        	.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        
        // Initialize all BlockType lists
        ArrayList<AwsTextractBlockDTO> words = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> lines = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> cells = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> tables = new ArrayList<>();

        ArrayNode blocksNode = (ArrayNode) textractResults.get(String.valueOf(pageNumber)); // Blocks Node

        AwsTextractBlockDTO block = null;
        for (JsonNode blockNode : blocksNode) {

            try {
            	// C.Sparks 6-20-2024 
            	String json  = mapper.writeValueAsString(blockNode);
            	block = mapper.readValue(json,AwsTextractBlockDTO.class );            	

            	switch (block.getBlockType()) {
                    case "WORD" -> words.add(block);
                    case "LINE" -> lines.add(block);
                    case "CELL", "MERGED_CELL" -> cells.add(block);
                    case "TABLE" -> tables.add(block);
                }
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
        }

        // Make tables list immutable (06-04-2024 I. Summers - may be unnecessary)
        List<AwsTextractBlockDTO> tables_reference = Collections.unmodifiableList(tables);

        return generateText(words, lines, cells, tables_reference);
    }

    private static String generateText(List<AwsTextractBlockDTO> words, List<AwsTextractBlockDTO> lines, List<AwsTextractBlockDTO> cells, List<AwsTextractBlockDTO> tables) {

    	logger.debug(" Starting  generateText()");
    	
        StringJoiner joiner = new StringJoiner(" ");    // Initialize StringJoiner for document text
        ArrayList<String> ignoredCells = new ArrayList<>(); // Initialize ignoredCells id list
        String ignoredLine = null; // Initialize ignoredLine id String
        int lineCounter = 0;    // Initialize counter to decide how long a word should be ignored
        String lastBlockType = null;   // Initialize String for the BlockType of the last text String added to joiner

        AwsTextractBlockDTO lineParent = null;
        AwsTextractBlockDTO cellParent = null;
        		
        for (AwsTextractBlockDTO word : words) {  // Iterate through all WORDs

        	logger.debug(" Processing a WORD ->: " +  word.getText() );
        	if (word.getText().equalsIgnoreCase("heat")) {
        		logger.debug(" Has a problem  ->: " +  word.toString());
        	}
        	
            lineParent = findParentBlock(word, lines);   // Initialize LINE parent AwsTextractBlockDTO for WORD AwsTextractBlockDTO
            cellParent = findParentBlock(word, cells);   // Initialize CELL parent AwsTextractBlockDTO for WORD AwsTextractBlockDTO

            // TODO: Refactor for a faster way to iterate through LINEs, CELLs (and TABLEs?). WORD and TABLEs need to
            //  maintain order

            if (cellParent != null) {   // If WORD is contained in a CELL

                if (!ignoredCells.contains(cellParent.getId())) {

                    // Initialize TABLE number for CELL AwsTextractBlockDTO
                    int tableParent = tables.indexOf(findParentBlock(cellParent, tables)) + 1;

                    if (cellContainsLine(cellParent, lineParent)) { // If CELL contains entire LINE

                        if (lastBlockType == null) {  // Don't insert new line
                            joiner.add("\"%s\" is found in a cell (row %d, column %d) in table %d."
                                    .formatted(lineParent.getText(),
                                            cellParent.getRowIndex(),
                                            cellParent.getColumnIndex(),
                                            tableParent));
                        } else {    // Insert new line
                            joiner.add("\n\"%s\" is found in a cell (row %d, column %d) in table %d."
                                    .formatted(lineParent.getText(),
                                            cellParent.getRowIndex(),
                                            cellParent.getColumnIndex(),
                                            tableParent));
                        }

                    } else { // If CELL doesn't contain entire LINE

                        if (lastBlockType == null) {  // Don't insert new line
                            joiner.add("\"%s\" belongs to \"%s\" and is found in a cell (row %d, column %d) in table %d."
                                    .formatted(word.getText(),
                                            lineParent.getText(),
                                            cellParent.getRowIndex(),
                                            cellParent.getColumnIndex(),
                                            tableParent));
                        } else {    // Insert new line
                            joiner.add("\n\"%s\" belongs to \"%s\" and is found in a cell (row %d, column %d) in table %d."
                                    .formatted(word.getText(),
                                            lineParent.getText(),
                                            cellParent.getRowIndex(),
                                            cellParent.getColumnIndex(),
                                            tableParent));
                        }
                    }

                    ignoredCells.add(cellParent.getId());  // Add cell to ignoredCells list
                    lastBlockType = "cell"; // Overwrite lastBlockType String
                }

            } else {    // If WORD is not contained in a CELL

                if (lineParent.getId().equals(ignoredLine)) {  // If LINE is the current ignored LINE

                    if (lineCounter > 0) {  // If there are still WORDs to iterate through
                        lineCounter--;
                    } else {
                        ignoredLine = null; // Clear ignoredLine String on the final WORD
                    }
                } else {    // If LINE is not the currently ignored LINE

                    if (matchesLastBlockType("line", lastBlockType)) {  // Don't insert new line
                        joiner.add(lineParent.getText());
                    } else {    // Insert new line
                        joiner.add("\n%s".formatted(lineParent.getText()));
                    }

                    Iterator<Relationship> iterator = lineParent.getRelationships().iterator();

                    while (iterator.hasNext()) {
                        Relationship relationship = iterator.next();
                        if (relationship.getType().equals("CHILD")) {
                            lineCounter = relationship.getIds().size() - 2;    // Set lineCounter to LINE size, minus 2
                        }
                    }

                    if (lineCounter > -1) {
                        ignoredLine = lineParent.getId();  // Only set ignoredLine if there are more than two words
                    }

                    lastBlockType = "line"; // Overwrite lastBlockType String
                }
            }
        }

        logger.debug(" Finished generateText(). ");
        
        return joiner.toString();
    }

    private static AwsTextractBlockDTO findParentBlock(AwsTextractBlockDTO childBlock, List<AwsTextractBlockDTO> parentList) {

        AwsTextractBlockDTO parentBlock = null;

        if (childBlock != null) {
        	
        	Iterator<Relationship> iterator = null;
        	Relationship relationship = null;
        			 
            for (AwsTextractBlockDTO block : parentList) { // Iterate through AwsTextractBlockDTO list, searching for parent AwsTextractBlockDTO
            	
            	if (block.hasRelationships()) {
	                iterator = block.getRelationships().iterator();	
	                while (iterator.hasNext()) {
	                    relationship = iterator.next();
	                    if (relationship.getType().equals("CHILD")) {	
	                        if (relationship.getIds().contains(childBlock.getId())) {
	                            parentBlock = block;  // Assign lineParent to LINE that contains WORD
	                        }
	                    }
	                }
            	}
            }
        }

        return parentBlock;
    }

    
    private static boolean cellContainsLine(AwsTextractBlockDTO cell, AwsTextractBlockDTO line) {

        Iterator<Relationship> iterator = cell.getRelationships().iterator();
        List<String> cellIds = null;
        List<String> lineIds = null;

        while (iterator.hasNext()) {
            Relationship relationship = iterator.next();
            if (relationship.getType().equals("CHILD")) {           
                cellIds = relationship.getIds();
            }
        }

        iterator = line.getRelationships().iterator();
        while (iterator.hasNext()) {
            Relationship relationship = iterator.next();

            if (relationship.getType().equals("CHILD")) {
                lineIds = relationship.getIds();
            }
        }

        try {
            return cellIds.containsAll(lineIds);
        } catch (NullPointerException e) {
        	logger.debug(e.getMessage());
            return false;
        }
    }

    private static boolean matchesLastBlockType(String currentBlockType, String previousBlockType) {

        if (previousBlockType == null || previousBlockType.equals(currentBlockType)) {
            return true;
        } else {
            return false;
        }
    }
}
