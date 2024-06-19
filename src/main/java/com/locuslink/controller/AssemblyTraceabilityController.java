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
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.logic.AwsS3AssemblyFileLogic;
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
    private AwsS3AssemblyFileLogic awsS3AssemblyFileLogic;
    
    

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

		logger.debug("Starting processAssemblyReqDocUpload()..inputfile ->:" + inputfile.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "Upload");

		
		if (inputfile.getOriginalFilename().contains(".pdf")) {
			// do nothing, all good
		} else {
			logger.debug("  ERROR xlsFileUpload failed ->: Wrong File extension. " );
			return response;
		}

		String prefixAssemblytPkId = dashboardFormDTO.getAssemblyPkid()+ "_";	
		
		awsS3AssemblyFileLogic.processAssemblyAttachmentUpload( response ,inputfile, prefixAssemblytPkId);
		       	        		
		model.addAttribute("message", "You successfully uploaded " + inputfile.getOriginalFilename() + "!");
		
		return response;
	}
	

	/**
	 * 6-10-2024  ASSEMBLY - Attachment ADD via Dropzone.
	 *       Once an attachment is droped in dropzone buffer, it was added to AWS staging.
	 */
	@RequestMapping(value = "/deleteAssemblyMtrFileUpload", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse deleteAssemblyFileUpload(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In deleteAssemblyFileUpload()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "deleteFileUpload");
		
		// 6-19-2024
		awsS3AssemblyFileLogic.processAssemblyAttachmentUploadDelete(request, response );
								
		return response;		
	}
	
	
	
	
	/**
	 *   06-10-2024 C.Sparks
	 *   ASSEMBLY - Attachment List has an ADD function, after Dropzone already called, and it loaded Staging,
	 *    it calls this to move the staging files to the  the AWS S3 Storage bucket, 
	 *    and insert into the database an attachment record with the filename and path.
	 */
	
	@PostMapping(value = "/saveAssemblyAttachmentsMtrFromStagingToStorage")
	public String saveAssemblyAttachmentsFromStagingToStorage (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
	
		logger.debug("In saveAssemblyAttachmentsFromStagingToStorage()");
	  		
		int assemblyPkid = Integer.valueOf(dashboardFormDTO.getAssemblyPkid());		
		//String assemblyDocTypePkid = dashboardFormDTO.getAssemblyDocTypePkId();
		String assemblyDocTypePkid = "62";  //Assembly MTR's are docType1, regular MTR are 50, FYI
		//String ardPkId = dashboardFormDTO.getArdPkId();
		String ardPkId = "1";  //Trace Doc is 1
			
		awsS3AssemblyFileLogic.processAssemblyAttachmentStageToStorage( assemblyPkid , assemblyDocTypePkid, ardPkId);
		

       	// 6-10-2024 - Need this so the UI can navigate back to the TAB we want in focus
	   	model.addAttribute("gotoDocumentsTab", "mtr");	 
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
	   	
	   	
		AssemblyDTO assemblyDto = assemblyDao.getDtoById(Integer.valueOf(assemblyPkid)  );		
	 	model.addAttribute("assemblyDto", assemblyDto);
	   	
	   	return "fragments/assembly-detail";	   		   	
	 }
		 
	
	
	
}