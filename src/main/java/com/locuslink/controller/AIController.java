package com.locuslink.controller;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.dao.AssemblyAttachmentDao;
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
		
				
		try {
			// 6-19-2024			
			JsonNode ocrResults = awsTextractLogic.process(assemblyAttachment);
			
			String result = "";
			if (ocrResults != null) {
				// 6-20-2024 I. Summers
				// 		AWS Textract Block object does not have bean properties, so .toPrettyString() 
				//		on following line throws InvalidDefinitionException
				//logger.debug("ocrResults ->: " + ocrResults.toPrettyString());	
				result = ocrResults.isNull() ? "failed" : "succeeded";
				logger.debug("Result from AWSTextract processing ->: " + result);
			} else {
				logger.debug("Error: Result from AWSTextract processing is null.");
			}
			
			JSONObject nerResults = azureNerLogic.processAzureNER(ocrResults);
			if (nerResults != null) {
				logger.debug("nerResults ->: " + nerResults.toJSONString());
				result = nerResults == null ? "failed" : "succeeded";
				logger.debug("Result from AzureNER processing ->: " + result);	
			} else {
				logger.debug("Error: Result from AzureNER processing is null.");
			}

			// TODO
			// Add in result process to the database, so the UI can display status and attributes, even for partial results.
			
			
			
			
		} catch (Exception e) {
			logger.debug("  ERROR something failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
}