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
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UniqueAssetDTO;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class AssetController {

	private static final Logger logger = Logger.getLogger(AssetController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private UniversalCatalogDao universalCatalogDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;
    
    
    
	
	
	//*************************************************************//
	//*******            A S S E T  D A T A            ************//
	//*************************************************************//

	@PostMapping(value = "/initAssetData")
	public String initAssetData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssetData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		//return "fragments/myworkspace_asset";
	   	return "fragments/asset-data";
	}
	
	@RequestMapping(value = "/getAllAsset", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllAsset(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllAsset()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllAsset");
	  	
		// TESTING
		List <UniqueAssetDTO> uniqueAssetListDTO =  uniqueAssetDao.getAllDTO();
		if (uniqueAssetListDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
			
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(uniqueAssetListDTO);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("uniqueassetlist",  json);

		return response;
	 }
	
	
	@PostMapping(value = "/initAssetDetail")
	public String initAssetDetail (@ModelAttribute(name = "uniqueAssetDTO") UniqueAssetDTO uniqueAssetDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initAssetDetail()...");

		
		 uniqueAssetDTO =  uniqueAssetDao.getDtoById(1);
		if (uniqueAssetDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
		

	   	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);	   		   	
	  // 	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		//return "fragments/myworkspace_asset";
	   	return "fragments/asset-detail";
	}

}