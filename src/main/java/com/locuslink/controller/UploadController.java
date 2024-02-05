package com.locuslink.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONObject;
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
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dao.ProductAttributeDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.uploadedFileObjects.ProductDTO;
import com.locuslink.model.ProductAttachment;
import com.locuslink.model.ProductAttribute;
import com.locuslink.model.UniqueAsset;
import com.locuslink.model.UniversalCatalog;

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
	private UniversalCatalogDao universalCatalogDao;
	
    @Autowired
    private ProductAttachmentDao productAttachmentDao;
    
    @Autowired
    private ProductAttributeDao productAttributeDao;
    
    
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
			
	 
	@Value("${file.attachment.staging.fullpath}")
	private String attachmentStagingFullpath;
	
	@Value("${file.attachment.storage.fullpath}")
	private String attachmentStorageFullpath;

//	@PostMapping(value = "/initUpload")
//	public String initUpload (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
//		logger.debug("Starting initUpload()...");
//
//		
//		// TODO make a distinct list based on the product type
//		// Build the drop down on the UI for uploading
//		List <UniversalCatalog> ucatList = universalCatalogDao.getAll();
//		
//	   	model.addAttribute("ucatList", ucatList);	   	
//	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
//
//		return "fragments/upload-data";
//	}


	
	
	@PostMapping(value = "/uploadStep1")
	public String uploadStep1 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting uploadStep1()...");

		
		// TODO make a distinct list based on the product type
		// Build the drop down on the UI for uploading
		List <UniversalCatalog> ucatList = universalCatalogDao.getAll();
		
	   	model.addAttribute("ucatList", ucatList);	   	
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload-step1";
	}
	
	
	
	
	@PostMapping(value = "/uploadStep2")
	public String uploadStep2 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting uploadStep2()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload-step2";
	   	
	  // 	return "fragments/uploadstep2";
	   	
	}

	
	
	@PostMapping(value = "/uploadStep3")
	public String uploadStep3 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting uploadStep3()...");
			
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload-step3";
	}
	

	@PostMapping(value = "/uploadStep4")
	public String uploadStep4 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting uploadStep3()...");
			
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload-step4";
	}
	
	
	// TODO 6-15-2023   
