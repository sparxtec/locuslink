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
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class TestHarnessController {

	private static final Logger logger = Logger.getLogger(TestHarnessController.class);


    @Value("${barcode.print.barcode1}")
    private String printBarcode1;
    
    
	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UniversalCatalogDao universalCatalogDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;
    
    
    

	@PostMapping(value = "/initTestHarness")
	public String initTestHarness (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initTestHarness()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/testing_harness";
	}
	
	
	
	@PostMapping(value = "/initBarcodeView")
	public String initBarcodeView (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initBarcodeView()...");

		// Set the URL for the UI
		dashboardFormDTO.setPrintBarcode1(printBarcode1);
		
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/barcode_viewer";
	}

	
	@PostMapping(value = "/initPdfView")
	public String initPdfView (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initPdfView()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/pdf_viewer";
	}
	
	
	
	
	
	

}