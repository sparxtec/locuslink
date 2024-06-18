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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.model.AssemblyReqDoc;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2024 - Initial version
 */
@Controller
@Service
public class AssemblyTraceabilityController {

	private static final Logger logger = Logger.getLogger(AssemblyTraceabilityController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private AssemblyDao assemblyDao;
    
    @Autowired
    private AssemblyAttachmentDao assemblyAttachmentDao;
    
    
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.assembly.staging.fullpath}")
	private String assemblyStagingFullpath;
	
	@Value("${file.assembly.storage.fullpath}")
	private String assemblyStorageFullpath;
	
	

	@PostMapping(value = "initAssemblyTraceDocument")
	public String initAssemblyTraceDocument (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssemblyTraceDocument()...");

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Working on Assembly id ->: " + assemblyPkid);
					
		List<AssemblyAttachmentDTO> assemblyAttachmentDTOList = assemblyAttachmentDao.getAllDTOMtrByAssemblyId(Integer.valueOf(assemblyPkid) );
				
	 	model.addAttribute("assemblyAttachmentDTOList", assemblyAttachmentDTOList);
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);


	   	return "fragments/assembly-trace-document";
	}
	
	
	
	
	/**
	 *  Dropzone comes here. !!!
	 *  This is for the Assembly MTR uploads, which have the AI assist. 
	 */		
	@PostMapping(value = "/processAssemblyMtrUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processAssemblyMtrUpload(@RequestParam("file") MultipartFile inputfile,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

		logger.debug("Starting processAssemblyMtrUpload()..inputfile ->:" + inputfile.getOriginalFilename());
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
			
						
//			// TODO 5-11-2023   Dont allow duplicate files uploaded to the same Asset.
//			if (!checkFileIsValideToUpload(prefixAssemblytPkId + inputfile.getOriginalFilename())) {
//				logger.debug("  ERROR duplicate file upload not allowed for the same assembly. " );
//				response.setError(1);
//				response.setErrorMessage(" ERROR. You cannot upload duplicate file for the same assembly.");				
//				response.setField("uploadedFilenameInError", inputfile.getOriginalFilename());
//				return response;				
//			}
//
//				 
//			 
//	        logger.debug("           Name ->: " + fullpathFileName_keyName);
//	        logger.debug("    Content Type ->: " + inputfile.getContentType());
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
//            awsS3Client.putObject(putObjectRequest);
//            	        		
//			model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
			
			logger.debug(" Assembly Upload Worked,  size ->: " + inputfile.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR Document Upload failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
	
}