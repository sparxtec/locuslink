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
import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.dto.UniqueAssetDTO;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class AttachmentController {

	private static final Logger logger = Logger.getLogger(AttachmentController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;
    
    @Autowired
    private ProductAttachmentDao productAttachmentDao;
    
    
    
    


	
	//*************************************************************//
	//*******      A T T A C H M E N T   D A T A       ************//
	//*************************************************************//

    
	@PostMapping(value = "/initAssetAttachments")
	public String getAllAssetAttachments (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getAllAssetAttachments()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_list";
	}
	
	
			
	
	/**
	 *   5-17-2023   Get all the attachments for a clicked Asset.
	 */		
	@RequestMapping(value = "/getAllAssetAttachments", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllAssetAttachments(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllAssetAttachments()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllAssetAttachments");
	  	
		
		String uniqueAssetPkId = request.getFieldAsString("uniqueAssetPkId");
		if (uniqueAssetPkId == null || uniqueAssetPkId.length() < 1) {
			logger.debug("Error - uniqueAssetPkId is missing");
		} else {
			logger.debug("  uniqueAssetPkId ->: " + uniqueAssetPkId);
		}
		
		// TESTING
		List <ProductAttachmentDTO> productAttachmentListDTO =  productAttachmentDao.getDtoByUniqueAssetId(Integer.valueOf(uniqueAssetPkId));
		if (productAttachmentListDTO == null) {
			logger.debug("  Note:  No Data Found......");
		}
			
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(productAttachmentListDTO);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("assetAttachmentlist",  json);

		return response;
	 }

	
	@PostMapping(value = "/getAttachmentForAsset")
	public String getBarcodeForAsset (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getAttachmentForAsset()...");

		
//		// Step 1 - Get the database data for the Asset clicked
//		UniqueAssetDTO uniqueAssetDTO =  uniqueAssetDao.getDtoById(Integer.valueOf(dashboardFormDTO.getUniqueAssetPkId()));
//		if (uniqueAssetDTO == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
		
		

		
		// 5-9-2023
	//	String encodedPDFBarcdeString = bartenderRestClient.getBarcode_PDFEncodedStream(barcodeTemplateName, String.valueOf(uniqueAssetDTO.getUniqueAssetPkId()));		
	//   	model.addAttribute("encodedPDFBarcdeString", encodedPDFBarcdeString);	
		
	  // 	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_viewer";
	}

	
}