// FIX THIS
	
	@PostMapping(value = "/deleteUploadStagedFile")
	public String deleteUploadStagedFile (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting deleteUploadStagedFile()...");

		
//		// TODO Delete the clicked on ROW from AWS and the Database
//		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(dashboardFormDTO.getProductAttachPkId()));
//		if (productAttachment == null) {
//			logger.debug("  Error:  Product Attachment Lookup failed...");
//		}
//		
//		// AWS Remove the attachment from the Storge Area			
//        awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, productAttachment.getFilenameFullpath()));
//        logger.debug("     remove the AWS STORAGE file successfully.");	 
//        
//        // Database
//        productAttachmentDao.delete(productAttachment);
		
        
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
            
            
            if (fullpathFileName_keyName.toUpperCase().contains("MTR")) {
                tags.add(new Tag("product_type", "STEEL"));
            } else if (fullpathFileName_keyName.toUpperCase().contains("CABLE")) {
                tags.add(new Tag("product_type", "CABLE"));
            }else if (fullpathFileName_keyName.toUpperCase().contains("SPLICE")) {
                tags.add(new Tag("product_type", "SPLICE"));
            }
            else {
            	
            	// need to know the file type or we cant write it to AWS, it will get stuck there.
            	logger.debug("  ERROR csvFileUpload failed unknow file type NEED CODE. ->: " );
            	return response;
            }
            
            
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

		
	    // Gets the list of just files, under the directory structure {tag name}
	    ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(fileStagingFullpath)
	            .withMarker(fileStagingFullpath);
          		

        S3Object s3Object;
        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
           
        	System.out.println();
        	logger.debug("    staging file found ->: " + s3ObjectSummary.getKey());	 
        	
            GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(awsS3BucketName, s3ObjectSummary.getKey());
            GetObjectTaggingResult getTagsResult = awsS3Client.getObjectTagging(getTaggingRequest);
            
            String tagFileName = "unknown";
            String tagProductType = "unknown";
            List <Tag> tagList = getTagsResult.getTagSet();
            for (Tag tag :tagList ) {
            	logger.debug("    s3 tags found ->: " + tag.getKey() + " : " + tag.getValue());	 
            	if (tag.getKey().equals("filename")) {
            		tagFileName = tag.getValue();
            	}
            	// 6-12-2023
            	if (tag.getKey().equals("product_type")) {
            		tagProductType = tag.getValue();
            	}
            }
                        
            s3Object = awsS3Client.getObject(awsS3BucketName, s3ObjectSummary.getKey());	            	            
            S3ObjectInputStream s3is = s3Object.getObjectContent();           	     	              
                        
            // TODO 6-12-2023
            if (tagProductType.toUpperCase().contains("STEEL")) {            	
            	processgetStagedSteel(productObjectList,  tagFileName, s3is );            	
            } else if (tagProductType.toUpperCase().contains("CABLE")) {            	
            	processgetStagedCable(productObjectList,  tagFileName, s3is );   
            } else if (tagProductType.toUpperCase().contains("SPLICE")) {            	
            	processgetStagedSplice(productObjectList,  tagFileName, s3is );            	
            } else {
            	logger.error("Error: The product type for the file was not found, cant process the STAGED FILE, delet it from AWS S3 Bucket.");
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
		 
		 
	
	private  boolean processgetStagedSteel( List <ProductDTO> productObjectList, String tagFileName, S3ObjectInputStream s3is ) {
		
		ProductDTO productDTO = new ProductDTO();
        Row row = null;
        
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
						
						// 6-12-2023
						if (!row.getCell(i).equals(null) && row.getCell(i) != null ) {
							rowCellValue = row.getCell(i).toString();
							System.out.print(rowCellValue);	
						}								
					
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
								
							} else if (rowCellValue.equalsIgnoreCase("heat_number")) {
							
								// 2-1-2024 Override the default with the real value in the excel files
								//uniqueAsset.setTraceCode(row.getCell(i+1).toString());
								productDTO.setHeatNumber(row.getCell(i+1).toString());
							}
						}							
					}	
					System.out.println();						
	        	 } 							
        	}	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				s3is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		return true;
	}
	
	
	private  boolean processgetStagedCable( List <ProductDTO> productObjectList, String tagFileName, S3ObjectInputStream s3is ) {
		
		ProductDTO productDTO = new ProductDTO();
        Row row = null;
        
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
						
						// 6-12-2023
						if ( row.getCell(i) != null ) {
							rowCellValue = row.getCell(i).toString();
							System.out.print(rowCellValue);	
						}								
					
						if(len-1 == i) 	{
							// do nothing
						} else {
							System.out.print(",");								
							if (rowCellValue.equalsIgnoreCase("catalog_id")) {
															
								// Key Data
								productDTO = new ProductDTO();									
								productDTO.setProductCatalogId(row.getCell(i + 1).toString());
																
								productDTO.setUploadedFilename(tagFileName);
								productDTO.setProductTypeCode("CABLE");		
								productDTO.setProductDesc("Under ground Cable Medium Voltage");	
								
								// should come from the data
								productDTO.setProductNumber("ELEC-DISTR-UC-MV");	
								productDTO.setTraceTypeCode("SERIAL");
								productDTO.setSerialNumber("ser12345");
								
								productObjectList.add(productDTO);										
							}								
						}							
					}	
					System.out.println();						
	        	 } 							
        	}	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				s3is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		return true;
	}
	
	
	
