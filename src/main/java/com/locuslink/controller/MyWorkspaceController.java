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
import com.locuslink.dao.CustomerDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.Customer;
import com.locuslink.model.UniqueAsset;
import com.locuslink.model.UniversalCatalog;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class MyWorkspaceController {

	private static final Logger logger = Logger.getLogger(MyWorkspaceController.class);


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
    
    
    
    
	@PostMapping(value = "/initMyWorkspace")
	public String initMyWorkspace (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initMyWorkspace()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}


	//*************************************************************//
	//********         C U S T O M E R               **************//
	//*************************************************************//
	
	@PostMapping(value = "/initViewCustomerData")
	public String initViewCustomerData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCustomerData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_customer";
	}

	
	@RequestMapping(value = "/getAllCustomers", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllUser(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllCustomers()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllCustomers");
	  			
		List <Customer> customerList =  customerDao.getAll();
		if (customerList == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(customerList);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("customerList",  json);

		return response;
	 }
	
	
	
	
	
	
	//*************************************************************//
	//********      A C C E S S - for customers      **************//
	//*************************************************************//
	
	@PostMapping(value = "/initViewCustomerPermissionData")
	public String initViewCustomerPermissionData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCustomerPermissionData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}
	
	
	
	
	//*************************************************************//
	//*******      U N I V E R S A L  C A T A L O G    ************//
	//*************************************************************//
		
	@PostMapping(value = "/initViewCatalogData")
	public String initViewCatalogData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCatalogData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_catalog";
	}
	
	@RequestMapping(value = "/getAllCatalog", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllCatalog(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllCatalog()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllCatalog");
	  			
		List <UniversalCatalog> universalCatalogList =  universalCatalogDao.getAll();
		if (universalCatalogList == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(universalCatalogList);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("universalcataloglist",  json);

		return response;
	 }
	
	
	
	
	
	//*************************************************************//
	//*******            A S S E T  D A T A            ************//
	//*************************************************************//

	@PostMapping(value = "/initViewAssetData")
	public String initViewAssetData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewAssetData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_asset";
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
	
	
	
	
	/**
	 *   5-17-2023   Get all the attachments for a clicked Asset.
	 */		
	@RequestMapping(value = "/getAllAssetAttachments", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllAssetAttachments(@RequestBody GenericMessageRequest request, HttpSession session)  {

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
		response.setField("assetAttachmentlist",  json);

		return response;
	 }

	
	
	@PostMapping(value = "/initAssetAttachments")
	public String getAllAssetAttachments (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting getAllAssetAttachments()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/modal_attachment_list";
	//	return "fragments/modal_attachment_viewer";
	}
	
	
	

	
	
	//*************************************************************//
	//*******         P U B L I S H E D                ************//
	//*************************************************************//
	@PostMapping(value = "/initViewPublishedData")
	public String initViewPublishedData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewPublishedData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/publish";
	}
	
	
	

	
	

}