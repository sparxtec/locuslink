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

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.dto.AssemblyFormDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.model.Assembly;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2024 - Initial version
 */
@Controller
@Service
public class AssemblyDetailController {

	private static final Logger logger = Logger.getLogger(AssemblyDetailController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private AssemblyDao assemblyDao;
    

	@PostMapping(value = "initAssemblyDetail")
	public String initAssemblyDetail (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssemblyDetail()...");

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Working on Assembly id ->: " + assemblyPkid);
		
		//AssemblyDTO assemblyDto = assemblyDao.getDtoById(Integer.valueOf(assemblyPkid)  );
		AssemblyDTO assemblyDto = assemblyDao.getDtoById(Integer.valueOf(assemblyPkid)  );
		
	 	model.addAttribute("assemblyDto", assemblyDto);
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-detail";
	}
	
	
	
	/**
	 *   The Edit Assembly Button on the Assemblies Detail UI
	 * 
	 */
	@PostMapping(value = "initEditAssembly")
	public String initEditAssembly (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initEditAssembly()...");

		

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Editing Assembly id ->: " + assemblyPkid);
		
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
	   //	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-edit";
	}
	

	/**
	 *   The Edit Assembly UPDATE Button was clicked
	 * 
	 */
	@PostMapping(value = "editAssembly")
	public String editAssembly (@ModelAttribute(name = "assemblyFormDTO") AssemblyFormDTO assemblyFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initEdieditAssemblytAssembly()...");

	
		int assemblyPkid = assemblyFormDTO.getAssemblyPkid();
		logger.debug("Editing Assembly id ->: " + assemblyPkid);
		
		// TODO read the assembly form DTO passed in, and save those values to the DB
		Assembly assembly = assemblyDao.getById(Integer.valueOf(assemblyPkid));
		assembly.setCustomerSpecNumber(assemblyFormDTO.getCustomerSpecNumber());
		assembly.setDesignSpecNumber(assemblyFormDTO.getDesignSpecNumber());
		assembly.setDrawingNumber(assemblyFormDTO.getDrawingNumber());
		assembly.setFabricatorCompanyName(assemblyFormDTO.getFabricatorCompanyName());
		assembly.setJobDescription(assemblyFormDTO.getJobDescription());
		assembly.setJobNumber(assemblyFormDTO.getJobNumber());
		assembly.setLocation(assemblyFormDTO.getLocation());
		assembly.setStationName(assemblyFormDTO.getStationName());
		assembly.setStationNumber(assemblyFormDTO.getStationNumber());
		assembly.setTraceNumber(assemblyFormDTO.getTraceNumber());
		assemblyDao.saveOrUpdate(assembly);
		
		// Now reread from the DB to get this DTO for the details page		
		AssemblyDTO assemblyDto = assemblyDao.getDtoById(assemblyPkid );
	   	model.addAttribute("assemblyDto", assemblyDto);
	   	
	   	
		DashboardFormDTO dashboardFormDTO = new DashboardFormDTO();	
		dashboardFormDTO.setAssemblyPkid(String.valueOf(assemblyPkid));
	    model.addAttribute("dashboardFormDTO", dashboardFormDTO);


	   	
	   	return "fragments/assembly-detail";
	}
	
	
}