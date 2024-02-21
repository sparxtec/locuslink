package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.IndustryDao;
import com.locuslink.dao.ProductAttributeDao;
import com.locuslink.dao.ProductSubTypeDao;
import com.locuslink.dao.ProductTypeDao;
import com.locuslink.dao.SubIndustryDao;
import com.locuslink.dto.UidGeneratorFormDTO;

/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class ProductAttributeController {

	private static final Logger logger = Logger.getLogger(ProductAttributeController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;
    

    @Autowired
    private ProductAttributeDao productAttributeDao;

    
    @Autowired
    private IndustryDao industryDao;
    
    @Autowired
    private SubIndustryDao subIndustryDao;
    
    @Autowired
    private ProductTypeDao productTypeDao;
    
    @Autowired
    private ProductSubTypeDao productSubTypeDao;
    
//    @Autowired
//    private UidProductAttributeListDao uidProductAttributeListDao;  



	
	//*************************************************************//
	//*******      A T T A C H M E N T   D A T A       ************//
	//*************************************************************//
    
	@PostMapping(value = "/initProductAttributeDetail")
	public String initProductAttributeDetail (@ModelAttribute(name = "UidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProductAttributeDetail()...");

// TODO 10-19=2023 fix this		
		
//		// TESTING
//		// 3 = KVA_RATING for Testing
//		UidProductAttributeList uidProductAttributeList =  uidProductAttributeListDao.getById(3);
//		if (uidProductAttributeList == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
			
		// testing 
		JSONObject jsonAttributes = null;
		try {
		// fix this   jsonAttributes = new JSONObject(uidProductAttributeList.getUidPalAttributesJson());
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	

        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			// fix this json = mapper.writeValueAsString(uidProductAttributeList.getUidPalAttributesJson());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
	//	response.setField("uidProductAttributeList_List",  json);
		
	   	model.addAttribute("uidPalAttributesJson_object", jsonAttributes);
	   	
	   	model.addAttribute("uidPalAttributesJson_string", json);
		
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);

		return "fragments/product_attribute_viewer";
	}
	
	
    


	
}