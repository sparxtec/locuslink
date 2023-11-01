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
	//********       D O C U M E N T                 **************//
	//*************************************************************//	
	
	@PostMapping(value = "/initDocumentData")
	public String initDocumentData (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initDocumentData()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/myworkspace_documents";
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
	public @ResponseBody GenericMessageResponse getAllCustomers(@RequestBody GenericMessageRequest request, HttpSession session)  {

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
	  			
		List <UniversalCatalogDTO> universalCatalogDTOList =  universalCatalogDao.getAllDTO();
		if (universalCatalogDTOList == null) {
			logger.debug("  Note:  No Data Found......");
		}
		
		
		List<CatalogAttributeListDTO> attributeList = new ArrayList<CatalogAttributeListDTO>();
		
		CatalogAttributeListDTO wrkDto = new CatalogAttributeListDTO();
			wrkDto.setCdalPkId(1);
			wrkDto.setSelectedValue("Other");
			wrkDto.setUidAttrSeq(1);
			wrkDto.setUidAttrListName("NBR_PHASES");
			wrkDto.setUidAttributesJson(" {'name':'Number of Phases','Unknown':'0','1':'1','2':'2','3':'3','Other':'4','NA':'5'} ");
			attributeList.add(wrkDto);
			
			wrkDto = new CatalogAttributeListDTO();
			wrkDto.setCdalPkId(3);
			wrkDto.setSelectedValue("Liquid");
			wrkDto.setUidAttrSeq(4);
			wrkDto.setUidAttrListName("COOLING_TYPE");
			wrkDto.setUidAttributesJson(" {\"name\":Cooling Type\",\"Unknown\":\"0\",\"Liquid\":\"1\",\"Dry-Type\":\"2\",\"Other\":\"3\",\"NA\":\"4\"} ");
			attributeList.add(wrkDto);
		
			wrkDto = new CatalogAttributeListDTO();
			wrkDto.setCdalPkId(4);
			wrkDto.setSelectedValue("0.5 kva");
			wrkDto.setUidAttrSeq(5);
			wrkDto.setUidAttrListName("KVA_RATING");
			wrkDto.setUidAttributesJson(" {\"name\":\"kva Rating\",\"Unknown\":\"0\",\"0.5 kva\":\"1\",\"1.0 kva\":\"2\",\"1.5 kva\":\"3\",\"3 kva\":\"4\",\"5 kva\":\"5\",\"7 kva\":\"6\",\"7.5 kva\":\"7\",\"10 kva\":\"8\",\"15 kva\":\"9\",\"20 kva\":\"A\",\"25 kva\":\"B\",\"30 kva\":\"C\",\"37.5 kva\":\"D\",\"45 kva\":\"E\",\"50 kva\":\"F\",\"75 kva\":\"G\",\"100 kva\":\"H\",\"112.5 kva\":\"I\",\"150 kva\":\"J\",\"167 kva\":\"K\",\"200 kva\":\"L\",\"225 kva\":\"M\",\"250 kva\":\"N\",\"300 kva\":\"O\",\"333 kva\":\"P\",\"342 kva\":\"Q\",\"375 kva\":\"R\",\"416 kva\":\"S\",\"450 kva\":\"T\",\"500 kva\":\"U\",\"666 kva\":\"V\",\"750 kva\":\"W\",\"833 kva\":\"X\",\"966 kva\":\"Y\",\"1000 kva\":\"Z\",\"1250 kva\":\"a\",\"1500 kva\":\"b\",\"1750 kva\":\"c\",\"2000 kva\":\"d\",\"2500 kva\":\"e\",\"3000 kva\":\"f\",\"3500 kva\":\"g\",\"3750 kva\":\"h\",\"10000 kva\":\"i\",\"12000 kva\":\"j\",\"12500 kva\":\"k\",\"15000 kva\":\"l\",\"16667 kva\":\"m\",\"20000 kva\":\"n\",\"25000 kva\":\"o\",\"30000 kva\":\"p\",\"33333 kva\":\"q\",\"37500 kva\":\"r\",\"50000 kva\":\"s\",\"60000 kva\":\"t\",\"75000 kva\":\"u\",\"100000 kva\":\"v\"} ");
			attributeList.add(wrkDto);
			
		
		List <UniversalCatalogDTO> wrkUniversalCatalogDTOList =  new ArrayList<UniversalCatalogDTO>();
		
		for (UniversalCatalogDTO universalCatalogDTO : universalCatalogDTOList) {
			universalCatalogDTO.setAttributeList(attributeList);			
			wrkUniversalCatalogDTOList.add(universalCatalogDTO);
		}

        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			json = mapper.writeValueAsString(wrkUniversalCatalogDTOList);	
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