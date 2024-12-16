package com.locuslink.controller;

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

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.dto.AssemblyFormDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.logic.AwsS3AssemblyFileLogic;
import com.locuslink.model.Assembly;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2024 - Initial version
 */
@Controller
@Service
public class AssemblyExportController {

	private static final Logger logger = Logger.getLogger(AssemblyExportController.class);


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
	
	/**
	 *   The Edit Assembly Button on the Assemblies Detail UI
	 * 
	 */
	@PostMapping(value = "initExportAssembly")
	public String initEditAssembly (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initExportAssembly()...");

		

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Exporting for Assembly id ->: " + assemblyPkid);
		
		// TODO read the assembly to display
		Assembly assembly = assemblyDao.getById(Integer.valueOf(assemblyPkid));
		
		AssemblyFormDTO assemblyFormDTO = new AssemblyFormDTO();
		assemblyFormDTO.setAssemblyPkid(Integer.valueOf(assemblyPkid));
		assemblyFormDTO.setCustomerSpecNumber(assembly.getCustomerSpecNumber());
		assemblyFormDTO.setDesignSpecNumber(assembly.getDesignSpecNumber());
		assemblyFormDTO.setDrawingNumber(assembly.getDrawingNumber());
		assemblyFormDTO.setFabricatorCompanyName(assembly.getFabricatorCompanyName());
		assemblyFormDTO.setJobDescription(assembly.getJobDescription());
		assemblyFormDTO.setJobNumber(assembly.getJobNumber());
		assemblyFormDTO.setLocation(assembly.getLocation());
		assemblyFormDTO.setStationName(assembly.getStationName());
		assemblyFormDTO.setStationNumber(assembly.getStationNumber());
		assemblyFormDTO.setTraceNumber(assembly.getTraceNumber());
		
	  	model.addAttribute("assemblyFormDTO", assemblyFormDTO);
	  	
	  	
		
	//	List<AssemblyAttachmentDTO> assemblyAttachmentDTOList = assemblyAttachmentDao.getAllDTOMtrByAssemblyId(Integer.valueOf(assemblyPkid) );
			
		
	    List<AssemblyAttachmentDTO> assemblyAttachmentDTOList = assemblyAttachmentDao.getAllDTOExportbyAssemblyId(Integer.valueOf(assemblyPkid) );
		
		model.addAttribute("assemblyAttachmentDTOList", assemblyAttachmentDTOList);
		model.addAttribute("dashboardFormDTO", dashboardFormDTO);



	   	return "fragments/assembly-export";
	}
	

	/**
	 *   The Edit Assembly UPDATE Button was clicked
	 * 
	 */
	@PostMapping(value = "exportAssembly")
	public String editAssembly (@ModelAttribute(name = "assemblyFormDTO") AssemblyFormDTO assemblyFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initEditAssembly()...");

	
		int assemblyPkid = assemblyFormDTO.getAssemblyPkid();
		logger.debug("Exporting Assembly id ->: " + assemblyPkid);
		
		
		// Now reread from the DB to get this DTO for the details page		
		AssemblyDTO assemblyDto = assemblyDao.getDtoById(assemblyPkid );
	   	model.addAttribute("assemblyDto", assemblyDto);
	   	
	   	
		DashboardFormDTO dashboardFormDTO = new DashboardFormDTO();	
		dashboardFormDTO.setAssemblyPkid(String.valueOf(assemblyPkid));
	    model.addAttribute("dashboardFormDTO", dashboardFormDTO);


	   	
	   	return "fragments/assembly-detail";
	}
	
	
}