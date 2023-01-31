package com.locuslink.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.uploadedFileObjects.WireObject;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class UploadController {

	private static final Logger logger = Logger.getLogger(UploadController.class);
    	
	@Autowired
    private SecurityContextManager securityContextManager;
	
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
	
		
	
	@Value("${file.templates.fullpath}")
	private String fileTemplatesFullpath;
			  
	@Value("${file.upload.staging.fullpath}")
	private String fileStagingFullpath;
	
	@Value("${file.upload.storage.fullpath}")
	private String fileStorageFullpath;
	
	@Value("${file.upload.error.fullpath}")
	private String fileErrorFullpath;			
			

//	@Autowired
//	private FileStorageService fileStorageService;


	@PostMapping(value = "/initUpload")
	public String initUpload (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initUpload()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload";
	}


	
	@PostMapping(value = "/initUploadStep2")
	public String initUploadStep2 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initUploadStep2()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/uploadstep2";
	}

	
	
	@PostMapping(value = "/initUploadStep3")
	public String initUploadStep3 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initUploadStep3()...");

		
//		String[] wrkArray = {"1","2","3","4","5"};
//		
//		ArrayList <String []> wrkArrayList = new ArrayList<String []>();
//		wrkArrayList.add(wrkArray);
//				
//		dashboardFormDTO.setDataTableArray(wrkArrayList);
	

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/uploadstep3";
	}
	

	
	/**
	 *  02-23-2023 - C.Sparks
	 *  
	 *  This is a JSON method because we return to the same screen to display a status for the "dropped" files on the UI.
	 *  This method will write the dropped files to a staging folder. The next step will save to the DB and permanent storage
	 */		
	@PostMapping(value = "/processCsvFileUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processCsvFileUpload(@RequestParam("file") MultipartFile inputfile,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

//		UserTrace user = (UserTrace) session.getAttribute("userTrace");
//		if (user == null) {
//			logger.debug(" userTrace in session does not exist");
//		}
		logger.debug("Starting processFileUpload()..inputfile ->:" + inputfile.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "trace - processCsvFileUpload");

		try {
			
			if (inputfile.getOriginalFilename().contains(".xls")) {
				// do nothing, all good
			} else {
				logger.debug("  ERROR xlsFileUpload failed ->: Wrong File extension. " );
				return response;
			}
			 
	        String fullpathFileName_keyName = fileStagingFullpath + inputfile.getOriginalFilename();	        
	        logger.debug("    ->: " + fullpathFileName_keyName);
        	        
            final ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentType(inputfile.getContentType());
            metaData.setContentLength(inputfile.getSize());
            
            // create and call S3 request to create the new S3 object 
            PutObjectRequest putObjectRequest = new PutObjectRequest(
            	awsS3BucketName, 
            	fullpathFileName_keyName, // file/object name in S3
            	inputfile.getInputStream(), // input stream from the Multipart
                metaData // created above, with the only content type and size
            );
            
            
                    
            PutObjectResult putObjectResult = awsS3Client.putObject(putObjectRequest);
            	        		
			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" CSV fileUpload Worked,  size ->: " + inputfile.getSize());
			//logger.debug("         result details ->: " + putObjectResult.g);

		} catch (Exception e) {
			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
	
	
	// TODO TEsting
	
		 @RequestMapping(value = "/getAllStagedUploads", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
		    public @ResponseBody GenericMessageResponse getAllUser(@RequestBody GenericMessageRequest request, HttpSession session)  {

			logger.debug("In getAllStagedUploads()");
			GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllStagedUploads");
	  
			// Displays on the UI - Target format for the downloaded xls files from the S3 bucket.
			List <WireObject> wireObjectList =  new ArrayList<WireObject>(); 
			
			
	        // Gets the list of just files, under the directory structure {tag name}
	        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	                .withBucketName(awsS3BucketName)
	                .withPrefix(fileStagingFullpath)
	                .withMarker(fileStagingFullpath);
	          
	        Row row = null;
	        S3Object s3Object;
	        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest)	 ;       		
	        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
	           
	        	logger.debug("    staging file found ->: " + s3ObjectSummary.getKey());	  
	            
	            s3Object = awsS3Client.getObject(awsS3BucketName, s3ObjectSummary.getKey());	            	            
	            S3ObjectInputStream s3is = s3Object.getObjectContent();
	           	     	           	            
	            // Create Workbook for each file in the staging folder
	            try {
					XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
		            XSSFSheet sheet = workbook.getSheetAt(0);
		            Iterator<Row> rowIterator = sheet.iterator();
		            		            
		        	while (rowIterator.hasNext()) {
						row = rowIterator.next();
						
						// TODO Create the JSON or the java object here
						// *********************************************************
						
						// Columns				
						int len = row.getLastCellNum();
						for ( int i = 0; len > i ; i++) {							
							System.out.print(row.getCell(i).toString());							
							if(len-1 == i) 	{
								// print nothing
							} else {
								System.out.print(",");
							}							
						}
						System.out.println();
		        	}						
						
				} catch (Exception e1) {
					e1.printStackTrace();
				}	                  
	        }
	        

			

			
			WireObject wireObject = new WireObject();
			wireObject.setMaterialId("100");
			wireObject.setMaterialName("Copper Wire");
			wireObject.setMaterialDesc("2 AWG Twisted Wire.");
			
			wireObjectList.add(wireObject);
			wireObjectList.add(wireObject);
			wireObjectList.add(wireObject);
			
		   //	model.addAttribute("wireObjectList", wireObjectList);
			ObjectMapper mapper = new ObjectMapper();		
			String json = "";	
			
			try {
				json = mapper.writeValueAsString(wireObjectList);			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.debug("json ->: " + json);
			
		//	String = "{"materialId":100,"materialName":"Copper Wire","materialDesc":"2 AWG Twisted Wire."},{"materialId":100,"materialName":"Copper Wire","materialDesc":"2 AWG Twisted Wire."},{"materialId":100,"materialName":"Copper Wire","materialDesc":"2 AWG Twisted Wire."}';
					
			
			//model.addAttribute("lvProjectAllList",json);
			
			response.setField("wireObjectList",  json);
			
		//	response.setField("wireObjectList",  wireObjectList);
			
			
			return response;
		 }
		 
		 


}