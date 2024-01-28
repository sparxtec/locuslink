package com.locuslink.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.locuslink.dao.ProductAttributeDao;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.ProductAttribute;


@JsonIgnoreProperties(ignoreUnknown = true) 
@Controller
@Service
public class BartenderRestClient {
	
	private static final Logger logger = Logger.getLogger(BartenderRestClient.class);
	
	// 5-17-2023
	//private static final String btcServerLocation = "https://bartendercloud.com";		
	private static final String btcServerLocation = "https://am1.bartendercloud.com";	
	
	
	@Value("${bartender.access.token}")
	private String bartenderAccessToken;
	
    @Value("${barcode.template.pipe}")
    private String barcodeTemplatePipe;
	
    @Value("${barcode.template.splice}")
    private String barcodeTemplateSplice;
    
    @Value("${barcode.template.cable}")
    private String barcodeTemplateCable;
	
    @Autowired
    private ProductAttributeDao productAttributeDao;
	  
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
	public String getBarcode_PDFEncodedStream(UniqueAssetDTO uniqueAssetDTO) {  
		
		// Step 1 - Print to FILE - Barcode to PDF
		String barcodePdfTempPath = btcBarcodePrintToFile(uniqueAssetDTO);
		
		// Step 2 - Get the temp PDF Barcode, as an encoded 64 String
		String encodedPDFBarcdeString = getPDFEncodedByteArray(barcodePdfTempPath);
				
		// Step 3 - return the encoded barcode byte array for viewing
		return encodedPDFBarcdeString;
	}
	
	
	
