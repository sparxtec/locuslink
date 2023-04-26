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
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingResult;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.Tag;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.uploadedFileObjects.ProductDTO;
import com.locuslink.model.UniqueAsset;
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
	private UniqueAssetDao uniqueAssetDao;
	
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
			
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/uploadstep3";
	}
	

	
	/**
	 *  02-23-2023 - C.Sparks
	 *  
	 *  This is a JSON method because we return to the same screen to display a status for the "dropped" files on the UI.
	 *  This method will write the dropped files to a staging folder. The next step will save to the DB and permanent storage
	 */		
	@PostMapping(value = "/processXlsFileUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processXlsFileUpload(@RequestParam("file") MultipartFile inputfile,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

//		UserTrace user = (UserTrace) session.getAttribute("userTrace");
//		if (user == null) {
//			logger.debug(" userTrace in session does not exist");
//		}
		logger.debug("Starting processFileUpload()..inputfile ->:" + inputfile.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "trace - processXlsFileUpload");

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
            
            // Tags for easier process on retrieval
            List<Tag> tags = new ArrayList<Tag>();
            tags.add(new Tag("filename", inputfile.getOriginalFilename()));
            putObjectRequest.setTagging(new ObjectTagging(tags));
            
            PutObjectResult putObjectResult = awsS3Client.putObject(putObjectRequest);
            	        		
			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" CSV fileUpload Worked,  size ->: " + inputfile.getSize());
			//logger.debug("         result details ->: " + putObjectResult.g);

		} catch (Exception e) {
			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
	
	
	/**
	 *   04-26-2023 C.Sparks
	 *   Upload Page 3 calls this to display all files in the AWS S3 Staging bucket
	 */
	@RequestMapping(value = "/getAllStagedUploads", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllStagedUploads(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllStagedUploads()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllStagedUploads");
	  
		// Displays on the UI - Target format for the downloaded xls files from the S3 bucket.
		List <ProductDTO> productObjectList =  new ArrayList<ProductDTO>(); 
		ProductDTO productDTO = new ProductDTO();
		
	    // Gets the list of just files, under the directory structure {tag name}
	    ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(fileStagingFullpath)
	            .withMarker(fileStagingFullpath);
          		
        Row row = null;
        S3Object s3Object;
        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
           
        	System.out.println();
        	logger.debug("    staging file found ->: " + s3ObjectSummary.getKey());	 
        	
            GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(awsS3BucketName, s3ObjectSummary.getKey());
            GetObjectTaggingResult getTagsResult = awsS3Client.getObjectTagging(getTaggingRequest);
            
            String tagFileName = "unknown";
            List <Tag> tagList = getTagsResult.getTagSet();
            for (Tag tag :tagList ) {
            	logger.debug("    s3 tags found ->: " + tag.getKey() + " : " + tag.getValue());	 
            	if (tag.getKey().equals("filename")) {
            		tagFileName = tag.getValue();
            	}
            }
                        
            s3Object = awsS3Client.getObject(awsS3BucketName, s3ObjectSummary.getKey());	            	            
            S3ObjectInputStream s3is = s3Object.getObjectContent();           	     	              
            
            // Create Workbook for each file in the staging folder
            try {
				XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            boolean notFinished = true;		            
	        	while (rowIterator.hasNext() && notFinished) {	        	
					row = rowIterator.next();												
					int len = row.getLastCellNum();

					if ( row.getCell(0) != null ) {						
						// print out all the cells on this row
						String rowCellValue = "";
						for ( int i = 0; len > i ; i++) {								
							rowCellValue = row.getCell(i).toString();
							System.out.print(rowCellValue);							
							if(len-1 == i) 	{
								// do nothing
							} else {
								System.out.print(",");								
								if (rowCellValue.equalsIgnoreCase("catalog_id")) {
									
									// The only value we need for now is the Catalog id for the UI, not the file contents										  																	
									// 4-25-2023
									// TODO Check Product Type and load based on that.									
									// Key Data
									productDTO = new ProductDTO();									
									// the next cell contains the catalogID value
									productDTO.setProductCatalogId(row.getCell(i + 1).toString());
									
									// should come from the data
									productDTO.setProductNumber("AE-152D 4047-S");	
									productDTO.setHeatNumber("535521");
									
									productDTO.setUploadedFilename(tagFileName);
									productDTO.setProductTypeCode("STEEL_PIPE");																								
									//productDTO.setProductName("Steel Pipe");		
									productDTO.setProductDesc("FBE Steel Pipe 12-Inch API 5L PSL2 ");	
									
									productObjectList.add(productDTO);										
								}								
							}							
						}	
						System.out.println();						
		        	 } 							
	        	}	
			} catch (Exception e1) {
				e1.printStackTrace();
			}	                                     
            
        }
        

        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {	
			json = mapper.writeValueAsString(productObjectList);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("productObjectList",  json);

		return response;
	 }
		 
		 
	
	
	/**
	 *  04-25-2023 - C.Sparks
	 *  
	 *  Called from Upload Page 3, Submit button.
	 *  This method will write all uploaded files on upload page 3, to the database.
	 *     tables to load ->:  unique_asset,  product_attachment 
	 *     
	 *  The files are also moved in AWS S3 from the stage folder to the storage folder.
	 */		
	@PostMapping(value = "/processXlsFileSave")
	public String processXlsFileSave (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
//  [{"uploadedFilename":"Steel Pipe 0123001D015- MTR.xlsx","productCatalogId":"0123001D015","productNumber":"AE-152D 4047-S","productName":"Steel Pipe",
//           "productDesc":"FBE Steel Pipe 12-Inch API 5L PSL2 ","activeStatus":null,"productTypeCode":"STEEL_PIPE"}]

		logger.debug("Starting processXlsFileSave()...");
		logger.debug("  data ->: " + dashboardFormDTO.getJsonUploadedProductObjectList());
	
		List <ProductDTO> productObjectList =  new ArrayList<ProductDTO>();         
		ObjectMapper mapper = new ObjectMapper();				
		try {
			productObjectList =  mapper.readValue(dashboardFormDTO.getJsonUploadedProductObjectList(), new TypeReference<ArrayList<ProductDTO>>() {});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		for (ProductDTO productDTO : productObjectList) {
			
			logger.debug("Found  fileName ->: " + productDTO.getUploadedFilename() );				
			try {									
				//String tagFilename = "local/files/upload/staging/Steel Pipe 0123001D015- MTR.xlsx";				
				String tagFilename =fileStagingFullpath + productDTO.getUploadedFilename();
								
		        S3Object s3Object;
		        s3Object = awsS3Client.getObject(awsS3BucketName, tagFilename );	
	            S3ObjectInputStream s3is = s3Object.getObjectContent();     
				
	            // Create Workbook for this file in AWS S3, passed from upload page 3 SUBMIT
	            try {
					XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
		            XSSFSheet sheet = workbook.getSheetAt(0);
		            Iterator<Row> rowIterator = sheet.iterator();
		            Row row = null;
				
		            // Loop thru the rows in this file
		            UniqueAsset uniqueAsset = new UniqueAsset();
		 		            
		            boolean notFinished = true;            
		        	while (rowIterator.hasNext() && notFinished) {	        	
						row = rowIterator.next();												
						int len = row.getLastCellNum();
							
						// Loop thru the cells on a row
						if ( row.getCell(0) != null ) {						
							// print out all the cells on this row
							String rowCellValue = "";
							for ( int i = 0; len > i ; i++) {								
								rowCellValue = row.getCell(i).toString();
								System.out.print(rowCellValue);							
								if(len-1 == i) 	{
									// do nothing
								} else {
									System.out.print(",");	
									
									// TODO Find CELLS we care about
									
									if (rowCellValue.equalsIgnoreCase("catalog_id")) {
										
										// TODO add DB lookup to use text to get pkId
										uniqueAsset.setUcatPkId(804);
										uniqueAsset.setUniqueAssetId("xxx." + row.getCell(i+1).toString());
										uniqueAsset.setManufacturerPkId(55); // real value in the table
										uniqueAsset.setCustomerPkId(2);  // ACME Utilities
										uniqueAsset.setTraceTypePkId(40); // heat	
										
										uniqueAsset.setTraceCode("535521");
										uniqueAsset.setAddBy("digitalMtr");
										
									} else if (rowCellValue.equalsIgnoreCase("Facility_Name_for_Pipe_Manufacturer")) {
										
										// TODO add DB lookup to use "EVRAZ NA Camrose name" to get the pkId
										uniqueAsset.setManufacturerPkId(55);
															
									} else if (rowCellValue.equalsIgnoreCase("heat_number")) {
										
										uniqueAsset.setTraceCode(row.getCell(i+1).toString());
										
									} else if (rowCellValue.equalsIgnoreCase("catalog_id")) {
					
									}     
																											
									
								}							
							}	
							System.out.println();
						}

		        	}
				
		        	// Validate the required fields
		        	if (uniqueAsset.getUcatPkId() > 0) {
		        		logger.debug(" Found a new Catalogue PRoduct, to insert to the Unique Asset Table.");
		        				        		
		        		uniqueAssetDao.save(uniqueAsset);
		        		
		        	}
		        	
		        	
	            } catch (Exception e1) {
					e1.printStackTrace();
				}	     	            
		
			} catch (Exception e) {
				logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
			}
			
		}
		
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload";
	}
	
	


}