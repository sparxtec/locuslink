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
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.util.BartenderRestClient;
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

//    @Autowired
//    private CustomerDao customerDao;
//
//    @Autowired
//    private UniversalCatalogDao universalCatalogDao;
//    
//    @Autowired
//    private UniqueAssetDao uniqueAssetDao;
    
    
    @Autowired
    private BartenderRestClient bartenderRestClient;

    
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

	/**
	 *   Original pdf viewer, not used in BArtender Cloud testing
	 */
	@PostMapping(value = "/initPdfView")
	public String initPdfView (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initPdfView()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/pdf_viewer";
	}
	
	
	
	
	
	
	
	/**
	 *   Test case to get the printer list from BTC, then use a selected printer, and print to that
	 *   defined "networked" printer.
	 *   
	 *   Outlier Test Case I think,
	 */
	
	
//	@PostMapping(value = "/printBartenderCloud")
//	public String printBartenderCloud (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
//		logger.debug("Starting printBartenderCloud()...");
//
//		
//		// Step 1 - Get the right attached printer, maybe this needs to be a list or profile setting
//		bartenderRestClient.getPrinterList();
//
//		
//		
//		// Step 2 - Print Barcode
//		JSONObject jsonRequest = new JSONObject();
//		JSONObject jsonMainOptions = new JSONObject();
//		JSONObject jsonNamedDataSources = new JSONObject();
//
//		try {
//			jsonMainOptions.put("Document", "Librarian://main/locuslink_1.btw");
//			jsonMainOptions.put("Printer", "printer:Sparxtec_1/ZDesigner_ZD420-300dpi_ZPL");
//			jsonMainOptions.put("SaveAfterPrint", false);	
//			
//			jsonNamedDataSources.put("Ship_To_Name", "Bohak and BuddahConcheezy");
//			
//			jsonMainOptions.put("NamedDataSources", jsonNamedDataSources);			
//
//			jsonRequest.put("PrintBTWAction", jsonMainOptions);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.debug("jsonREquest ->: " +jsonRequest.toString());
//		bartenderRestClient.printBarcode(jsonRequest.toString());
//				
//		
//		model.addAttribute("dashboardFormDTO", dashboardFormDTO);
//
//		return "fragments/testing_harness";
//	}
	
	
	/**
	 *   Part 1 for BTC Barcode to File {print to file}, so it can be downloaded, viewed, 
	 *   and printed locally
	 */	 
	@PostMapping(value = "/writeBartenderCloudToPDF")
	public String writeBartenderCloudToPDF (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting writeBartenderCloudToPDF()...");

					
		
		bartenderRestClient.btcBarcodePrintToFile("todo");		
		
						
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/testing_harness";
	}
	
	
	
	
	/**
	 *   03-14-2023  Go to the Bartender Cloud, Populate and figure out best options for viewing
	 *   Download may enable the print from the UI Printer list.
	 */	 
	@PostMapping(value = "/viewBartenderCloud")
	public String viewBartenderCloud (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting viewBartenderCloud()...");


	//	bartenderRestClient.viewBarcodePDF();	
		
		
		

		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/testharness_modal_barcode_viewer";
	}
	

}