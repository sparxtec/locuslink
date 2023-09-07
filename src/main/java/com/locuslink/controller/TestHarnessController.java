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
import com.locuslink.dao.ProductSubTypeDao;
import com.locuslink.dao.ProductTypeDao;
import com.locuslink.dao.SubIndustryDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.UidDTO;
import com.locuslink.dto.UidGeneratorFormDTO;
import com.locuslink.model.Industry;
import com.locuslink.model.ProductSubType;
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
    private IndustryDao industryDao;
    
    @Autowired
    private SubIndustryDao subIndustryDao;
    
    @Autowired
    private ProductTypeDao productTypeDao;
    
    @Autowired
    private ProductSubTypeDao productSubTypeDao;
    
    
    
	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;



    
	@PostMapping(value = "/initTestHarness")
	public String initTestHarness (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initTestHarness()...");
		
		List <UidDTO> uidDtoList = industryDao.getUidDTO(0, 0, 0, 0, 0);
		logger.debug("uidDtoList size ->: " + uidDtoList.size());
	   	model.addAttribute("uidDtoList", uidDtoList);		 
		
	   	// Industry List
	    List<UidDTO> uidDtoIndustryList = io.vavr.collection.List.ofAll(uidDtoList)	   			
	    	.distinctBy(UidDTO::getIndustryPkId)
	    	.toJavaList();
	   	model.addAttribute("uidDtoIndustryList", uidDtoIndustryList);	 
		logger.debug("uidDtoIndustryList size ->: " + uidDtoIndustryList.size());
	   	
		// Sub Industry List
	    List<UidDTO> uidDtoSubIndustryList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getSubIndustryPkId)
		    .toJavaList();
		model.addAttribute("uidDtoSubIndustryList", uidDtoSubIndustryList);	 
		logger.debug("uidDtoSubIndustryList size ->: " + uidDtoSubIndustryList.size());
		   	
		
		
//		List <Industry> industryList = industryDao.getAll();	
//	   	model.addAttribute("industryList", industryList);	 
//		logger.debug("industryList size ->: " + industryList.size());
		
		
		
//  		   	
//		List <SubIndustry> subIndustryList = subIndustryDao.getAll();	
//	   	model.addAttribute("subIndustryList", subIndustryList);	 
//		logger.debug("subIndustryList size ->:  " +  subIndustryList.size());
//	   	
//		List <ProductType> productTypeList = productTypeDao.getAll();	
//	   	model.addAttribute("productTypeList", productTypeList);		   	
//	   	logger.debug("productTypeList size ->: " + productTypeList.size());
//		   		   		   	
//		List <ProductSubType> productSubTypeList = productSubTypeDao.getAll();	
//	   	model.addAttribute("productSubTypeList", productSubTypeList);		   	
//	   	logger.debug("productSubTypeList size ->: " + productSubTypeList.size());
		   	

	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processIndustrySelected")
	public String processIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryUid());

