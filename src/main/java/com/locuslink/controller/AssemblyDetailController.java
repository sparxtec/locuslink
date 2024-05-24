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
import com.locuslink.dto.DashboardFormDTO;
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
	@PostMapping(value = "editAssemblyData")
	public String editAssemblyData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting editAssemblyData()...");

		

		String assemblyPkid = dashboardFormDTO.getAssemblyPkid();
		logger.debug("Editing Assembly id ->: " + assemblyPkid);
		
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-edit";
	}
	

}