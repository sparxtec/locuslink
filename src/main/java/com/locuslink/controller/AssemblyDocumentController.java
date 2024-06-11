package com.locuslink.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.Tag;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dao.AssemblyReqDocDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.model.AssemblyAttachment;
import com.locuslink.model.AssemblyReqDoc;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2024 - Initial version
 */
@Controller
@Service
public class AssemblyDocumentController {

	private static final Logger logger = Logger.getLogger(AssemblyDocumentController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private AssemblyReqDocDao assemblyReqDocDao;
    
    @Autowired
    private AssemblyAttachmentDao assemblyAttachmentDao;
    
    @Autowired
    private AssemblyDao assemblyDao;
    
    
    
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.assembly.staging.fullpath}")
	private String assemblyStagingFullpath;
	
	@Value("${file.assembly.storage.fullpath}")
	private String assemblyStorageFullpath;
	
	
	
    

	@PostMapping(value = "initAssemblyDocument")
	public String initAssemblyDocument (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssemblyDocument()...");

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Working on Assembly id ->: " + assemblyPkid);
		
		
		List<AssemblyReqDoc> assemblyReqDocList = assemblyReqDocDao.getAllById(Integer.valueOf(assemblyPkid) );
		
		
		// TODO Get the list of upload documents for this Assembly
		List<AssemblyAttachment> assemblyAttachmentList = assemblyAttachmentDao.getAllById(Integer.valueOf(assemblyPkid) );
		
		List<AssemblyAttachmentDTO> assemblyAttachmentDTOList = assemblyAttachmentDao.getAllDTObyAssemblyId(Integer.valueOf(assemblyPkid) );
		
		
	 	model.addAttribute("assemblyReqDocList", assemblyReqDocList);
		model.addAttribute("assemblyAttachmentList", assemblyAttachmentList);
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-document";
	}
	
	
	
	
	
