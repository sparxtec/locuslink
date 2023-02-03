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
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.model.Customer;
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

	@PostMapping(value = "/initMyWorkspace")
	public String initMyWorkspace (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initMyWorkspace()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}


	
	
	@PostMapping(value = "/initViewCustomerData")
	public String initViewCustomerData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCustomerData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_customer";
	}

	
	@RequestMapping(value = "/getAllCustomers", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllUser(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllStagedUploads()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllStagedUploads");
	  			
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
	
	
	
	
	@PostMapping(value = "/initViewCustomerPermissionData")
	public String initViewCustomerPermissionData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCustomerPermissionData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}
	
	
	
	@PostMapping(value = "/initViewCatalogData")
	public String initViewCatalogData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewCatalogData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}
	


	@PostMapping(value = "/initViewAssetData")
	public String initViewAssetData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewAssetData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}
	
	

	@PostMapping(value = "/initViewPublishedData")
	public String initViewPublishedData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initViewPublishedData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace";
	}
	
	
	
	

}