	/**
	 * 
	 */
	public String btcBarcodePrintToFile(UniqueAssetDTO uniqueAssetDTO) {  
		  		
		String  url = btcServerLocation + "/api/actions?Wait=30s&MessageCount=200&MessageSeverity=Info"	;
				  
		// Step 1 - Writer  Barcode to PDF on the Cloud
		JSONObject jsonAttributes = null;
		JSONObject jsonRequest = new JSONObject();
		JSONObject jsonMainOptions = new JSONObject();
		JSONObject jsonNamedDataSources = new JSONObject();

		
		//   1-12-2024 defaulkt them all to pipe when not really there, so the PDF viewer can show something	
		String barcodeTemplateName = "tbd";
		if (uniqueAssetDTO.getProductTypeCode().equals("CABLE"))  {
			
			// 1-26-2024
			//barcodeTemplateName = "ucable_prod.btw";					
			barcodeTemplateName = barcodeTemplateCable;
			try {	
				
				//jsonNamedDataSources.put("catalog_id",    uniqueAssetDTO.getUniqueAssetId().trim());	
				jsonNamedDataSources.put("catalog_id",    uniqueAssetDTO.getUniversalCatalogId().trim());	
				
				jsonNamedDataSources.put("manufacturer",  uniqueAssetDTO.getManufacturerName().trim());				
				ProductAttribute productAttribute = productAttributeDao.getByUniversalCatalogId(uniqueAssetDTO.getUcatPkId());
				if (productAttribute != null) {
					jsonAttributes = new JSONObject(productAttribute.getAttributesJson());	
					// put serial number in lot code
					jsonNamedDataSources.put("lot_code", jsonAttributes.get("serial_number"));				
					jsonNamedDataSources.put("reel_id", jsonAttributes.get("reel_id"));
					jsonNamedDataSources.put("customer_part_number", jsonAttributes.get("customer_part_number"));	
				} else {							
					// put serial number in lot code
					jsonNamedDataSources.put("lot_code", "serial_na");				
					jsonNamedDataSources.put("reel_id", "reel_na");
					jsonNamedDataSources.put("customer_part_number", "part_na");	
					
				}			
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
	
		} else if (uniqueAssetDTO.getProductTypeCode().contains("SPLICE"))  {
	
			// 1-26-2024
			//barcodeTemplateName = "ucable_prod.btw";	
			barcodeTemplateName = barcodeTemplateCable;
		
			try {				
				//jsonNamedDataSources.put("catalog_id",    uniqueAssetDTO.getUniqueAssetId().trim());	
				jsonNamedDataSources.put("catalog_id",    uniqueAssetDTO.getUniversalCatalogId().trim());	
				
				jsonNamedDataSources.put("manufacturer",  uniqueAssetDTO.getManufacturerName().trim());				
				ProductAttribute productAttribute = productAttributeDao.getByUniversalCatalogId(uniqueAssetDTO.getUcatPkId());				
				jsonAttributes = new JSONObject(productAttribute.getAttributesJson());				
				jsonNamedDataSources.put("lot_code", jsonAttributes.get("serial_number"));
				jsonNamedDataSources.put("reel_id", "");
				jsonNamedDataSources.put("customer_part_number", jsonAttributes.get("customer_part_number"));								
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (uniqueAssetDTO.getProductTypeCode().equals("STEEL_PIPE")) {
			
			// 1-26-2024
			//barcodeTemplateName = "pipe_prod.btw";
			barcodeTemplateName = barcodeTemplatePipe;
			
			try {
				//jsonNamedDataSources.put("xxxxxx",  uniqueAssetDTO.getUniqueAssetId());
				
				// 1-28-2023  works
				//jsonNamedDataSources.put("univ_id",    "x1.x2.x3");					
				//jsonNamedDataSources.put("serial_number",  "S78787878");	
				//jsonNamedDataSources.put("heat_number",  "B56565656");		
				//jsonNamedDataSources.put("manufacturer",  "Steel Co");	
				//jsonNamedDataSources.put("purchase_order",  "12121212");	
				
				jsonNamedDataSources.put("univ_id",    uniqueAssetDTO.getUniqueAssetId());					
				jsonNamedDataSources.put("serial_number",  "S03146257");	// Not in the DB, WE have Heat or Serial not both. This is really part number ?
				jsonNamedDataSources.put("heat_number",  uniqueAssetDTO.getTraceCode().trim());		
				jsonNamedDataSources.put("manufacturer",  uniqueAssetDTO.getManufacturerName().trim());	
				jsonNamedDataSources.put("purchase_order",  "801375");	// asset doesn't have to have a po, not sure why matt has this in here.
				
				
				//ProductAttribute productAttribute = productAttributeDao.getByUniversalCatalogId(uniqueAssetDTO.getUcatPkId());				
				//jsonAttributes = new JSONObject(productAttribute.getAttributesJson());				
				//jsonNamedDataSources.put("lot_code", jsonAttributes.get("serial_number"));
				//jsonNamedDataSources.put("reel_id", "");
				//jsonNamedDataSources.put("customer_part_number", jsonAttributes.get("customer_part_number"));	
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}						
		} else {
			// C.Sparks 1-12-2024 need a default file for the viewer as we build out more assets
			// Should make this a generic PDF, not a cable one.		
			
			// 1-26-2024
			barcodeTemplateName = barcodeTemplatePipe;
			//barcodeTemplateName = "pipe_prod.btw";			
			try {
				jsonNamedDataSources.put("xxxxxx",  uniqueAssetDTO.getUniqueAssetId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		try {
			jsonMainOptions.put("Document", "Librarian://main/" + barcodeTemplateName );			
			jsonMainOptions.put("Printer", "PDF");			
			jsonMainOptions.put("ReturnPrintData", "true");
			jsonMainOptions.put("ReturnPrintSummary", "true");						
			jsonMainOptions.put("PrintToFileFolder", "Librarian://main/output/pdf/");			
			jsonMainOptions.put("PrintToFileFileName", uniqueAssetDTO.getUniqueAssetPkId() + "_output.pdf");							
			jsonMainOptions.put("PrintToFileNameVariable", "PrintToFileName");				
			jsonMainOptions.put("SaveAfterPrint", false);	
			
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
		
		
		// 1-12-2024  When the Barcode Token expires this traps out. i want to display a sample image instead
		HttpEntity<String> requestEntity = null;
		ResponseEntity<String> responseEntity = null;
		try {			
			requestEntity = new HttpEntity<String>(jsonRequest.toString(), headers);
			responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
			
			logger.debug(" status ->: " + responseEntity.getStatusCode());
			logger.debug(" response ->: " + responseEntity.getBody());
			
		} catch (Exception e) {
			logger.debug(" ERROR ->: BArcode token key may be expired, lookup faild. ->: " + e.getMessage());
		}


			// Early Exit
		if (responseEntity == null) {
			logger.debug(" Respone was bad, so manually creating a response to display a blank image.");

			// response entity body is just a string
			return "{encodedPDFBarcdeString:''}";
		} 
		
		 		
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