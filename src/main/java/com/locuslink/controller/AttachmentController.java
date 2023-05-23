package com.locuslink.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingResult;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.model.ProductAttachment;

/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class AttachmentController {

	private static final Logger logger = Logger.getLogger(AttachmentController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;
    
    @Autowired
    private ProductAttachmentDao productAttachmentDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;

    
    
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.attachment.staging.fullpath}")
	private String attachmentStagingFullpath;
	
	@Value("${file.attachment.storage.fullpath}")
	private String attachmentStorageFullpath;
	

    


	
	//*************************************************************//
	//*******      A T T A C H M E N T   D A T A       ************//
	//*************************************************************//

    
	@PostMapping(value = "/initAssetAttachments")
	public String getAllAssetAttachments (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getAllAssetAttachments()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_list";
	}
	
	
    
	@PostMapping(value = "/deleteAssetAttachment")
	public String deleteAssetAttachment (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting deleteAssetAttachment()...");

		
		// TODO Delete the clicked on ROW from AWS and the Database
		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(dashboardFormDTO.getProductAttachPkId()));
		if (productAttachment == null) {
			logger.debug("  Error:  Product Attachment Lookup failed...");
		}
		
		// AWS Remove the attachment from the Storge Area			
        awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, productAttachment.getFilenameFullpath()));
        logger.debug("     remove the AWS STORAGE file successfully.");	 
        
        // Database
        productAttachmentDao.delete(productAttachment);
		
        
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_list";
	}
	
	
	
	
	/**
	 *   5-17-2023   Get all the attachments for a clicked Asset.
	 */		
	@RequestMapping(value = "/getAllAssetAttachments", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllAssetAttachments(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllAssetAttachments()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllAssetAttachments");
	  	
		
		String uniqueAssetPkId = request.getFieldAsString("uniqueAssetPkId");
		if (uniqueAssetPkId == null || uniqueAssetPkId.length() < 1) {
			logger.debug("Error - uniqueAssetPkId is missing");
		} else {
			logger.debug("  uniqueAssetPkId ->: " + uniqueAssetPkId);
		}
		
		// TESTING
		List <ProductAttachmentDTO> productAttachmentListDTO =  productAttachmentDao.getDtoByUniqueAssetId(Integer.valueOf(uniqueAssetPkId));
		if (productAttachmentListDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
			
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(productAttachmentListDTO);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("assetAttachmentlist",  json);

		return response;
	 }

	
	
	
	
	@PostMapping(value = "/getAttachmentForAsset")
	public String getAttachmentForAsset (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
	
		logger.debug("Starting getAttachmentForAsset()...");

		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(dashboardFormDTO.getProductAttachPkId()));
		if (productAttachment == null) {
			logger.debug("  Error:  Product Attachment Lookup failed...");
		}
					
        GetObjectRequest request = new GetObjectRequest(
        	awsS3BucketName, 
        	productAttachment.getFilenameFullpath() );
                
        S3Object s3Object = awsS3Client.getObject(request);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
          
		ByteArrayResource byteArrayResource = null;
		try {
			byteArrayResource = new ByteArrayResource( inputStream.readAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}				
		String encodedPDFBarcdeString = Base64.getEncoder().encodeToString(byteArrayResource.getByteArray());
				
	   	model.addAttribute("encodedPDFBarcdeString", encodedPDFBarcdeString);		   	
	   	model.addAttribute("productAttachPkId", productAttachment.getProductAttachPkId());		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_viewer";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *  05-19-2023 - C.Sparks
	 *  
	 *  This is a JSON method because we return to the same screen to display a status for the "dropped" files on the UI.
	 *  This method will write the dropped files to a staging folder. The next step will save to the DB and permanent storage
	 */		
	@PostMapping(value = "/processAttachmentUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processXlsFileUpload(@RequestParam("file") MultipartFile inputfile,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

		logger.debug("Starting processAttachmentUpload()..inputfile ->:" + inputfile.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "trace - processXlsFileUpload");

		try {			
			if (inputfile.getOriginalFilename().contains(".pdf")) {
				// do nothing, all good
			} else {
				logger.debug("  ERROR xlsFileUpload failed ->: Wrong File extension. " );
				return response;
			}

			// 5-19-2023 PRefix with the uniqueAssetPkID to avoid collisions
			String prefixUniqueAssetPkId = dashboardFormDTO.getUniqueAssetPkId()+ "_";
			
	        // String fullpathFileName_keyName = attachmentStagingFullpath + inputfile.getOriginalFilename();	
			String fullpathFileName_keyName = attachmentStagingFullpath +  prefixUniqueAssetPkId + inputfile.getOriginalFilename();	
			
			
			
			
			// TODO 5-11-2023   Dont allow duplicate files uploaded to the same Asset.
			if (!checkFileIsValideToUpload(prefixUniqueAssetPkId + inputfile.getOriginalFilename())) {
				logger.debug("  ERROR duplicate file upload not allowed for the same asset. " );
				response.setError(1);
				response.setErrorMessage(" ERROR. You cannot upload duplicate file for the same asset.");				
				response.setField("uploadedFilenameInError", inputfile.getOriginalFilename());
				return response;				
			}

			
			

			 
			 
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
            awsS3Client.putObject(putObjectRequest);
            	        		
			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" Attachment Upload Worked,  size ->: " + inputfile.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
		}
		return response;
	}
		
	
	// Check for Duplicate File
	public boolean checkFileIsValideToUpload (String fullpathFileName_keyName) {
		
		logger.debug("Starting checkFileIsValideToUpload()...");

	    // Gets the list of just files, under the directory structure {tag name}
	    ListObjectsRequest listObjectsRequest_staging = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(attachmentStagingFullpath)
	            .withMarker(attachmentStagingFullpath);
	    	              	
       //Check Staging for Duplicate
	    boolean dupFound = false;
        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest_staging)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
        	
        	if (s3ObjectSummary.getKey().contains(fullpathFileName_keyName)) {
        		logger.debug("    STAGING duplicate file found ->: " + s3ObjectSummary.getKey());	
        		dupFound = true;   
        		return false;
        	} 
 
        }
        
        
        // Check Storage for Duplicate
	    ListObjectsRequest listObjectsRequest_storage = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(attachmentStorageFullpath)
	            .withMarker(attachmentStorageFullpath);

        s3ObjectList = awsS3Client.listObjects(listObjectsRequest_storage)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
        	
        	if (s3ObjectSummary.getKey().contains(fullpathFileName_keyName)) {
        		logger.debug("    STORAGE duplicate file found ->: " + s3ObjectSummary.getKey());	
        		dupFound = true;   
        		return false;
        	} 
        	
        }
	    
	    
		return true;
	}
	
	
	
	/**
	 *   04-26-2023 C.Sparks
	 *   Attachment List has an ADD function, after Dropzone already called, and it loaded Staging,
	 *    it calls this to move the staging files to the  the AWS S3 Storage bucket, 
	 *    and insert into the database an attachment record with the filename and path.
	 */
	@RequestMapping(value = "/deleteFileUpload", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse deleteFileUpload(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In deleteFileUpload()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "deleteFileUpload");
		
		
		// Step 1 - One File or Many files to delete
		String removeFilename =  request.getFieldAsString("removeFileName");
		String uniqueAssetPkId =  request.getFieldAsString("uniqueAssetPkId");
		String deleteAllFilesWithPk = request.getFieldAsString("deleteAllFilesWithPk");
		
		if (deleteAllFilesWithPk.toUpperCase().equals("YES")) {
			
			// Cancel Button Hit -  Delete from AWS Staging all files with this uniqueAssetPkID prefix
    	    ListObjectsRequest listObjectsRequest_staging = new ListObjectsRequest()
    	            .withBucketName(awsS3BucketName)
    	            .withPrefix(attachmentStagingFullpath)
    	            .withMarker(attachmentStagingFullpath);
    	    
        	
			String fullpathWildCardFileName_keyName = attachmentStagingFullpath +  uniqueAssetPkId + "_" ;					
            ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest_staging)	 ;       		
            for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
            	
            	if (s3ObjectSummary.getKey().contains(fullpathWildCardFileName_keyName)) {
            		logger.debug("    STAGING file found ->: " + s3ObjectSummary.getKey());	                      
                    awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName,  s3ObjectSummary.getKey()));            		                   
                    logger.debug("     remove the staging file successfully.");	                    
            	}

            }
                     
		} else {			
			// Remove Link on one file clicked from the UI - Delete from AWS Staging this file from AWS Staging
			String fullpathFileName_keyName = attachmentStagingFullpath +  uniqueAssetPkId + "_" + removeFilename;					
            awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, fullpathFileName_keyName));
            logger.debug("     remove the staging file successfully.");	            
			
		}
		

		
		
		
		return response;		
	}
	
	
	
	
	/**
	 *   04-26-2023 C.Sparks
	 *   Attachment List has an ADD function, after Dropzone already called, and it loaded Staging,
	 *    it calls this to move the staging files to the  the AWS S3 Storage bucket, 
	 *    and insert into the database an attachment record with the filename and path.
	 */
	@RequestMapping(value = "/saveAttachmentsFromStagingToStorage", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse saveAttachmentsFromStagingToStorage(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In saveAttachmentsFromStagingToStorage()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "saveAttachmentsFromStagingToStorage");
	  		
		String uniqueAssetPkId = request.getFieldAsString("uniqueAssetPkId");
		if (uniqueAssetPkId == null || uniqueAssetPkId.length() < 1) {
			logger.debug("Error ->: missing uniqueAssetPkId. ");
		}
		
	    // Gets the list of just files, under the directory structure {tag name}
	    ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(attachmentStagingFullpath)
	            .withMarker(attachmentStagingFullpath);
          		
       // Row row = null;
        S3Object s3Object;
        CopyObjectRequest copyObjRequest;
        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
           
        	System.out.println();
        	logger.debug("    staging file found ->: " + s3ObjectSummary.getKey());	 
        	        	
            GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(awsS3BucketName, s3ObjectSummary.getKey());
            GetObjectTaggingResult getTagsResult = awsS3Client.getObjectTagging(getTaggingRequest);
                        
            String sourceKey = s3ObjectSummary.getKey();                       
            String destinationKey = sourceKey.replace("staging", "storage");            
         	logger.debug("    moving  ->: " + sourceKey);	 
         	logger.debug("       to   ->: " + destinationKey);	 
            
            copyObjRequest = new CopyObjectRequest(awsS3BucketName, sourceKey, awsS3BucketName, destinationKey);
            awsS3Client.copyObject(copyObjRequest);
            
            // remove it from the staging folder
            awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, sourceKey));
            logger.debug("     remove the staging file successfully.");	
            
            // TODO Insert to DB for a successful copy
     
         	
         	ProductAttachment productAttachment = new ProductAttachment();
          	productAttachment.setUniqueAssetPkId(Integer.valueOf(uniqueAssetPkId));
         	productAttachment.setAddBy("attachmentUpload");      
         	
         	// TODO maybe ADD just the filename, instead of the whole path for the UI
         	productAttachment.setFilenameFullpath(destinationKey);   
         	
         	// TODO  pkId 1 = Generic General Attachment, need to enhance this when the doctype is selected on upload.
         	productAttachment.setDocTypePkId(1);   
         	productAttachmentDao.save(productAttachment);
            logger.debug("     Inrested to the database successfully.");	
        }

		return response;
	 }
		 
	
	
	
	
	
}