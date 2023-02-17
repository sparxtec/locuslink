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
import com.locuslink.dao.CustomerDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.UniqueAsset;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class BarcodeController {

	private static final Logger logger = Logger.getLogger(BarcodeController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UniversalCatalogDao universalCatalogDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;
    
    
    

	//*************************************************************//
	//********         C U S T O M E R               **************//
	//*************************************************************//
	
	@PostMapping(value = "/xxxxinitBarcode")
	public String xxxxinitBarcode (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting xxxxinitBarcode()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/xxxxbarcode";
	}

	
	
	
	
	@PostMapping(value = "/getBarcodeForAsset2")
	public String getBarcodeForAsset2 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getBarcodeForAsse2t()...");

		
		UniqueAsset uniqueAsset =  uniqueAssetDao.getById(dashboardFormDTO.getUniqueAssetPkId());
		if (uniqueAsset == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
		UniqueAssetDTO uniqueAssetDTO = new UniqueAssetDTO();
		uniqueAssetDTO.setManufacturerName("ABC Manufacturing");
		uniqueAssetDTO.setUniqueAssetId(dashboardFormDTO.getUniqueAssetPkId() + ".xxx.xxx.xxx");
		uniqueAssetDTO.setUniversalCatalogId("zzz.zzz.zzz");
		
	   	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_barcode_viewer";
	}
	
	
	

//	@RequestMapping(value = "/getBarcodeForAsset", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
//	public @ResponseBody GenericMessageResponse getBarcodeForAsset(@RequestBody GenericMessageRequest request, HttpSession session)  {
//
//		logger.debug("In getBarcodeForAsset()");
//		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getBarcodeForAsset");
//	  	
//		// TESTING
//		UniqueAsset uniqueAsset =  uniqueAssetDao.getById(900);
//		if (uniqueAsset == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
//			
//		
//		
//        // Convert the POJO array to json, for the UI
//		ObjectMapper mapper = new ObjectMapper();		
//		String json = "";			
//		try {
//			json = mapper.writeValueAsString(uniqueAsset);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		logger.debug("json ->: " + json);		
//		response.setField("uniqueAsset",  json);
//
//		return response;
//	 }
	
	
	

}