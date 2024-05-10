package com.locuslink.logic;

import java.io.IOException;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFileSupplier;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.DocumentMetadata;
import software.amazon.awssdk.services.textract.model.S3Object;
import software.amazon.awssdk.services.textract.model.TextractException;


//import com.amazonaws.services.textract.model.S3Object;





@Service
public class AwsTextractLogic {
		
	private static final Logger logger = Logger.getLogger(AwsTextractLogic.class);
	
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.attachment.staging.fullpath}")
	private String attachmentStagingFullpath;
	
	@Value("${file.attachment.storage.fullpath}")
	private String attachmentStorageFullpath;
	
	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;
	
	@Autowired
	private Environment env;
	
	/**
	 *   C.Sparks 4-19-2024
	 *   	We will define the logic methods needed
	 */
	public boolean process_1( String assemblyPkId ) {
		
		logger.debug("Starting process_1() " );

		// 1.) TODO get the filename from the DB when its ready, for the assembly MTR being worked on.
//		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(uniqueAssetDTO.getProductAttachPkId()));
//		if (productAttachment == null) {
//			logger.debug("  Error:  Product Attachment Lookup failed...");
//		}
		

		// TODO 5-9-2024  
		processAWSTextract();
		
		return true;
	}

	private String processAWSTextract () {
				
		TextractClient textractClient  = TextractClient.builder()
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
				.region(Region.US_EAST_1)
				.build();
				
	     detectDocTextS3(textractClient, awsS3BucketName, "dev/files/assembly/storage/71_0123001D015_12inch_52 Pipe.pdf");
	     textractClient.close();
	     
	     return "";	     
	}
	
	
	
	 public static void detectDocTextS3(TextractClient textractClient, String bucketName, String docName) {
		 
	        try {
	        	
	        	S3Object s3Object = S3Object.builder()
	                    .bucket(bucketName)
	                    .name(docName)
	                    .build();

	            // Create a Document object and reference the s3Object instance.
	            Document myDoc = Document.builder()
	                    .s3Object(s3Object)
	                    .build();

	            DetectDocumentTextRequest detectDocumentTextRequest = DetectDocumentTextRequest.builder()
	                    .document(myDoc)
	                    .build();

	            // TODO 5-10-2024
	            // Says it can take a PDF, but throws an error
	            DetectDocumentTextResponse textResponse = textractClient.detectDocumentText(detectDocumentTextRequest);

	            for (Block block : textResponse.blocks()) {
	                System.out.println("The block type is " + block.blockType().toString());
	            }

	            DocumentMetadata documentMetadata = textResponse.documentMetadata();
	            System.out.println("The number of pages in the document is " + documentMetadata.pages());

	        } catch (TextractException e) {

	            System.err.println(e.getMessage());
	            System.exit(1);
	        }
	    }
	 
	
	
}