private  boolean processgetStagedSplice( List <ProductDTO> productObjectList, String tagFileName, S3ObjectInputStream s3is ) {
		
		ProductDTO productDTO = new ProductDTO();
        Row row = null;
        
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
						
						// 6-12-2023
						if ( row.getCell(i) != null ) {
							rowCellValue = row.getCell(i).toString();
							System.out.print(rowCellValue);	
						}								
					
						if(len-1 == i) 	{
							// do nothing
						} else {
							System.out.print(",");								
							if (rowCellValue.equalsIgnoreCase("catalog_id")) {
															
								// Key Data
								productDTO = new ProductDTO();									
								productDTO.setProductCatalogId(row.getCell(i + 1).toString());
																
								productDTO.setUploadedFilename(tagFileName);
								productDTO.setProductTypeCode("SPLICE");		
								productDTO.setProductDesc("Joint Splice");	
								
								// should come from the data
								productDTO.setProductNumber("ELEC-DISTR-SPLICE-DJ");	
								productDTO.setTraceTypeCode("SERIAL");
								productDTO.setSerialNumber("ser12345");
								
								productObjectList.add(productDTO);										
							}								
						}							
					}	
					System.out.println();						
	        	 } 							
        	}	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				s3is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		return true;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 *  01-23-2024 - C.Sparks
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
	            UniqueAsset uniqueAsset = new UniqueAsset();
	            
	            // TODO 6-12-2023
	            if (productDTO.getProductTypeCode().toUpperCase().contains("STEEL")) {
	            	if (processSaveToDB_Steel( s3is, uniqueAsset )) {
	            		logger.debug("Successfull Save to DB, Unique Asset Created");
	            	} else {
	            		// ERROR
	            		logger.error("Error - saving file to Database, bypassing.");
	            		continue;
	            	}
	            } else if (productDTO.getProductTypeCode().toUpperCase().contains("CABLE")) {
	             	if (processSaveToDB_Cable(  s3is, uniqueAsset  )) {
	             		logger.debug("Successfull Save to DB, Unique Asset Created");
	             	} else {
	             		logger.error("Error - saving file to Database, bypassing.");
	             		continue;
	             	}
	             	
	            } else if (productDTO.getProductTypeCode().toUpperCase().contains("SPLICE")) {
	             	if (processSaveToDB_Splice(  s3is, uniqueAsset  )) {
	             		logger.debug("Successfull Save to DB, Unique Asset Created");
	             	} else {
	             		logger.error("Error - saving file to Database, bypassing.");
	             		continue;
	             	}   	
	             	
	            } else {
	              	logger.error("Error: The product type for the file was not found, cant save to DB.");
	            }
	            
	            
	            
	            	            	           
	            // The Upload File saved successfully to create an Asset in the DB
	            //  1.) Clean up AWS, move stage to Storage
	            //  2.) Save Storage File to the Asset Attachments Table in the DB
	            
	            
	            //String sourceKey = s3Object.getKey();                       	            	            
	            
	            // 1.) All Done Clean Up AWS - Move from Staging to Storage
	            processCopyStageToStorage(s3Object);
	            
	            
	            // 2.) All Done Clean Up AWS - Move from Staging to Storage
	           // saveUploadStorageToAttachment(productDTO, s3is, destinationKey, uniqueAsset);
	            
	            saveCopyUploadStorageToAttachment(s3Object, uniqueAsset);
	                 	            
		
			} catch (Exception e) {
				logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
			}			
		}
		
		