	/**
	 *  05-30-2024 - C.Sparks
	 *  
	 *  Assemblies have regular upload for Required Docs, and AI assist upload.
	 *  
	 */		
	@PostMapping(value = "/processAssemblyReqDocUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processAssemblyReqDocUpload(@RequestParam("file") MultipartFile inputfile,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

		logger.debug("Starting processAssemblyReqDocUpload()..inputfile ->:" + inputfile.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "Upload");

		try {			
			if (inputfile.getOriginalFilename().contains(".pdf")) {
				// do nothing, all good
			} else {
				logger.debug("  ERROR xlsFileUpload failed ->: Wrong File extension. " );
				return response;
			}

			// 5-19-2023 PRefix with the uniqueAssetPkID to avoid collisions
			String prefixAssemblytPkId = dashboardFormDTO.getAssemblyPkid()+ "_";	
			String fullpathFileName_keyName = assemblyStagingFullpath +  prefixAssemblytPkId + inputfile.getOriginalFilename();	
			
						
			// TODO 5-11-2023   Dont allow duplicate files uploaded to the same Asset.
			if (!checkFileIsValideToUpload(prefixAssemblytPkId + inputfile.getOriginalFilename())) {
				logger.debug("  ERROR duplicate file upload not allowed for the same assembly. " );
				response.setError(1);
				response.setErrorMessage(" ERROR. You cannot upload duplicate file for the same assembly.");				
				response.setField("uploadedFilenameInError", inputfile.getOriginalFilename());
				return response;				
			}

				 
			 
	        logger.debug("           Name ->: " + fullpathFileName_keyName);
	        logger.debug("    Content Type ->: " + inputfile.getContentType());
        	        
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
			
			logger.debug(" Assembly Upload Worked,  size ->: " + inputfile.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR Document Upload failed ->: " + e.getMessage());
		}
		return response;
	}
		
	
	// Check for Duplicate File
	public boolean checkFileIsValideToUpload (String fullpathFileName_keyName) {
		
		logger.debug("Starting checkFileIsValideToUpload()...");

		
		// TODO  5-30-2024
		
		/*
		 * // Gets the list of just files, under the directory structure {tag name}
		 * ListObjectsRequest listObjectsRequest_staging = new ListObjectsRequest()
		 * .withBucketName(awsS3BucketName) .withPrefix(attachmentStagingFullpath)
		 * .withMarker(attachmentStagingFullpath);
		 * 
		 * //Check Staging for Duplicate boolean dupFound = false; ObjectListing
		 * s3ObjectList = awsS3Client.listObjects(listObjectsRequest_staging) ;
		 * for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) { if
		 * (s3ObjectSummary.getKey().contains(fullpathFileName_keyName)) {
		 * logger.debug("    STAGING duplicate file found ->: " +
		 * s3ObjectSummary.getKey()); dupFound = true; return false; }
		 * 
		 * }
		 * 
		 * 
		 * // Check Storage for Duplicate ListObjectsRequest listObjectsRequest_storage
		 * = new ListObjectsRequest() .withBucketName(awsS3BucketName)
		 * .withPrefix(attachmentStorageFullpath)
		 * .withMarker(attachmentStorageFullpath);
		 * 
		 * s3ObjectList = awsS3Client.listObjects(listObjectsRequest_storage) ;
		 * for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) { if
		 * (s3ObjectSummary.getKey().contains(fullpathFileName_keyName)) {
		 * logger.debug("    STORAGE duplicate file found ->: " +
		 * s3ObjectSummary.getKey()); dupFound = true; return false; } }
		 */    
	    
		return true;
	}
	   	
	/**
	 * 6-10-2024  ASSEMBLY - Attachment ADD via Dropzone.
	 *       Once an attachment is droped in dropzone buffer, it was added to AWS staging.
	 */
	@RequestMapping(value = "/deleteAsseblyFileUpload", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse deleteAsseblyFileUpload(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In deleteAsseblyFileUpload()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "deleteFileUpload");
		
		
		// Step 1 - One File or Many files to delete
		String removeFilename =  request.getFieldAsString("removeFileName");
		String assemblyPkid =  request.getFieldAsString("assemblyPkid");
		String deleteAllFilesWithPk = request.getFieldAsString("deleteAllFilesWithPk");
		
		if (deleteAllFilesWithPk.toUpperCase().equals("YES")) {
			
			// Cancel Button Hit -  Delete from AWS Staging all files with this uniqueAssetPkID prefix
    	    ListObjectsRequest listObjectsRequest_staging = new ListObjectsRequest()
    	            .withBucketName(awsS3BucketName)
    	            .withPrefix(assemblyStagingFullpath)
    	            .withMarker(assemblyStagingFullpath);
    	            	
			String fullpathWildCardFileName_keyName = assemblyStagingFullpath +  assemblyPkid + "_" ;					
            ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest_staging)	 ;       		
            for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {
            	
            	if (s3ObjectSummary.getKey().contains(fullpathWildCardFileName_keyName)) {
            		logger.debug("    STAGING file found ->: " + s3ObjectSummary.getKey());	                      
                    awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName,  s3ObjectSummary.getKey()));            		                   
                    logger.debug("     remove the staging file successfully ->: " + s3ObjectSummary.getKey());	                  
            	}
            }
                     
		} else {			
			// Remove Link on one file clicked from the UI - Delete from AWS Staging this file from AWS Staging
			String fullpathFileName_keyName = assemblyStagingFullpath +  assemblyPkid + "_" + removeFilename;					
            awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, fullpathFileName_keyName));
            logger.debug("     remove the staging file successfully ->: " + fullpathFileName_keyName);	            			
		}
								
		return response;		
	}
	
	
	
	
	/**
	 *   06-10-2024 C.Sparks
	 *   ASSEMBLY - Attachment List has an ADD function, after Dropzone already called, and it loaded Staging,
	 *    it calls this to move the staging files to the  the AWS S3 Storage bucket, 
	 *    and insert into the database an attachment record with the filename and path.
	 */
	
	@PostMapping(value = "/saveAssemblyAttachmentsFromStagingToStorage")
	public String saveAssemblyAttachmentsFromStagingToStorage (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
	
		logger.debug("In saveAssemblyAttachmentsFromStagingToStorage()");
	  		
		//String uniqueAssetPkId = request.getFieldAsString("uniqueAssetPkId");
		int assemblyPkid = Integer.valueOf(dashboardFormDTO.getAssemblyPkid());		
		String assemblyDocTypePkid = dashboardFormDTO.getAssemblyDocTypePkId();
		
		if (assemblyPkid < 1) {
			logger.debug("Error ->: missing assemblyPkid. ");
		}
		if (assemblyDocTypePkid == "" || assemblyDocTypePkid.equalsIgnoreCase("")) {
			logger.debug("Error ->: missing assemblyDocTypePkid. ");
		}
		
	    // Gets the list of just files, under the directory structure {tag name}
	    ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(awsS3BucketName)
	            .withPrefix(assemblyStagingFullpath)
	            .withMarker(assemblyStagingFullpath);
          		
       // Row row = null;
        S3Object s3Object;
        CopyObjectRequest copyObjRequest;
        ObjectListing s3ObjectList = awsS3Client.listObjects(listObjectsRequest)	 ;       		
        for(S3ObjectSummary s3ObjectSummary : s3ObjectList.getObjectSummaries()) {           
        	System.out.println();
        	logger.debug("    staging file found ->: " + s3ObjectSummary.getKey());	 
        	        	
            GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(awsS3BucketName, s3ObjectSummary.getKey());
            GetObjectTaggingResult getTagsResult = awsS3Client.getObjectTagging(getTaggingRequest);
                                    
            // 2-21-2024 get the filename from a tag when uploaded
            String tagFileName = "unknown";
            String tagProductType = "unknown";
            List <Tag> tagList = getTagsResult.getTagSet();
            for (Tag tag :tagList ) {
            	logger.debug("    s3 tags found ->: " + tag.getKey() + " : " + tag.getValue());	 
            	if (tag.getKey().equals("filename")) {
            		tagFileName = tag.getValue();
            	}
            	if (tag.getKey().equals("product_type")) {
            		tagProductType = tag.getValue();
            	}
            }
                                    
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
            logger.debug("    Insert to DB pkID ->: " + assemblyPkid );	
            
         	
            AssemblyAttachment assemblyAttachment = new AssemblyAttachment();
            assemblyAttachment.setAssemblyPkid(assemblyPkid);            
            assemblyAttachment.setAddBy("assemblyUpload");      
         	
         	// TODO maybe ADD just the filename, instead of the whole path for the UI
            assemblyAttachment.setFilenameFullpath(destinationKey);   
         	
         	// TODO  pkId 1 = Generic General Attachment, need to enhance this when the doctype is selected on upload.
            assemblyAttachment.setDocTypePkid(Integer.valueOf(assemblyDocTypePkid));   
         	
         	// 2-21-2024
            assemblyAttachment.setFilenameFullpath(tagFileName);
         	
            assemblyAttachmentDao.save(assemblyAttachment);
            
            
            logger.debug("     Inrested to the database successfully.");	
        }

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
        

	   	// 6-10-2024 - Need this so the UI can navigate back to the TAB we want in focus
	   	model.addAttribute("gotoDocumentsTab", "yes");	   	
		AssemblyDTO assemblyDto = assemblyDao.getDtoById(Integer.valueOf(assemblyPkid)  );		
	 	model.addAttribute("assemblyDto", assemblyDto);
	   	
	   	return "fragments/assembly-detail";	   		   	

	 }
		 

}