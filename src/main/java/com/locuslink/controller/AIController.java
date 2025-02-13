package com.locuslink.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dto.Azure.Entity;
import com.locuslink.dto.Azure.MtrDocumentDTO;
import com.locuslink.logic.AwsTextractLogic;
import com.locuslink.logic.AzureNerLogic;
import com.locuslink.model.AssemblyAttachment;



/**
 * This is the controller to be used for the AI development work. AWS-Textract and Azure NER processing.
 * This controller has methods in it for AI functions. The UI page routine, is in another controller.
 * UI pages and Controllers are kinda 1-1 so things are easy to find.
 * 
 * I will set up the other Java modules for processing, logic, services, DAO, etc to provide samples and a starting point.
 * 
 * 
 * @author C.Sparks
 * @since 1.0.0 - April 19, 2024 - Initial version
 */
@Controller
@Service
public class AIController {
 
	private static final Logger logger = Logger.getLogger(AIController.class);
	
    @Autowired
    private AssemblyAttachmentDao assemblyAttachmentDao;
	   
    @Autowired
    private AwsTextractLogic  awsTextractLogic;
  
    @Autowired
    private AzureNerLogic azureNerLogic;
	

	/**
	 *  04-19-2024 - C.Sparks
	 *  
	 *  This type of method is a JSON method, where the UI navigation is not changing. 
	 *  The UI needs data or a response, so its called with this method signature.
	 */		
	@RequestMapping(value = "/processAWSTextract", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse processAWSTextract(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In processAWSTextract()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "processAWSTextract");

		// 6-19-2024
		String assemblyAttachmentPkId = request.getFieldAsString("assemblyAttachmentPkId");
		if (assemblyAttachmentPkId == null || assemblyAttachmentPkId.length() < 1) {
			logger.debug("Error - assemblyAttachmentPkId is missing");			
			response.setError(420);
			response.setErrorMessage("Error. The assemblyAttachmentPkId was not found and is required.");
			return response;						
		} else {
			logger.debug("  assemblyAttachmentPkId ->: " + assemblyAttachmentPkId);
		}
				
		// 6-19-2024
		AssemblyAttachment assemblyAttachment = assemblyAttachmentDao.getById(Integer.valueOf(assemblyAttachmentPkId));
		if (assemblyAttachment == null) {
			logger.debug("Error - Assembly Attachment not found in the DB.");			
			response.setError(430);
			response.setErrorMessage("Error - Assembly Attachment not found in the DB.");
			return response;
		}
		
		
		// AWS
		JsonNode ocrResults = null;
		String result = "";		
		try {	
			ocrResults = awsTextractLogic.process(assemblyAttachment);			
			if (ocrResults != null) {
				result = ocrResults.isNull() ? "failed" : "succeeded";
				logger.debug("Result from AWSTextract processing ->: " + result);
			} else {
				logger.debug("Error: Result from AWSTextract processing is null.");
				response.setError(710);
				response.setErrorMessage("Error: Result from AWSTextract processing is null" );
				return response;
			}
		} catch (Exception e) {
			logger.debug("  ERROR awsTextractLogic failed ->: " + e.getMessage());		
			response.setError(720);
			response.setErrorMessage("ERROR awsTextractLogic failed with exception ->: " + e);
			return response;
		}
		
				
		// Azure
		JSONObject nerResults = null;
		try {
			nerResults = azureNerLogic.processAzureNER(ocrResults);
			if (nerResults != null) {
				logger.debug("nerResults ->: " + nerResults.toJSONString());
				result = nerResults == null ? "failed" : "succeeded";
				logger.debug("Result from AzureNER processing ->: " + result);	
			} else {
				logger.debug("Error: Result from AzureNER processing is null.");
				response.setError(730);
				response.setErrorMessage("\"Error: Result from AzureNER processing is null." );
				return response;
			}
								
		} catch (Exception e) {
			logger.debug("  AzureNerLogic failed ->: " + e.getMessage());
			response.setError(740);
			response.setErrorMessage("ERROR AzureNerLogic failed with exception ->: " + e);
			return response;
		}
		
		
		// Create DTO for the Azure Results
		List<MtrDocumentDTO> mtrDocumentDTOList = new ArrayList<MtrDocumentDTO>();
		if (processNerResults(nerResults, mtrDocumentDTOList)) {
			logger.debug("  Success, MTR attributes found.");
		} else {
			logger.error("  Error ->: Processing parsing the MTR data from the Azure NER result");
			response.setError(750);
			response.setErrorMessage("ERROR  Processing parsing the MTR data from the Azure NER result");
		}
				
		
		// Add in result process to the database, so the UI can display status and attributes, even for partial results.	
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(mtrDocumentDTOList);			
		} catch (Exception e) {
			e.printStackTrace();
		}           		
	
		assemblyAttachment.setAttributesJson(json);	
		
		if (response.getError() > 0) {
			assemblyAttachment.setAttrFlag("Error.");
		} else {
			assemblyAttachment.setAttrFlag("Success.");
		}

		assemblyAttachmentDao.saveOrUpdate(assemblyAttachment);
		logger.debug("Success DB Update.   assemblyAttachment jsonAttributes() ");	
		
		
		return response;
	}
	
	
	
	
	/**
	 *  7-3-2024
	 *  	Parse the Azure results into a DTO that can saved and parsed
	 */
	private boolean processNerResults(JSONObject nerResults, List<MtrDocumentDTO> mtrDocumentDTOList) {
				
		MtrDocumentDTO mtrDocumentDTO = null;
		List<Entity> entityList = null;
		Entity entity = null;
		List<String> warnings = null;
		String id = null;
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(nerResults.toJSONString());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		
		JsonNode documentsNode =   root.get("documents");
		JsonNode entitiesArray = null;
		if (documentsNode.isArray()) {				

			for (JsonNode documentNode : documentsNode ) {
				// Set document id and warnings list
				id = documentNode.path("id").asText();
				if (documentNode.path("warnings").isArray()) {
					warnings = new ArrayList<String>();
					for (JsonNode warning : documentNode.path("warnings")) {
						warnings.add(warning.asText());
					}
				}
        	   
				// Access entities node
				entitiesArray = documentNode.get("entities");
				
				// TODO 7-2-2024 test this
				entityList = new ArrayList<Entity>();
				if (entitiesArray.isArray()) {   
					for (JsonNode dataNode :entitiesArray ) {   
						// Fetch each entity
						entity = Entity.builder()
								.category(dataNode.path("category").asText())
								.confidenceScore(dataNode.path("confidenceScore").floatValue())
								.length(dataNode.path("length").asInt())
								.offset(dataNode.path("offset").asInt())
								.text(dataNode.path("text").asText())
								.build();
    					  
						entityList.add(entity);	// Add entity to list
					}	        			                      
				}				
				mtrDocumentDTO = new MtrDocumentDTO(id, entityList, warnings);
				mtrDocumentDTOList.add(mtrDocumentDTO);
            }				 
		}

		return true;
	}
	
	
}