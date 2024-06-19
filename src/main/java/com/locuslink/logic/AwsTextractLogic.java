package com.locuslink.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locuslink.model.AssemblyAttachment;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.DocumentLocation;
import software.amazon.awssdk.services.textract.model.DocumentMetadata;
import software.amazon.awssdk.services.textract.model.FeatureType;
import software.amazon.awssdk.services.textract.model.GetDocumentAnalysisRequest;
import software.amazon.awssdk.services.textract.model.GetDocumentAnalysisResponse;
import software.amazon.awssdk.services.textract.model.S3Object;
import software.amazon.awssdk.services.textract.model.StartDocumentAnalysisRequest;
import software.amazon.awssdk.services.textract.model.StartDocumentAnalysisResponse;
import software.amazon.awssdk.services.textract.model.TextractException;


//import com.amazonaws.services.textract.model.S3Object;





@Service
public class AwsTextractLogic {
		
	private static final Logger logger = Logger.getLogger(AwsTextractLogic.class);
	
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	
	@Value("${file.assembly.storage.fullpath}")
	private String assemblyStorageFullpath;
	
	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;
	
	@Autowired
	private Environment env;
	
	/**
	 *   6-19-2024 - Passing in the whole attachement object for now, in case we need more than just the filename.
	 */
	public JsonNode process ( AssemblyAttachment assemblyAttachment) {
				
		TextractClient textractClient  = TextractClient.builder()
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
				.region(Region.US_EAST_1)
				.build();
				
	   //  detectDocTextS3(textractClient, awsS3BucketName, "dev/files/assembly/storage/71_0123001D015_12inch_52 Pipe.pdf");
	   //  textractClient.close();		
		String docName = assemblyStorageFullpath +  assemblyAttachment.getAssemblyPkid() +  "_"   +  assemblyAttachment.getFilenameFullpath();		
	    logger.debug("Working on MTR ->: " + docName);
		
	    String jobId = startDocAnalysisS3(textractClient, awsS3BucketName, docName);   
	    logger.debug("Getting results for job " + jobId);

	    /*
	     * I. Summers 5/31/24
	     * 		Changed var "status" to "results": now contains the OCR results from Textract.
	     * 		"status" now succeeded/failed based on "results". Will change as required.
	     */
	    JsonNode results = null;
		try {
			results = getJobResults(textractClient, jobId);
		} catch (Exception e) {
			logger.debug("Error. AWS Document Analysis." + e);
		} finally {
		    textractClient.close();
		}
	    String status =  results.isNull() ? "failed" : "succeeded";
	    logger.debug("The OCR job" + status);
	    	
	    return results;	     
	}
	
	
	
