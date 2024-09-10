package com.locuslink.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.model.AssemblyAttachment;


@Service
public class AwsS3AssemblyFileLogic {
		
	private static final Logger logger = Logger.getLogger(AwsS3AssemblyFileLogic.class);
	
	@Autowired
    private SecurityContextManager securityContextManager;
	

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
	
	
	
	public boolean processAssemblyAttachmentUpload(GenericMessageResponse response, MultipartFile inputfile, String prefixAssemblytPkId ) {

		

		// 5-19-2023 PRefix with the uniqueAssetPkID to avoid collisions
		//String prefixAssemblytPkId = dashboardFormDTO.getAssemblyPkid()+ "_";	
		//String fullpathFileName_keyName = assemblyStagingFullpath +  prefixAssemblytPkId + inputfile.getOriginalFilename();	
       
		// 9-10-2024 need to scrub the file name first.
		String formattedFileName = inputfile.getOriginalFilename().replace(" ","_").replace("(", "").replace(")", "");			
		String fullpathFileName_keyName = assemblyStagingFullpath +  prefixAssemblytPkId + formattedFileName;
		 
		logger.debug("           Name ->: " + fullpathFileName_keyName);
        logger.debug("    Content Type ->: " + inputfile.getContentType());
                
		
		// TODO 5-11-2023   Dont allow duplicate files uploaded to the same Asset.
		if (!checkFileIsValideToUpload(fullpathFileName_keyName)) {
			logger.debug("  ERROR duplicate file upload not allowed for the same assembly. " );
			response.setError(1);
			response.setErrorMessage(" ERROR. You cannot upload duplicate file for the same assembly.");				
			response.setField("uploadedFilenameInError", fullpathFileName_keyName);
			return false;				
		}		
				
		try {						
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
           // tags.add(new Tag("filename", inputfile.getOriginalFilename()));
            tags.add(new Tag("filename", formattedFileName));
            putObjectRequest.setTagging(new ObjectTagging(tags));
            awsS3Client.putObject(putObjectRequest);
            	        		
			//model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" Assembly Upload Worked,  size ->: " + inputfile.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR Document Upload failed ->: " + e.getMessage());
		}
		
		return true;
	}

	
	
	// Check for Duplicate File
	public boolean checkFileIsValideToUpload (String fullpathFileName_keyName) {
		
		logger.debug("Starting checkFileIsValideToUpload()...");
		
		return true;
	}
	
	
	/**
	 *   On Dropzone, the remove link was clicked, or the cancel button clicked.
	 */	
	public boolean processAssemblyAttachmentUploadDelete(GenericMessageRequest request,GenericMessageResponse response ) {

		logger.debug("In processAssemblyAttachmentUploadDelete()");
					
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
								
		return true;		
	}
	
	
	
	public boolean processAssemblyAttachmentStageToStorage(int assemblyPkid, String assemblyDocTypePkid ,String ardPkId ) {
	
		if (assemblyPkid < 1) {
			logger.debug("Error ->: missing assemblyPkid. ");
		}
		if (assemblyDocTypePkid == "" || assemblyDocTypePkid.equalsIgnoreCase("")) {
			logger.debug("Error ->: missing assemblyDocTypePkid. ");
		}
		if (ardPkId == "" || ardPkId.equalsIgnoreCase("")) {
			logger.debug("Error ->: missing ardPkId. ");
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
               
            logger.debug("    Insert to DB pkID ->: " + assemblyPkid );	                     	
            AssemblyAttachment assemblyAttachment = new AssemblyAttachment();
            assemblyAttachment.setAssemblyPkid(assemblyPkid);            
            assemblyAttachment.setAddBy("assemblyUpload");      
         	
            assemblyAttachment.setFilenameFullpath(destinationKey);   
            assemblyAttachment.setDocTypePkid(Integer.valueOf(assemblyDocTypePkid));   
            assemblyAttachment.setArdPkid(Integer.valueOf(ardPkId));                                     
            assemblyAttachment.setFilenameFullpath(tagFileName);     
            
            // 7-2-2024
            assemblyAttachment.setAttrFlag("Not Processed.");
            
            assemblyAttachmentDao.save(assemblyAttachment);
                        
            logger.debug("     Inserted to the database successfully.");	
        }

	 	
		return true;
	
	}
	
	
	
}
