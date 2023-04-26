package com.locuslink.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.Tag;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.DashboardFormDTO;


@Service
public class AwsS3FileLogic {
		
	private static final Logger logger = Logger.getLogger(AwsS3FileLogic.class);
	
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
	
	
	
	/**
	 *   4-26-2023 need this to share code once we delete from stage and save to storage, etc.
	 *   can wait for now.
	 */
	public boolean processAwsS2FileSave(String targetFullpathFileName ) {
		
		logger.debug("Starting processAwsS2FileSave()..targetFullpathFileName ->:" + targetFullpathFileName);

//		try {
//			 
//            final ObjectMetadata metaData = new ObjectMetadata();
//            metaData.setContentType(inputfile.getContentType());
//            metaData.setContentLength(inputfile.getSize());
//            
//            // create and call S3 request to create the new S3 object 
//            PutObjectRequest putObjectRequest = new PutObjectRequest(
//            	awsS3BucketName, 
//            	fullpathFileName_keyName, // file/object name in S3
//            	inputfile.getInputStream(), // input stream from the Multipart
//                metaData // created above, with the only content type and size
//            );
//            
//            // Tags for easier process on retrieval
//            List<Tag> tags = new ArrayList<Tag>();
//            tags.add(new Tag("filename", inputfile.getOriginalFilename()));
//            putObjectRequest.setTagging(new ObjectTagging(tags));
//            
//            PutObjectResult putObjectResult = awsS3Client.putObject(putObjectRequest);
//            	        		
//			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
//			
//			logger.debug(" CSV fileUpload Worked,  size ->: " + inputfile.getSize());
//			//logger.debug("         result details ->: " + putObjectResult.g);
//
//		} catch (Exception e) {
//			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
//		}
		return true;
	}

	
	
}
