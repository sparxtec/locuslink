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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
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
public class AssemblyController {

	private static final Logger logger = Logger.getLogger(AssemblyController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    
    @Autowired
    private AssemblyDao assemblyDao;
    
    

	@PostMapping(value = "initAssembliesData")
	public String initAssetData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssembliesData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assemblies-data";
	}
	
	
	@RequestMapping(value = "/getAllAssemblies", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllAsset(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllAsset()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllAssemblies");
	  	
		
		
		
		// TODO REPLACE THIS 
		List <AssemblyDTO> assemblyListDTO =  assemblyDao.getAllDTO();
		if (assemblyListDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
			
		
		
		
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(assemblyListDTO);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//logger.debug("json ->: " + json);		
		response.setField("assemblylist",  json);

		return response;
	 }
	
	
	
	
	
	
	/**
	 *   The Create Assembly Button on the Assemblies Main page UI
	 * 
	 */
	@PostMapping(value = "initAddAssembly")
	public String initAddAssembly (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAddAssembly()...");

		//Assembly assembly = new Assembly();
				
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-add";
	}
	
	
	
	/**
	 *   The Create Assembly Button on the Assemblies Main page UI
	 * 
	 */
	@PostMapping(value = "addAssembly")
	public String addAssembly (@ModelAttribute(name = "assemblyFormDTO") AssemblyFormDTO assemblyFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting addAssembly()...");

		Assembly assembly = new Assembly();
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
		
		assemblyDao.save(assembly);	
		logger.debug("Added new Assembly Station..");
		
		DashboardFormDTO dashboardFormDTO = new DashboardFormDTO();		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	 	return "fragments/assemblies-data";
	}
	
	
}