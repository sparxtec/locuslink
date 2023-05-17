package com.locuslink.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Controller
@Service
public class BartenderRestClient {
	
	private static final Logger logger = Logger.getLogger(BartenderRestClient.class);
	
	// 5-17-2023
	//private static final String btcServerLocation = "https://bartendercloud.com";		
	private static final String btcServerLocation = "https://am1.bartendercloud.com";	
	
	
	@Value("${bartender.access.token}")
	private String bartenderAccessToken;
	
	  
	public String getPrinterList() {
		
		String url = btcServerLocation + "/api/printers";	
		
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
		    								 
		
		HttpEntity<String> requestEntity = new HttpEntity<String>("body", headers);
		
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
		    		    
		return responseEntity.getBody();
	}
	
	
	/**
	 * 	C.Sparks 3-21-2023
	 * 
	 *   Step 1 - Print to FILE - Barcode to PDF, Get the oputput file path of the written PDF barcode
	 *   Step 2 - Get the encoded byte array for the PDF
	 *   Step 3 - Return to UI for Viewing and/or local printing
	 */
	public String getBarcode_PDFEncodedStream(String barcodeTemplateName, String  uniqueAssetPkId) {  
		
		// Step 1 - Print to FILE - Barcode to PDF
		String barcodePdfTempPath = btcBarcodePrintToFile(barcodeTemplateName, uniqueAssetPkId);
		
		// Step 2 - Get the temp PDF Barcode, as an encoded 64 String
		String encodedPDFBarcdeString = getPDFEncodedByteArray(barcodePdfTempPath);
				
		// Step 3 - return the encoded barcode byte array for viewing
		return encodedPDFBarcdeString;
	}
	
	
	
	/**
	 * 
	 */
	public String btcBarcodePrintToFile(String barcodeTemplateName, String  uniqueAssetPkId) {  
		  				
		String  url = btcServerLocation + "/api/actions?Wait=30s&MessageCount=200&MessageSeverity=Info"	;
				  
		// Step 1 - Writer  Barcode to PDF on the Cloud
		JSONObject jsonRequest = new JSONObject();
		JSONObject jsonMainOptions = new JSONObject();
		JSONObject jsonNamedDataSources = new JSONObject();

		try {
			jsonMainOptions.put("Document", "Librarian://main/" + barcodeTemplateName );			
			jsonMainOptions.put("Printer", "PDF");			
			jsonMainOptions.put("ReturnPrintData", "true");
			jsonMainOptions.put("ReturnPrintSummary", "true");						
			jsonMainOptions.put("PrintToFileFolder", "Librarian://main/output/pdf/");			
			jsonMainOptions.put("PrintToFileFileName", uniqueAssetPkId + "_output.pdf");							
			jsonMainOptions.put("PrintToFileNameVariable", "PrintToFileName");				
			jsonMainOptions.put("SaveAfterPrint", false);	
			
			
			// TODO 5-17-2023
			//    Get the attributes from the DB, and populate them all here			
			jsonNamedDataSources.put("Ship_To_Name", "Kracken:" + uniqueAssetPkId);
			
			
			jsonMainOptions.put("NamedDataSources", jsonNamedDataSources);			

			jsonRequest.put("PrintBTWAction", jsonMainOptions);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("jsonREquest ->: " +jsonRequest.toString());
		
			
		// Step 2 - Call the BTC API to print to file
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );				
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonRequest.toString(), headers);
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
				
		
				
		// Step 3 = Get the ID and StatusURL Variables from the above response
		JSONObject jsonObj = null;
		String id = "";
		String statusUrl = "";
		try {
			jsonObj = new JSONObject(responseEntity.getBody());			
			id = (String) jsonObj.get("Id");
			statusUrl = (String) jsonObj.get("StatusUrl");			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
				
		
		// Step 4 - Get the PDF output path, so we can stream it in the next step {could use ID or statusURL}
		//     ex ->: Librarian://main/output/pdf/Output(2023-03-16T14.52.37.478).pdf
		url = statusUrl + "/variables/PrintToFileName";				    								
		requestEntity = new HttpEntity<String>("", headers);
		responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
		logger.debug(" status ->: " + responseEntity.getStatusCode());
		logger.debug(" response ->: " + responseEntity.getBody());
		
		// The body is the byte array
	    return responseEntity.getBody();
	}

	
	

	public String getPDFEncodedByteArray(String barcodePdfTempPath) {  
		  		
		String filePathEncoded = "";
        try {
        	
        	//filePathEncoded = URLEncoder.encode("Librarian://main/output/pdf/Output.pdf", StandardCharsets.UTF_8.toString());        
        	filePathEncoded = URLEncoder.encode(barcodePdfTempPath, StandardCharsets.UTF_8.toString());                    	        
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
		
        
        // Step 1 - Get the PDF from the temp output folder it was written to
        String url = btcServerLocation + "/api/librarian/files/path/" + filePathEncoded + "/content";		
        logger.debug(" url ->: " + url);
        
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
		headers.add("Authorization",  "Bearer " + bartenderAccessToken );				
		
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<byte[]> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, byte[].class);				

		
		// Step 2 - Convert the PDF image byte array to an encoded string
		ByteArrayResource byteArrayResource = new ByteArrayResource(responseEntity.getBody());				
		String encodedPDFBarcdeString = Base64.getEncoder().encodeToString(byteArrayResource.getByteArray());
		logger.debug( encodedPDFBarcdeString );
						
		//return new ResponseEntity<Resource>(byteArrayResource, null, HttpStatus.OK);	
		
		//    return responseEntity.getBody();
		return encodedPDFBarcdeString;
	}
	
	

	

	  
//	public void put(String uri, String json) {
//		 
//		String url = btcServerLocation + "/api/xxxxx";
//			
//		RestTemplate rest = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Accept", "*/*");
//		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
//			
//	    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
//	    ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, requestEntity, String.class);  
//		logger.debug(" status ->: " + responseEntity.getStatusCode());
//		logger.debug(" response ->: " + responseEntity.getBody());
//		
//	}
//
//	  public void delete(String uri) {
//		
//		String url = btcServerLocation + "/api/xxxx";
//			
//		RestTemplate rest = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Accept", "*/*");
//		headers.add("Authorization",  "Bearer " + bartenderAccessToken );
//			
//	    HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
//	    ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
//		logger.debug(" status ->: " + responseEntity.getStatusCode());
//		logger.debug(" response ->: " + responseEntity.getBody());
//	  }

}