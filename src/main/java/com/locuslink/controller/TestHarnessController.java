package com.locuslink.controller;

import java.util.ArrayList;
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
import com.locuslink.dao.SubIndustryDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.UidGeneratorFormDTO;
import com.locuslink.model.Industry;
import com.locuslink.model.ProductType;
import com.locuslink.model.SubIndustry;
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
    
    @Autowired
    private SubIndustryDao subIndustryDao;
    
    
	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;



    
	@PostMapping(value = "/initTestHarness")
	public String initTestHarness (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initTestHarness()...");

		
		List <Industry> industryList = industryDao.getAll();	
	   	model.addAttribute("industryList", industryList);	 
	   	if (industryList.size() > 0) {
		    logger.debug("industryList ->:   uid:" + industryList.get(0).getUid() +  "  code:" + industryList.get(0).getIndustryCode() + "   desc:" + industryList.get(0).getIndustryDesc());
	   	}
  		   	
		List <SubIndustry> subIndustryList = subIndustryDao.getAll();	
	   	model.addAttribute("subIndustryList", subIndustryList);	 
	   	if (subIndustryList.size() > 0) {
		    logger.debug("subIndustryList ->:   uid:" + subIndustryList.get(0).getUid() +  "   code:" + subIndustryList.get(0).getSubIndustryCode() + "   desc:" + subIndustryList.get(0).getSubIndustryDesc());
	   	}
	   	
		List <ProductType> productTypeList = productTypeDao.getAll();	
	   	model.addAttribute("productTypeList", productTypeList);		   	
	   	logger.debug("productTypeList ->: " + productTypeList);
		   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processIndustrySelected")
	public String processIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustry());

		Industry industry = industryDao.getById(wrkIndustryId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industryList ->:   uid:" + industryList.get(0).getUid() +  "  code:" + industryList.get(0).getIndustryCode() + "   desc:" + industryList.get(0).getIndustryDesc());
		}
	  	model.addAttribute("industryList", industryList);	

	  	// Get the Sub Industry for the Selected Industry   	
		List <SubIndustry> subIndustryList = subIndustryDao.getByIndustry(wrkIndustryId);	
	   	model.addAttribute("subIndustryList", subIndustryList);	 
	   	if (subIndustryList.size() > 0) {
		    logger.debug("subIndustryList ->:   uid:" + subIndustryList.get(0).getUid() +  "   code:" + subIndustryList.get(0).getSubIndustryCode() + "   desc:" + subIndustryList.get(0).getSubIndustryDesc());
	   	}
	   	
	   	// sub industry not selected yet, so need to display them all
		List <ProductType> productTypeList = productTypeDao.getAll();
	   	model.addAttribute("productTypeList", productTypeList);		   	
	   	logger.debug("productTypeList ->: " + productTypeList);
		   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processSubIndustrySelected")
	public String processSubIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processSubIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustry());
		int wrkSubIndustryId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustry());

		// Get the Selected Industry  
		Industry industry = industryDao.getById(wrkIndustryId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industryList ->:   uid:" + industryList.get(0).getUid() +  "  code:" + industryList.get(0).getIndustryCode() + "   desc:" + industryList.get(0).getIndustryDesc());
		}
	  	model.addAttribute("industryList", industryList);	

	  	// Get the selected Sub Industry for the Selected Industry   	
		SubIndustry subIndustry = subIndustryDao.getById(wrkSubIndustryId);	
		List <SubIndustry> subIndustryList = new ArrayList<SubIndustry> ();
		if (subIndustry != null) {
			subIndustryList.add(subIndustry);
		    logger.debug("subIndustry ->:   uid:" + subIndustryList.get(0).getUid() +  "  code:" + subIndustryList.get(0).getSubIndustryCode() + "   desc:" + subIndustryList.get(0).getSubIndustryDesc());
		}
	   	model.addAttribute("subIndustryList", subIndustryList);	 
		

	   	
	   	// Get the selected Products for the Selected Sub Industry   	
		//List <ProductType> productTypeList = productTypeDao.getAll();
	   	List <ProductType> productTypeList = productTypeDao.getBySubIndustry(wrkSubIndustryId);	   	
		if (productTypeList != null) {
		    logger.debug("productTypeList ->:   uid:" + productTypeList.get(0).getUid() +  "  code:" + productTypeList.get(0).getProductTypeCode() + "   desc:" + productTypeList.get(0).getProducTypeDesc());
		}		
	   	model.addAttribute("productTypeList", productTypeList);		   	
	   	logger.debug("productTypeList ->: " + productTypeList);
		   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	

}