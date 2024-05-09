package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.logic.AwsTextractLogic;



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

		try {
			// C.Sparks  04-19-2024
			//    This is where the work will go. Below is a sample on calling the "logic" module.
			//    small coding can go in here, but larger chunks need to be separated out into logic modules/functions so they can be shared.
			
			
			boolean result = awsTextractLogic.process_1();
			logger.debug("Result from AWSTextract processing ->: " + result);
			
		} catch (Exception e) {
			logger.debug("  ERROR something failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
}