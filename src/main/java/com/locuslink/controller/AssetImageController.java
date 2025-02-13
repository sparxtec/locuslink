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
 * @since 1.0.0 - February 01, 2024 - Initial version
 */
@Controller
@Service
public class AssetImageController {

	private static final Logger logger = Logger.getLogger(AssetImageController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private UniversalCatalogDao universalCatalogDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;
    
     
	@PostMapping(value = "/initAssetImages")
	public String initAssetImages (@ModelAttribute(name = "uniqueAssetDTO") UniqueAssetDTO uniqueAssetDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initAssetImages()...");
		
		// 2-16-2024
		// Need to re-hydrate the DTO, the pkId is the only thing coming in to this routine
		uniqueAssetDTO =  uniqueAssetDao.getDtoById(uniqueAssetDTO.getUniqueAssetPkId());
		if (uniqueAssetDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
	   	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);	   		   	

	   	return "fragments/asset-image";
	}

}