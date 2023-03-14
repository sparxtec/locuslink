package com.locuslink.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Controller
@Service
public class BartenderRestClient {
	
	private static final Logger logger = Logger.getLogger(BartenderRestClient.class);
	
	private static final String btcServerLocation = "https://bartendercloud.com";	
	
	@Value("${bartender.access.token}")
	private String bartenderAccessToken;
	
	  
	public String getPrinterList() {
		
		String url = btcServerLocation + "/api/printers";	
		
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
		    								 
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
		    		    
		return responseEntity.getBody();
	}
	
	
	public String printBarcode(String json) {  
		  
		String  url = btcServerLocation + "/api/actions?Wait=30s&MessageCount=200&MessageSeverity=Info"	;
				   				   
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );				
		
	    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
	    ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
		
	    return responseEntity.getBody();
	}

	  
	public void put(String uri, String json) {
		 
		String url = btcServerLocation + "/api/xxxxx";
			
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
			
	    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
	    ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, requestEntity, String.class);  
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
		
	}

	  public void delete(String uri) {
		
		String url = btcServerLocation + "/api/xxxx";
			
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
			
	    HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
	  }

}