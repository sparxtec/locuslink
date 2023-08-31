package com.locuslink.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.IndustryDao;
import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dao.ProductAttributeDao;
import com.locuslink.dao.ProductTypeDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.UidGeneratorFormDTO;
import com.locuslink.model.Industry;
import com.locuslink.model.ProductType;
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

	@Autowired
	private UniqueAssetDao uniqueAssetDao;
	
	@Autowired
	private UniversalCatalogDao universalCatalogDao;
	
    @Autowired
    private ProductAttachmentDao productAttachmentDao;
    
    @Autowired
    private ProductAttributeDao productAttributeDao;
    
    @Autowired
    private ProductTypeDao productTypeDao;
    
    @Autowired
    private IndustryDao industryDao;
    
    
	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;



    
	@PostMapping(value = "/initTestHarness")
	public String initTestHarness (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initTestHarness()...");

		List <ProductType> productTypeList = productTypeDao.getAll();	
	   	model.addAttribute("productTypeList", productTypeList);		   	
	   	
		List <Industry> industryList = industryDao.getAll();	
	   	model.addAttribute("industryList", industryList);	 
		
   	
	   	model.addAttribute("dashboardFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	

	
	
	

}