	public static String startDocAnalysisS3(TextractClient textractClient, String bucketName, String docName) {
		
        try {
            List<FeatureType> myList = new ArrayList<>();
            myList.add(FeatureType.TABLES);

            S3Object s3Object = S3Object.builder()
                    .bucket(bucketName)
                    .name(docName)
                    .build();

            DocumentLocation location = DocumentLocation.builder()
                    .s3Object(s3Object)
                    .build();

            StartDocumentAnalysisRequest documentAnalysisRequest = StartDocumentAnalysisRequest.builder()
                    .documentLocation(location)
                    .featureTypes(myList)
                    .build();

            StartDocumentAnalysisResponse response = textractClient.startDocumentAnalysis(documentAnalysisRequest);

            // Get the job ID
            String jobId = response.jobId();
            return jobId;

        } catch (TextractException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
	}

    private static JsonNode getJobResults(TextractClient textractClient, String jobId) {
    	
        boolean finished = false;
        boolean moreBlocks;
        int index = 0;
        String status;
        JsonNode sortedBlocks = null;

        try {
            while (!finished) {
                GetDocumentAnalysisRequest analysisRequest = GetDocumentAnalysisRequest.builder()
                        .jobId(jobId)
                        .maxResults(1000)
                        .build();

                GetDocumentAnalysisResponse response = textractClient.getDocumentAnalysis(analysisRequest);
                status = response.jobStatus().toString();

                if (status.compareTo("SUCCEEDED") == 0) {
                	// Create document metadata and blocks variables
                    DocumentMetadata responseMetadata = response.documentMetadata();
                    List<Block> blocks = response.blocks();
                    String nextToken = response.nextToken();
            		
            		// Fetch document metadata
                    System.out.println("Document has the following metadata: " + responseMetadata.toString());
                    // Returns List<Block> for all TEXT, TABLES, etc. found in each page of the document
                	System.out.println("Found the following Blocks: " + blocks.toString());
                	
                	/*
                	 * I.Summers 5-13-2024
                	 * 		The max # blocks returned is 1000, even if maxResults() isn't specified. 
                	 * 		If >1000 results, the response is paginated using tokens (i.e. returns NextToken string).
                	 * 		Implemented the nextToken method to access full Textract results.
                	 */
            		do {
            			if (nextToken != null && nextToken != "") {
            				List<Object> nextJob = getJobResults(textractClient, jobId, nextToken);
            				nextToken = (String) nextJob.get(0);
            				blocks.addAll((List<Block>) nextJob.get(1));
            				moreBlocks = true;
            			} else {
            				System.out.println("All done! No more tokens.");
            				moreBlocks = false;
            			}
            		} while (moreBlocks);
                	
                	sortedBlocks = sortBlocks(blocks);
                    finished = true;
                    
                } else {
                    System.out.println(index + " status is: " + status);
                    Thread.sleep(1000);
                }
                index++;
            }

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return sortedBlocks;
    }
    
    
    
    /*
     * I. Summers 5-13-24
     * 		Created new overloaded method for getJobResults, to be used for paginated response results.
     * 		Accepts token String parameter and returns nextToken string (if applicable)  
     */
	private static List<Object> getJobResults(TextractClient textractClient, String jobId, String token) {
	    	
	        boolean finished = false;
	        int index = 0;
	        String status;
	        String nextToken = "";
	        ArrayList<Object> jobResults = new ArrayList<>(2);
	
	        try {
	            while (!finished) {
	                GetDocumentAnalysisRequest analysisRequest = GetDocumentAnalysisRequest.builder()
	                        .jobId(jobId)
	                        .nextToken(token)
	                        .maxResults(1000)
	                        .build();
	
	                GetDocumentAnalysisResponse response = textractClient.getDocumentAnalysis(analysisRequest);
	                status = response.jobStatus().toString();
	
	                if (status.compareTo("SUCCEEDED") == 0) {
	                	
            			// Create blocks list variable
                        List<Block> blocks = response.blocks();
	                    
                        // Returns List<Block> for all TEXT, TABLES, etc. found in each page of the document
                    	System.out.println("Found the following Blocks: " + blocks.toString());
                    	
                    	// Set nextToken
                    	nextToken = response.nextToken();
                    	// Add to jobResults
                    	jobResults.add(0,  nextToken);
                    	jobResults.add(blocks);
                    	finished = true;
	                    
	                } else {
	                    System.out.println(index + " status is: " + status);
	                    Thread.sleep(1000);
	                }
	                index++;
	            }
	
	        } catch (InterruptedException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }
	        return jobResults;
	}
	
	
	/*
	 * I. Summers 5-31-24
	 * 		Created new sortBlocks method to sort the all resulting blocks from getJobResults according to page number.
	 * 		This will let us generate plain-text documents (utilizing AWS table data) to forward onto the Azure API 
	 */
	private static JsonNode sortBlocks(List<Block> blocks) {
		
		ObjectMapper mapper = new ObjectMapper();	// Initialize ObjectMapper for Jackson functionality
		ObjectNode sortedBlocks = mapper.createObjectNode();	// Initialize root node -- base of JSON
		
		for (Block block : blocks) {
			if (!sortedBlocks.has(String.valueOf(block.page()))) {	// If doc page number does not exist as a key:
				
				ArrayNode pageBlocks = mapper.createArrayNode();	// Create new array node for current page
				pageBlocks.addPOJO(block);	// Add current Block object to array node
				
				sortedBlocks.set(String.valueOf(block.page()), pageBlocks);	// Set page-array<Block> as first k-v pair
			} else {	// If doc page already exists as a key:
				
				((ArrayNode) sortedBlocks.get(String.valueOf(block.page()))).addPOJO(block); // Add Block value to page key
			}
		}
		
		return sortedBlocks;
	}
	
	
	
//	 public static void detectDocTextS3(TextractClient textractClient, String bucketName, String docName) {
//		 
//	        try {
//	        	
//	        	S3Object s3Object = S3Object.builder()
//	                    .bucket(bucketName)
//	                    .name(docName)
//	                    .build();
//
//	            // Create a Document object and reference the s3Object instance.
//	            Document myDoc = Document.builder()
//	                    .s3Object(s3Object)
//	                    .build();
//
//	            DetectDocumentTextRequest detectDocumentTextRequest = DetectDocumentTextRequest.builder()
//	                    .document(myDoc)
//	                    .build();
//
//	            // TODO 5-10-2024
//	            // Says it can take a PDF, but throws an error
//	            DetectDocumentTextResponse textResponse = textractClient.detectDocumentText(detectDocumentTextRequest);
//
//	            for (Block block : textResponse.blocks()) {
//	                System.out.println("The block type is " + block.blockType().toString());
//	            }
//
//	            DocumentMetadata documentMetadata = textResponse.documentMetadata();
//	            System.out.println("The number of pages in the document is " + documentMetadata.pages());
//
//	        } catch (TextractException e) {
//
//	            System.err.println(e.getMessage());
//	            System.exit(1);
//	        }
//	    }
	 
	
	
}
