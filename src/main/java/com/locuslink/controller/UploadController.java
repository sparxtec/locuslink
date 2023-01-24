package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.Upload;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.DashboardFormDTO;
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
            
            awsS3Client.putObject(putObjectRequest);
            	        		
			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" CSV fileUpload Worked,  size ->: " + inputfile.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
	
	
	
	/*
	 * MultipartFile Upload - DropZone
	 */
	@PostMapping(value = "/processFileUpload")
	public String processFileUpload(@RequestParam("file") MultipartFile file, Model model,
			@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO) {

		logger.debug("Starting processFileUpload()..file ->:" + file.getOriginalFilename());

//		// CS 5-14-2020
//		String itemNumber = docUploadFormDTO.getItemNumber();
//		String poNumber = docUploadFormDTO.getPurchaseOrderNumber();
//		boolean isItemUploadProcessing = false;
//		if (poNumber.equalsIgnoreCase("notUSed") && !itemNumber.isEmpty()) {
//			logger.debug("Processing an MTR uploading to an ITEM.  {no po}");
//			isItemUploadProcessing = true;
//		}

		// CS 5-14-2020 - if this is an Item Upload, store the files in a different
		// folder location
		try {
//			String purchaseOrderNum = "";
//			String jobFilePath = "";
//			if (isItemUploadProcessing) {
//				jobFilePath = "/allItemUploads/Item" + itemNumber;
//			} else {
//				EhubPurchaseOrder ehubPurchaseOrder = ehubPurchaseOrderService
//						.getByPoNumber(docUploadFormDTO.getPurchaseOrderNumber());
//				purchaseOrderNum = ehubPurchaseOrder.getPurchaseOrderNum();
//				jobFilePath = "/po" + purchaseOrderNum;
//			}
//
//			fileStorageService.store(file, jobFilePath);

			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
			logger.debug("  fileUpload Worked,  size ->: " + file.getSize());
			
			model.addAttribute("dashboardFormDTO", dashboardFormDTO);
			
			return "fragments/docuploadpage3";

		} catch (Exception e) {
			logger.debug("  ERROR fileUpload failed ->: " + e.getMessage());
			return "fragments/docuploadstep2";
		}
	}

}