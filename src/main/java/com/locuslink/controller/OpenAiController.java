package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.OpenAI.ChatCompletionRequest;
import com.locuslink.dto.OpenAI.ChatCompletionResponse;



/**
 * This is a Spring MVC Controller.
 * 
 * @author C.Sparks
 * @since 1.0.0 - October 9, 2018 - Initial version
 */
@Controller
@Service
public class OpenAiController {
 
	private static final Logger logger = Logger.getLogger(ProfileController.class);
	
	//@Value("${openai.secretkey}")
	private String openAISecretKey = "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";
	
	private String openAIUrl = "https://api.openai.com/v1/chat/completions";
	
//    @Value("${app.logout.url}")
//    private String appLogoutUrl;
//    
//    @Autowired
//    private UserLocuslinkDao userLocuslinkDao;
    

	
	

	@PostMapping(value = "initAIUpload")
	public String initAIUpload (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAIUpload()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/ai-upload";
	}
	
	
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@PostMapping(value = "/initOpenAI")
	public String initOpenAI (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initOpenAI()...");
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
	   	
		return "fragments/openai";
	}
	
	
	@PostMapping(value = "/testApi_1")
	public String testApi_1 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
	
		logger.debug("In testApi_1()");
		
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "testApi_1");
		logger.debug("Starting testApi2()...");

	//	JSONObject jsonRequest = new JSONObject();
		JSONObject jsonAttributes = new JSONObject();
		
		// Step 2 - Call the BTC API to print to file
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + openAISecretKey );	
		
		
		
//		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonRequest.toString(), headers);
//		ResponseEntity<String> responseEntity = requestEntity.exchange(url, HttpMethod.POST, requestEntity, String.class);
//		logger.debug(" status ->: " + responseEntity.getStatusCode());
//		logger.debug(" response ->: " + responseEntity.getBody());
//		
//		
		String url = "https://api.openai.com/v1/chat/completions";
	

		
		String prompt = "100 word essay on coffee";
		ChatCompletionRequest  chatCompletionRequest = new ChatCompletionRequest("gpt-3.5-turbo", prompt);
		 
		String jsonRequest ="";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonRequest = mapper.writeValueAsString(chatCompletionRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonRequest.toString(), headers);
		//ResponseEntity<String> responseEntity =  restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		ResponseEntity<ChatCompletionResponse> chatCompletionResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ChatCompletionResponse.class);
		
		logger.debug(" status ->: " + chatCompletionResponse.getStatusCode());
		//logger.debug(" response ->: " + chatCompletionResponse.getBody());

		String openAiContent = "";
		for ( ChatCompletionResponse.Choice choice : chatCompletionResponse.getBody().getChoices()) {
			logger.debug("    Choice # ->: " + choice.getIndex());
			logger.debug("    Role  ->: " + choice.getMessage().getRole());
			logger.debug("    Content  ->: " + choice.getMessage().getContent());
			openAiContent = openAiContent +choice.getMessage().getContent();
		}
		

	   	model.addAttribute("openAiContent", openAiContent);		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		
		return "fragments/openai";
	}

//    
//	
//	@PostMapping(value = "/testApi_2", produces = "application/json", consumes = "application/json")
//	public @ResponseBody GenericMessageResponse testApi_2(@RequestBody GenericMessageRequest request, HttpSession session)  {
//	
//		logger.debug("In testApi_2()");
//		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "testApi");
//		
//		// OpenAI API we want to call
//		String url = "https://api.openai.com/v1/chat/completions";
//		JSONObject jsonRequest = new JSONObject();
//		JSONObject jsonAttributes = new JSONObject();
//		List <String> messages = new ArrayList <String> ();
//
////		curl https://api.openai.com/v1/chat/completions \
////			  -H "Content-Type: application/json" \
////			  -H "Authorization: Bearer $OPENAI_API_KEY" \
////			  -d '{
////			    "model": "gpt-3.5-turbo",
////			    "messages": [
////			      {
////			        "role": "system",
////			        "content": "You are a helpful assistant."
////			      },
////			      {
////			        "role": "user",
////			        "content": "Hello!"
////			      }
////			    ]
////			  }'
//	
//		try {
//			jsonAttributes.put("model", "gpt-3.5-turbo");
//			
//			
//			
//		} catch (JSONException e1) {
//			e1.printStackTrace();
//		}
//	
//		// Step 2 - Call the BTC API to print to file
//		RestTemplate rest = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Accept", "*/*");
//		headers.add("Authorization",  "Bearer " + openAISecretKey );				
//		
//		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonRequest.toString(), headers);
//		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
//		logger.debug(" status ->: " + responseEntity.getStatusCode());
//		logger.debug(" response ->: " + responseEntity.getBody());
//				
//		
//		 		
//		// Step 3 = Get the ID and StatusURL Variables from the above response
//		JSONObject jsonObj = null;
//		String id = "";
//		String statusUrl = "";
//		try {
//			jsonObj = new JSONObject(responseEntity.getBody());			
//			id = (String) jsonObj.get("Id");
//			statusUrl = (String) jsonObj.get("StatusUrl");			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		return response;
//	}
	
	

}