// 1-23-2024 return to page 4, says success, then to catalog list		
//		// 6-14-2023
//		List <UniversalCatalog> ucatList = universalCatalogDao.getAll();		
//	   	model.addAttribute("ucatList", ucatList);	
//	   	
//	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
//
//		return "fragments/upload";
		
		return "fragments/upload-step4";
		
	}

	
	
	
	
	
	/**
	 * 	6-14-2023
	 *   The Uploaded File was Under Ground Cable, this is the routine to parse thru that file and create the database entries from that.
	 *   The step after this would be to save the uploaded file to the attachments table.
	 */
	private boolean processSaveToDB_Steel( S3ObjectInputStream s3is, UniqueAsset uniqueAsset ) {
		
		JSONObject jsonObject = new JSONObject();
		
        // Create Workbook for this file in AWS S3, passed from upload page 3 SUBMIT
        try {
			XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = null;
		
            // Loop thru the rows in this file
            //UniqueAsset uniqueAsset = new UniqueAsset();
 		            
            boolean notFinished = true;            
        	while (rowIterator.hasNext() && notFinished) {	        	
				row = rowIterator.next();												
				int len = row.getLastCellNum();
					
				// Loop thru the cells on a row
				if (( row.getCell(0) != null ) && ( row.getCell(1) != null )   ){					
					// print out all the cells on this row
					String rowCellValue = "";
					for ( int i = 0; len > i ; i++) {								
						rowCellValue = row.getCell(i).toString();
						System.out.print(rowCellValue);							
						if(len-1 == i) 	{
							// do nothing
						} else {
							System.out.print(",");	
														
							// 2-1-2024  								
							jsonObject.put(rowCellValue, row.getCell(i+1).toString());
														
							if (rowCellValue.equalsIgnoreCase("catalog_id")) {
								
								// TODO add DB lookup to use text to get pkId
								uniqueAsset.setUcatPkId(804); // Steel
								
								// 2-1-2024 TODO this needs to come FROM the DB, previously built when the catalog was constructed
								//uniqueAsset.setUniqueAssetId(row.getCell(i+1).toString());								
								uniqueAsset.setUniqueAssetId("131011215G9");
								
								
								// 2-1-2024   default value db lookup is below
								uniqueAsset.setManufacturerPkId(55); // real value in the table
								
								// 2-1-2024 TODO
								//  Not sure if customer is required here during the Asset Creation process.
								//  by definition, i think the MTR is for a customer ??
								uniqueAsset.setCustomerPkId(2);  // ACME Utilities
								
								// 2-1-2024 Pipe uses HEAT by definition
								uniqueAsset.setTraceTypePkId(40); // heat	
								
								// 2-1-2024 The heat number default
								uniqueAsset.setTraceCode("535521");
								
								uniqueAsset.setAddBy("digitalMtr");
								
							} else if (rowCellValue.equalsIgnoreCase("Facility_Name_for_Pipe_Manufacturer")) {
								
								// 2-1-2024 TODO add DB lookup to use text to get the pkId
								uniqueAsset.setManufacturerPkId(55);
													
							} else if (rowCellValue.equalsIgnoreCase("heat_number")) {
								
								// 2-1-2024 Override the default with the real value in the excel files
								uniqueAsset.setTraceCode(row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("xxxxxx")) {
			
							}     
																																		
						}							
					}	
					System.out.println();
				}
        	}
		
        	// Validate the required fields
        	if (uniqueAsset.getUcatPkId() > 0) {
        		logger.debug(" Found a new Unique Asset / Catalogue Product, to insert to the Unique Asset Table.");		        				        	
        		uniqueAssetDao.save(uniqueAsset);
        		logger.debug(" Unique Asset Table ->: saved.");	
        		
        		
        		
        		// 2-1-2024
        		// Save all the stripped off attributes to the attributes table as json string.
                // Convert the POJO array to json, for the UI

        		
        		ProductAttribute productAttribute = new ProductAttribute();
        		productAttribute.setUcatPkId(804); // Steel Pipe
        		productAttribute.setAddBy("digitalAssetCable");        		
        		productAttribute.setAttributesJson(jsonObject.toString());
        	
        		productAttributeDao.delete(productAttribute);        		
        		productAttributeDao.save(productAttribute);
        		
        		
        		
        	}
        			        	
        } catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
        
		return true;
	}

	
	
	
	/**
	 * 	6-14-2023
	 *   The Uploaded File was Under Ground Cable, this is the routine to parse thru that file and create the database entries from that.
	 *   The step after this would be to save the uploaded file to the attachments table.
	 */
	private boolean processSaveToDB_Cable( S3ObjectInputStream s3is,  UniqueAsset uniqueAsset ) {
				
		
		JSONObject jsonObject = new JSONObject();
		
	    // Create Workbook for this file in AWS S3, passed from upload page 3 SUBMIT
        try {
			XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = null;
		 		            
            boolean notFinished = true;            
        	while (rowIterator.hasNext() && notFinished) {	        	
				row = rowIterator.next();
				
				// 6-14-2023
				int len = 2;;
					
				// 8-14-2023
				// Loop thru the cells on a row		
				if (( row.getCell(0) != null ) && ( row.getCell(1) != null )   ){		
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
								
								// TODO add DB lookup to use text to get pkId
								uniqueAsset.setUcatPkId(800); // Under ground Cable															
								uniqueAsset.setCustomerPkId(2);  // ACME Utilities
								uniqueAsset.setTraceTypePkId(40); // heat									
								uniqueAsset.setAddBy("digitalAssetCable");
								jsonObject.put("catalog_id", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("unique_asset_id")) {
								
								uniqueAsset.setUniqueAssetId(row.getCell(i+1).toString());								
								jsonObject.put("unique_asset_id", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("reel_id")) {
								
								jsonObject.put("reel_id", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("customer_part_number")) {
								
								jsonObject.put("customer_part_number", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("customer_po_number")) {
								
								jsonObject.put("customer_po_number", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("manufacturer")) {
								
								// TODO 6-14-2023   The value from the upload files, needs to read from the DB, to set the PkID,
								// and default to unknown if not found
								uniqueAsset.setManufacturerPkId(50); // SouthEastern Wire
								jsonObject.put("manufacturer", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("lot_code")) {								
								uniqueAsset.setTraceCode(row.getCell(i+1).toString());	
								jsonObject.put("lot_code", row.getCell(i+1).toString());
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
        		logger.debug(" Unique Asset Table ->: saved.");	
        		
        		
        		// TODO 8-24-2023
        		// Save all the stripped off attributes to the attributes table as json string.
                // Convert the POJO array to json, for the UI

        		
        		ProductAttribute productAttribute = new ProductAttribute();
        		productAttribute.setUcatPkId(800); // Under ground Cable        		
        		productAttribute.setAddBy("digitalAssetCable");        		
        		productAttribute.setAttributesJson(jsonObject.toString());
        	
        		productAttributeDao.delete(productAttribute);        		
        		productAttributeDao.save(productAttribute);
        		
        	}
        			        	
        } catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
        
        
		return true;
	}

	
	
	
	/**
	 * 	8-17-2023
	 * 		Splice
	 */
	private boolean processSaveToDB_Splice( S3ObjectInputStream s3is,  UniqueAsset uniqueAsset ) {
				
		
		JSONObject jsonObject = new JSONObject();
		
	    // Create Workbook for this file in AWS S3, passed from upload page 3 SUBMIT
        try {
			XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = null;
		 		            
            boolean notFinished = true;            
        	while (rowIterator.hasNext() && notFinished) {	        	
				row = rowIterator.next();			
				int len = 2;;
					
				// Loop thru the cells on a row		
				if (( row.getCell(0) != null ) && ( row.getCell(1) != null )   ){		
					// print out all the cells on this row
					String rowCellValue = "";
					for ( int i = 0; len > i ; i++) {								
						rowCellValue = row.getCell(i).toString();
						System.out.print(rowCellValue);							
						if(len-1 == i) 	{
							// do nothing
						} else {
							System.out.print(",");	
							
							//  8-22-2023 TODO This should just handle them all ? 								
							jsonObject.put(rowCellValue, row.getCell(i+1).toString());
							
							
							
							if (rowCellValue.equalsIgnoreCase("catalog_id")) {
								
								// TODO add DB lookup to use text to get pkId
								uniqueAsset.setUcatPkId(805); // Splice														
								uniqueAsset.setCustomerPkId(2);  // ACME Utilities
								uniqueAsset.setTraceTypePkId(40); // heat									
								uniqueAsset.setAddBy("digitalAssetSplice");
								//jsonObject.put("catalog_id", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("unique_asset_id")) {
								
								uniqueAsset.setUniqueAssetId(row.getCell(i+1).toString());								
								//jsonObject.put("unique_asset_id", row.getCell(i+1).toString());
								
//							} else if (rowCellValue.equalsIgnoreCase("reel_id")) {
//								
//								jsonObject.put("reel_id", row.getCell(i+1).toString());
//								
//							} else if (rowCellValue.equalsIgnoreCase("customer_part_number")) {
//								
//								jsonObject.put("customer_part_number", row.getCell(i+1).toString());
//								
//							} else if (rowCellValue.equalsIgnoreCase("customer_po_number")) {
//								
//								jsonObject.put("customer_po_number", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("manufacturer")) {
								
								// TODO 6-14-2023   The value from the upload files, needs to read from the DB, to set the PkID,
								// and default to unknown if not found
								uniqueAsset.setManufacturerPkId(56); // Richards
								//jsonObject.put("manufacturer", row.getCell(i+1).toString());
								
							} else if (rowCellValue.equalsIgnoreCase("lot_code")) {								
								uniqueAsset.setTraceCode(row.getCell(i+1).toString());	
								//jsonObject.put("lot_code", row.getCell(i+1).toString());
								
							}  else if (rowCellValue.equalsIgnoreCase("serial_number")) {	
								
								// Put serial number in lot_code
								uniqueAsset.setTraceCode(row.getCell(i+1).toString());	
								//jsonObject.put("lot_code", row.getCell(i+1).toString());
								
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
        		logger.debug(" Unique Asset Table ->: saved.");	
        		
        		
        		// TODO 8-24-2023
        		// Save all the stripped off attributes to the attributes table as json string.
                // Convert the POJO array to json, for the UI

        		
        		ProductAttribute productAttribute = new ProductAttribute();
        		productAttribute.setUcatPkId(805); // Under ground Cable        		
        		productAttribute.setAddBy("digitalAssetSplice");        		
        		productAttribute.setAttributesJson(jsonObject.toString());
        	
        		productAttributeDao.delete(productAttribute);        		
        		productAttributeDao.save(productAttribute);
        		
        	}
        			        	
        } catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
        
        
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	

    // The Upload saved to the database correctly, so now Clean up AWS
	//  move the staged file to storage.
	private boolean processCopyStageToStorage( S3Object s3Object ) {
	
        CopyObjectRequest copyObjRequest;
        
        String sourceKey = s3Object.getKey();                       
        String destinationKey = sourceKey.replace("staging", "storage");            
     	logger.debug("    moving  ->: " + sourceKey);	 
     	logger.debug("       to   ->: " + destinationKey);	
		
        copyObjRequest = new CopyObjectRequest(awsS3BucketName, sourceKey, awsS3BucketName, destinationKey);
        awsS3Client.copyObject(copyObjRequest);
        
        // remove it from the staging folder
        awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, sourceKey));
        logger.debug("     remove the staging file successfully.");	
                        
		return true;
	}

	
	
	// 6-19-2023
    // The Upload File saved successfully to create an Asset in the DB
    //     - Save Storage File to the Asset Attachments Table in the DB
	
	private boolean saveCopyUploadStorageToAttachment(S3Object s3Object, UniqueAsset uniqueAsset ) {
		
	   	logger.debug("   saveCopyUploadStorageToAttachment" );	
	   	
		   		   	
        CopyObjectRequest copyObjRequest;
        
        String sourceKey = s3Object.getKey();                       
        sourceKey = sourceKey.replace("staging", "storage");  
        String destinationKey = sourceKey.replace("upload", "attachment");  
        
     	logger.debug("    moving  ->: " + sourceKey);	 
     	logger.debug("       to   ->: " + destinationKey);	
		
        copyObjRequest = new CopyObjectRequest(awsS3BucketName, sourceKey, awsS3BucketName, destinationKey);
        awsS3Client.copyObject(copyObjRequest);
        
        logger.debug("     saved to AWS attachments successfully.");
	   	
                               
        // Insert to DB for a successful copy
             	
     	ProductAttachment productAttachment = new ProductAttachment();
      	productAttachment.setUniqueAssetPkId(Integer.valueOf(uniqueAsset.getUniqueAssetPkId()));
     	productAttachment.setAddBy("fileUpload");      
     	
     	// TODO maybe ADD just the filename, instead of the whole path for the UI
     	productAttachment.setFilenameFullpath(destinationKey);   
     	
     	// TODO  pkId 1 = Generic General Attachment, need to enhance this when the doctype is selected on upload.
     	productAttachment.setDocTypePkId(1);   
     	productAttachmentDao.save(productAttachment);
     	
        logger.debug("     Inserted to the database successfully.");	
	   	
		return true;
	}
	
	


	
	
	    
	    
}