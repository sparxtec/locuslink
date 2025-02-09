package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import com.locuslink.util.BartenderRestClient;
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
    
    @Autowired
    private BartenderRestClient bartenderRestClient;
    

	//*************************************************************//
	//********         C U S T O M E R               **************//
	//*************************************************************//
	

	@PostMapping(value = "/getBarcodeForAsset")
	public String getBarcodeForAsset (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getBarcodeForAsset()...");

		
		// Step 1 - Get the database data for the Asset clicked
		UniqueAssetDTO uniqueAssetDTO =  uniqueAssetDao.getDtoById(Integer.valueOf(dashboardFormDTO.getUniqueAssetPkId()));
		if (uniqueAssetDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
	
		// 1-13-2024 testing
		String encodedPDFBarcdeString = bartenderRestClient.getBarcode_PDFEncodedStream(uniqueAssetDTO);		
//		String encodedPDFBarcdeString = "";
		
	   	model.addAttribute("encodedPDFBarcdeString", encodedPDFBarcdeString);	
		
	   	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		//return "fragments/modal_barcode_viewer";
	   	
	   	return "fragments/asset-barcode-viewer-modal";
	   	
	}
	



}