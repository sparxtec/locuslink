package com.locuslink.logic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	

	public JSONObject process_2(JsonNode ocrResults) {
		
		logger.debug("Starting process_2() " );


		return processAzureNER(ocrResults);
	}
	
	private JSONObject processAzureNER(JsonNode ocrResults) {
		
		List<String> documentPages = generateDocumentPages(ocrResults);
		System.out.printf("%d document pages generated. Submitting documents to Azure NER model...%n", documentPages.size());
		
		JSONObject nerResults = performNER(projectName, deployedModelName, documentPages);
		String status =  nerResults == null ? "failed" : "succeeded";
		System.out.println("The NER job " + status);
		
		return nerResults;
	}
	
	private JSONObject performNER(String projectName, String deployedModelName, List<String> textDocuments) {

        // Initialize HTTP client for REST operations
        HttpClient client = HttpClient.newHttpClient();

        // POST request
        String getRequestURL = postNERTask(client, projectName, deployedModelName, textDocuments); // returns GET URL

        if (getRequestURL == null) {

            System.out.println("POST request failed.");
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
                System.out.println("Error: HTTP Status Code " + response.statusCode());
            }
        } catch (InterruptedException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
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
            
            do {
                // Use the client to send the request
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                responseBody = (JSONObject) parser.parse(response.body()); // Transform response body to JSON
                status = (String) responseBody.get("status");

                switch (status) {
                    case "succeeded", "completed" -> {

                        JSONObject taskStatus = (JSONObject) responseBody.get("tasks");
                        if ((long) taskStatus.get("failed") > 0) {
                            System.out.println(taskStatus.get("completed") + " task(s) completed. " + taskStatus.get("failed") +
                                    "/" + taskStatus.get("total") + " tasks failed.");
                        } else {
                            System.out.println("All tasks completed successfully.");
                        }

                        return parseAzureResponse(responseBody); // Return NER results if successful/complete
                    }
                    case "failed" -> {

                        System.out.println("Error: Job ID " + responseBody.get("jobId") + " failed.");
                        System.out.println("Job Data: " + responseBody.get("tasks"));
                        finished = true;
                    }
                    case "canceled" -> {

                        System.out.println("Error: Job ID " + responseBody.get("jobId") + " was canceled.");
                        finished = true;
                    }
                    default -> {
                        System.out.println(index + " status is: " + status);
                        Thread.sleep(3000);
                        index++;
                    }
                }
            } while (!finished);

            System.out.println("GET request failed.");
        } catch (InterruptedException | IOException | ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null; // Return null if NER job failed or was canceled
    }
	
	private String generatePostRequest(String projectName, String deployedModelName, List<String> textDocuments) {

        if (textDocuments.isEmpty()) { // If the document list is empty
            System.out.println("Error: Document List Is Empty");
            return null;
        } else { // Otherwise
            // Body root
            JSONObject body = new JSONObject();

            // Analysis input object
            JSONObject analysisInput = new JSONObject();

            // Initialize Document list
            JSONArray documents = new JSONArray();

            for (int i = 0; i < textDocuments.size(); i++) { // Add documents to document list
                JSONObject document = new JSONObject();
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

        ObjectMapper mapper = new ObjectMapper(); // Initialize ObjectMapper

        // Initialize all BlockType lists
        ArrayList<AwsTextractBlockDTO> words = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> lines = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> cells = new ArrayList<>();
        ArrayList<AwsTextractBlockDTO> tables = new ArrayList<>();

        ArrayNode blocksNode = (ArrayNode) textractResults.get(String.valueOf(pageNumber)); // Blocks Node

        for (JsonNode blockNode : blocksNode) {

            try {
                // Convert blockNode to AwsTextractBlockDTO object
                AwsTextractBlockDTO block = mapper.treeToValue(blockNode, AwsTextractBlockDTO.class);

                switch (block.blockTypeAsString()) {
                    case "WORD" -> words.add(block);
                    case "LINE" -> lines.add(block);
                    case "CELL", "MERGED_CELL" -> cells.add(block);
                    case "TABLE" -> tables.add(block);
                }

            } catch (JsonProcessingException e) {
                System.err.println(e.getMessage());
            }
        }

        // Make tables list immutable (06-04-2024 I. Summers - may be unnecessary)
        List<AwsTextractBlockDTO> tables_reference = Collections.unmodifiableList(tables);

        return generateText(words, lines, cells, tables_reference);
    }

    private static String generateText(List<AwsTextractBlockDTO> words, List<AwsTextractBlockDTO> lines, List<AwsTextractBlockDTO> cells, List<AwsTextractBlockDTO> tables) {

        StringJoiner joiner = new StringJoiner(" ");    // Initialize StringJoiner for document text
        ArrayList<String> ignoredCells = new ArrayList<>(); // Initialize ignoredCells id list
        String ignoredLine = null; // Initialize ignoredLine id String
        int lineCounter = 0;    // Initialize counter to decide how long a word should be ignored
        String lastBlockType = null;   // Initialize String for the BlockType of the last text String added to joiner

        for (AwsTextractBlockDTO word : words) {  // Iterate through all WORDs

            AwsTextractBlockDTO lineParent = findParentBlock(word, lines);   // Initialize LINE parent AwsTextractBlockDTO for WORD AwsTextractBlockDTO
            AwsTextractBlockDTO cellParent = findParentBlock(word, cells);   // Initialize CELL parent AwsTextractBlockDTO for WORD AwsTextractBlockDTO

            // TODO: Refactor for a faster way to iterate through LINEs, CELLs (and TABLEs?). WORD and TABLEs need to
            //  maintain order

            if (cellParent != null) {   // If WORD is contained in a CELL

                if (!ignoredCells.contains(cellParent.id())) {

                    // Initialize TABLE number for CELL AwsTextractBlockDTO
                    int tableParent = tables.indexOf(findParentBlock(cellParent, tables)) + 1;

                    if (cellContainsLine(cellParent, lineParent)) { // If CELL contains entire LINE

                        if (lastBlockType == null) {  // Don't insert new line
                            joiner.add("\"%s\" is found in a cell (row %d, column %d) in table %d."
                                    .formatted(lineParent.text(),
                                            cellParent.rowIndex(),
                                            cellParent.columnIndex(),
                                            tableParent));
                        } else {    // Insert new line
                            joiner.add("\n\"%s\" is found in a cell (row %d, column %d) in table %d."
                                    .formatted(lineParent.text(),
                                            cellParent.rowIndex(),
                                            cellParent.columnIndex(),
                                            tableParent));
                        }

                    } else { // If CELL doesn't contain entire LINE

                        if (lastBlockType == null) {  // Don't insert new line
                            joiner.add("\"%s\" belongs to \"%s\" and is found in a cell (row %d, column %d) in table %d."
                                    .formatted(word.text(),
                                            lineParent.text(),
                                            cellParent.rowIndex(),
                                            cellParent.columnIndex(),
                                            tableParent));
                        } else {    // Insert new line
                            joiner.add("\n\"%s\" belongs to \"%s\" and is found in a cell (row %d, column %d) in table %d."
                                    .formatted(word.text(),
                                            lineParent.text(),
                                            cellParent.rowIndex(),
                                            cellParent.columnIndex(),
                                            tableParent));
                        }
                    }

                    ignoredCells.add(cellParent.id());  // Add cell to ignoredCells list
                    lastBlockType = "cell"; // Overwrite lastBlockType String
                }

            } else {    // If WORD is not contained in a CELL

                if (lineParent.id().equals(ignoredLine)) {  // If LINE is the current ignored LINE

                    if (lineCounter > 0) {  // If there are still WORDs to iterate through
                        lineCounter--;
                    } else {
                        ignoredLine = null; // Clear ignoredLine String on the final WORD
                    }
                } else {    // If LINE is not the currently ignored LINE

                    if (matchesLastBlockType("line", lastBlockType)) {  // Don't insert new line
                        joiner.add(lineParent.text());
                    } else {    // Insert new line
                        joiner.add("\n%s".formatted(lineParent.text()));
                    }

                    Iterator<Relationship> iterator = lineParent.relationships().iterator();

                    while (iterator.hasNext()) {
                        Relationship relationship = iterator.next();

                        if (relationship.typeAsString().equals("CHILD")) {

                            lineCounter = relationship.ids().size() - 2;    // Set lineCounter to LINE size, minus 2
                        }
                    }

                    if (lineCounter > -1) {
                        ignoredLine = lineParent.id();  // Only set ignoredLine if there are more than two words
                    }

                    lastBlockType = "line"; // Overwrite lastBlockType String
                }
            }
        }

        return joiner.toString();
    }

    private static AwsTextractBlockDTO findParentBlock(AwsTextractBlockDTO childBlock, List<AwsTextractBlockDTO> parentList) {

        AwsTextractBlockDTO parentBlock = null;

        if (childBlock != null) {
            for (AwsTextractBlockDTO block : parentList) { // Iterate through AwsTextractBlockDTO list, searching for parent AwsTextractBlockDTO
            	if (block.hasRelationships()) {

	                Iterator<Relationship> iterator = block.relationships().iterator();
	
	                while (iterator.hasNext()) {
	                    Relationship relationship = iterator.next();
	
	                    if (relationship.typeAsString().equals("CHILD")) {
	
	                        if (relationship.ids().contains(childBlock.id())) {
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

        Iterator<Relationship> iterator = cell.relationships().iterator();
        List<String> cellIds = null;
        List<String> lineIds = null;

        while (iterator.hasNext()) {
            Relationship relationship = iterator.next();

            if (relationship.typeAsString().equals("CHILD")) {

                cellIds = relationship.ids();
            }
        }

        iterator = line.relationships().iterator();

        while (iterator.hasNext()) {
            Relationship relationship = iterator.next();

            if (relationship.typeAsString().equals("CHILD")) {

                lineIds = relationship.ids();
            }
        }

        try {
            return cellIds.containsAll(lineIds);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
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