///////////////////////////////////////////////////////////////////////////////////////////////////////////		
		Industry industry = industryDao.getById(wrkIndustryPkId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industry ->:   pkId: " +  wrkIndustryPkId + "  uid:" + wrkIndustryUid);
		}
	  	model.addAttribute("industryList", industryList);	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	  	
	  	
	  	// Use PArent PK
	  	// Get List of Sub Industries for the Selected Industry   	
		List <SubIndustry> subIndustryList = subIndustryDao.getByIndustry(wrkIndustryPkId);	
	   	model.addAttribute("subIndustryList", subIndustryList);	 
		logger.debug("subIndustryList size ->:  " + subIndustryList.size());
	   	
	   	// Get List of Product Types, not selected yet so we need to display them all
		List <ProductType> productTypeList = productTypeDao.getAll();
	   	model.addAttribute("productTypeList", productTypeList);		   	
	   	logger.debug("productTypeList size ->: " + productTypeList.size());
		   		
	   	// Get List of Product Sub Types, not selected yet so we need to display them all
		List <ProductSubType> productSubTypeList = productSubTypeDao.getAll();	
	   	model.addAttribute("productSubTypeList", productSubTypeList);		   	
	   	logger.debug("productSubTypeList size ->: " + productSubTypeList.size());
	   	
	   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processSubIndustrySelected")
	public String processSubIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processSubIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryUid());
		
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());
		int wrkSubIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryUid());	
		
		

		// Get the Selected Industry  
		Industry industry = industryDao.getById(wrkIndustryPkId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industry ->:   pkId: " +  wrkIndustryPkId + "  uid:" + wrkIndustryUid);
		}
	  	model.addAttribute("industryList", industryList);
	  	
	  	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	  		  	
	  	// Get the selected Sub Industry for the Selected Industry   	
		SubIndustry subIndustry = subIndustryDao.getById(wrkSubIndustryPkId);	
		List <SubIndustry> subIndustryList = new ArrayList<SubIndustry> ();
		if (subIndustry != null) {
			subIndustryList.add(subIndustry);
		    logger.debug("sub industry ->:   pkId: " +  wrkSubIndustryPkId + "  uid:" + wrkSubIndustryUid);
		}
	   	model.addAttribute("subIndustryList", subIndustryList);	 
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	   	
	   	
	   	// Use PArent PkId
	   	// Get the selected Products for the Selected Sub Industry   	
	   	List <ProductType> productTypeList = productTypeDao.getBySubIndustry(wrkSubIndustryPkId);	   	
		logger.debug("productTypeList size ->: " + productTypeList.size());	
	   	model.addAttribute("productTypeList", productTypeList);		   	

	   	
	   	// Get List of Product Sub Types, not selected yet so we need to display them all
		List <ProductSubType> productSubTypeList = productSubTypeDao.getAll();	
	   	model.addAttribute("productSubTypeList", productSubTypeList);		   	
	   	logger.debug("productSubTypeList size ->: " + productSubTypeList.size());
	   	
	   			   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	
	
	@PostMapping(value = "/processProductTypeSelected")
	public String processProductTypeSelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processProductTypeSelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryUid());		
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());
		int wrkSubIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryUid());		
		int wrkProductTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypePkId());
		int wrkProductTypeUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypeUid());		


		// Get the Selected Industry  
		Industry industry = industryDao.getById(wrkIndustryPkId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industry ->:   pkId: " +  wrkIndustryPkId + "  uid:" + wrkIndustryUid);
		}
	  	model.addAttribute("industryList", industryList);		
	  	
	  	// Get the selected Sub Industry for the Selected Industry   	
		SubIndustry subIndustry = subIndustryDao.getById(wrkIndustryPkId);	
		List <SubIndustry> subIndustryList = new ArrayList<SubIndustry> ();
		if (subIndustry != null) {
			subIndustryList.add(subIndustry);
		    logger.debug("sub industry ->:   pkId: " +  wrkSubIndustryPkId + "  uid:" + wrkSubIndustryUid);
		}
	   	model.addAttribute("subIndustryList", subIndustryList);	 
		 	
	   	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	   	
	   	// Get the selected Product {Asset Type } 
	   	ProductType productType = productTypeDao.getById(wrkProductTypePkId)	;
	   	List <ProductType> productTypeList = new ArrayList<ProductType> ();	
		if (productTypeList != null) {
			productTypeList.add(productType);
		    logger.debug("ProductType ->:   pkId: " +  wrkProductTypePkId + "  uid:" + wrkProductTypeUid);
		}		
	   	model.addAttribute("productTypeList", productTypeList);		   	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	   	
	   	
	   	// Use Parent Pk
	   	// Get List of Product Sub Types based on the Product Type selected
		List <ProductSubType> productSubTypeList = productSubTypeDao.getByProductType(wrkProductTypePkId);
	   	model.addAttribute("productSubTypeList", productSubTypeList);		   	
	   	logger.debug("productSubTypeList size ->: " + productSubTypeList.size());	   	
	   	
	   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	

	
	
	
	
	@PostMapping(value = "/processProductSubTypeSelected")
	public String processProductSubTypeSelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processProductSubTypeSelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryUid());		
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());
		int wrkSubIndustryUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryUid());		
		int wrkProductTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypePkId());
		int wrkProductTypeUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypeUid());		

		int wrkProductSubTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductSubTypePkId());
		int wrkProductSubTypeUid = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductSubTypeUid());	

		// Get the Selected Industry  
		Industry industry = industryDao.getById(wrkIndustryPkId);			
		List <Industry> industryList = new ArrayList<Industry> ();
		if (industry != null) {
			industryList.add(industry);
		    logger.debug("industry ->:   pkId: " +  wrkIndustryPkId + "  uid:" + wrkIndustryUid);
		}
	  	model.addAttribute("industryList", industryList);		
	  	
	  	// Get the selected Sub Industry for the Selected Industry   	
		SubIndustry subIndustry = subIndustryDao.getById(wrkIndustryPkId);	
		List <SubIndustry> subIndustryList = new ArrayList<SubIndustry> ();
		if (subIndustry != null) {
			subIndustryList.add(subIndustry);
		    logger.debug("sub industry ->:   pkId: " +  wrkSubIndustryPkId + "  uid:" + wrkSubIndustryUid);
		}
	   	model.addAttribute("subIndustryList", subIndustryList);	 
	   	
		 	
	   	// Get the selected Product {Asset Type } 
	   	ProductType productType = productTypeDao.getById(wrkProductTypePkId);
	   	List <ProductType> productTypeList = new ArrayList<ProductType> ();	
		if (productTypeList != null) {
			productTypeList.add(productType);
		    logger.debug("ProductType ->:   pkId: " +  wrkProductTypePkId + "  uid:" + wrkProductTypeUid);
		}		
	   	model.addAttribute("productTypeList", productTypeList);	
	   	
		   		   		   	
	   	// Get List of Product Sub Types based on the Product Type selected
	   	ProductSubType productSubType = productSubTypeDao.getById(wrkProductSubTypePkId);
		List <ProductSubType> productSubTypeList = new ArrayList<ProductSubType> ();	
		
		if (productSubTypeList != null) {
			productSubTypeList.add(productSubType);
		    logger.debug("ProductSubType ->:   pkId: " +  wrkProductSubTypePkId + "  uid:" + wrkProductSubTypeUid);
		}	
	   	model.addAttribute("productSubTypeList", productSubTypeList);		   	
	   	logger.debug("productSubTypeList size ->: " + productSubTypeList.size());	   	
	   	
	   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	
	
	
	
	
	
}