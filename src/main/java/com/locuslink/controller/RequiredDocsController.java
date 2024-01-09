package com.locuslink.controller;

import java.util.ArrayList;
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
import com.locuslink.dao.CustomerDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.CatalogAttributeListDTO;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.RequiredDocumentsDTO;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.dto.UniversalCatalogDTO;
import com.locuslink.model.Customer;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class RequiredDocsController {

	private static final Logger logger = Logger.getLogger(RequiredDocsController.class);


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
    
    
    

	

	//***********************************************************************//
	//********      A S S E T   R E Q   D O C U M E N TS       **************//
	//***********************************************************************//	
	
	@PostMapping(value = "/initRequiredAssetDocumentData")
	public String initRequiredAssetDocumentData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initRequiredAssetDocumentData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_asset_req_documents";
	}
	
	//***********************************************************************//
	//********   C A T A L O G  R E Q   D O C U M E N TS       **************//
	//***********************************************************************//	
	
	@PostMapping(value = "/initRequiredCatalogDocumentData")
	public String initRequiredCatalogDocumentData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initRequiredCatalogDocumentData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_catalog_req_documents";
	}
	
	
	@RequestMapping(value = "/getAllDocuments", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllDocuments(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllDocuments()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllDocuments");
	  			
//		List <RequiredDocumentsDTO> documentList =  customerDao.getAll();
//		if (customerList == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
		List <RequiredDocumentsDTO> documentList = new ArrayList<RequiredDocumentsDTO> () ;
		RequiredDocumentsDTO requiredDocumentsDTO = new RequiredDocumentsDTO();
		requiredDocumentsDTO.setDocName("BigDocument");
		requiredDocumentsDTO.setDocTypeCode("UberType");
		requiredDocumentsDTO.setDocTypeDesc("This is a test required document");
		documentList.add(requiredDocumentsDTO);
		
		
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(documentList);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("documentList",  json);

		return response;
	 }
	
	
	

}