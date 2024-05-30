package com.locuslink.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.Tag;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyReqDocDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.AssemblyAttachment;
import com.locuslink.model.AssemblyReqDoc;
import com.locuslink.model.ProductAttachment;
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
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.attachment.staging.fullpath}")
	private String attachmentStagingFullpath;
	
	@Value("${file.attachment.storage.fullpath}")
	private String attachmentStorageFullpath;
	
	
	
    

	@PostMapping(value = "initAssemblyDocument")
	public String initAssemblyDocument (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssemblyDocument()...");

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Working on Assembly id ->: " + assemblyPkid);
		
		
		List<AssemblyReqDoc> assemblyReqDocList = assemblyReqDocDao.getAllById(Integer.valueOf(assemblyPkid) );
		
		
		// TODO Get the list of upload documents for this Assembly
		List<AssemblyAttachment> assemblyAttachmentList = assemblyAttachmentDao.getAllById(Integer.valueOf(assemblyPkid) );
		
		
		
	 	model.addAttribute("assemblyReqDocList", assemblyReqDocList);
		model.addAttribute("assemblyAttachmentList", assemblyAttachmentList);
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-document";
	}
	
	
	
	
	
	/**
	 *  05-30-2024 - C.Sparks
	 *  
	 *  Assemblies have reuglar upload for Required Docs, and AI assist upload.
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
			String prefixUniqueAssetPkId = dashboardFormDTO.getUniqueAssetPkId()+ "_";	
			String fullpathFileName_keyName = attachmentStagingFullpath +  prefixUniqueAssetPkId + inputfile.getOriginalFilename();	
			
						
			// TODO 5-11-2023   Dont allow duplicate files uploaded to the same Asset.
			if (!checkFileIsValideToUpload(prefixUniqueAssetPkId + inputfile.getOriginalFilename())) {
				logger.debug("  ERROR duplicate file upload not allowed for the same asset. " );
				response.setError(1);
				response.setErrorMessage(" ERROR. You cannot upload duplicate file for the same asset.");				
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
	   	
	
	

}