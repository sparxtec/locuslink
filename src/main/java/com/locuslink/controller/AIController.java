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
import com.locuslink.logic.AwsTextractLogic;
import com.locuslink.logic.AzureNerLogic;
import com.locuslink.model.ProductAttachment;



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
      private AwsTextractLogic  awsTextractLogic;
	  
	  private AzureNerLogic azureNerLogic;
	

	/**
	 *  04-19-2024 - C.Sparks
	 *  
	 *  This type of method is a JSON method, where the UI navigation is not changing. 
	 *  The UI needs data or a response, so its called with this method signature.
	 */		
	@RequestMapping(value = "/processAWSTextract", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getSomeData(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In processAWSTextract()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "processAWSTextract");

		// TODO This is hard coded as 123 for now, but oncei get the database model created an loaded, we will write SQL here to pull
		// the assembly object
		String assemblyPkId = request.getFieldAsString("assemblyPkId");
		if (assemblyPkId == null || assemblyPkId.length() < 1) {
			logger.debug("Error - assemblyPkId is missing");
		} else {
			logger.debug("  assemblyPkId ->: " + assemblyPkId);
		}
		
		
		try {
			// C.Sparks  04-19-2024
			//    This is where the work will go. Below is a sample on calling the "logic" module.
			//    small coding can go in here, but larger chunks need to be separated out into logic modules/functions so they can be shared.
			
			
			JsonNode ocrResults = awsTextractLogic.process_1(assemblyPkId);
			String result = ocrResults.isNull() ? "failed" : "succeeded";
			logger.debug("Result from AWSTextract processing ->: " + result);
			
			JSONObject nerResults = azureNerLogic.process_2(ocrResults);
			result = nerResults == null ? "failed" : "succeeded";
			logger.debug("Result from AzureNER processing ->: " + result);
			
//			boolean result = awsTextractLogic.process_2();
//			logger.debug("Result from AWSTextract processing ->: " + result);
//			
//			boolean result = awsTextractLogic.process_3();
//			logger.debug("Result from AWSTextract processing ->: " + result);
			

			
			
		} catch (Exception e) {
			logger.debug("  ERROR something failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
}