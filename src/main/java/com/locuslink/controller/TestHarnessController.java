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
		   					
		// Product Type List
	    List<UidDTO> uidDtoProductTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductTypePkId)
		    .filter(f -> !(f.getProductTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductTypeList", uidDtoProductTypeList);	 
		logger.debug("uidDtoProductTypeList size ->: " + uidDtoProductTypeList.size());
		
		// Sub Product Type List
	    List<UidDTO> uidDtoProductSubTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductSubTypePkId)
		    .filter(f -> !(f.getProductSubTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductSubTypeList", uidDtoProductSubTypeList);	 
		logger.debug("uidDtoProductSubTypeList size ->: " + uidDtoProductSubTypeList.size());
		
		
		

	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processIndustrySelected")
	public String processIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
	
	   	// SELECTED Industry 
		List <UidDTO> uidDtoList = industryDao.getUidDTO(wrkIndustryPkId, 0, 0, 0, 0);
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
		   					
		// Product Type List
	    List<UidDTO> uidDtoProductTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductTypePkId)
		    .filter(f -> !(f.getProductTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductTypeList", uidDtoProductTypeList);	 
		logger.debug("uidDtoProductTypeList size ->: " + uidDtoProductTypeList.size());
		
		// Sub Product Type List
	    List<UidDTO> uidDtoProductSubTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductSubTypePkId)
		    .filter(f -> !(f.getProductSubTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductSubTypeList", uidDtoProductSubTypeList);	 
		logger.debug("uidDtoProductSubTypeList size ->: " + uidDtoProductSubTypeList.size());
		
   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	@PostMapping(value = "/processSubIndustrySelected")
	public String processSubIndustrySelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processSubIndustrySelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());
		
		
	   	// SELECTED Industry & SUB INDUSTRY
		List <UidDTO> uidDtoList = industryDao.getUidDTO(wrkIndustryPkId, wrkSubIndustryPkId, 0, 0, 0);
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
		   					
		// Product Type List
	    List<UidDTO> uidDtoProductTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductTypePkId)
		    .filter(f -> !(f.getProductTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductTypeList", uidDtoProductTypeList);	 
		logger.debug("uidDtoProductTypeList size ->: " + uidDtoProductTypeList.size());
		
		// Sub Product Type List
	    List<UidDTO> uidDtoProductSubTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductSubTypePkId)
		    .filter(f -> !(f.getProductSubTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductSubTypeList", uidDtoProductSubTypeList);	 
		logger.debug("uidDtoProductSubTypeList size ->: " + uidDtoProductSubTypeList.size());
		
		
		
   			   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	
	
	@PostMapping(value = "/processProductTypeSelected")
	public String processProductTypeSelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processProductTypeSelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());	
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());	
		int wrkProductTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypePkId());
	
		
		
	   	// SELECTED Industry & SUB INDUSTRY & Product Type
		List <UidDTO> uidDtoList = industryDao.getUidDTO(wrkIndustryPkId, wrkSubIndustryPkId, 0, wrkProductTypePkId, 0);
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
		   					
		// Product Type List
	    List<UidDTO> uidDtoProductTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductTypePkId)
		    .filter(f -> !(f.getProductTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductTypeList", uidDtoProductTypeList);	 
		logger.debug("uidDtoProductTypeList size ->: " + uidDtoProductTypeList.size());
		
		// Sub Product Type List
	    List<UidDTO> uidDtoProductSubTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductSubTypePkId)
		    .filter(f -> !(f.getProductSubTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductSubTypeList", uidDtoProductSubTypeList);	 
		logger.debug("uidDtoProductSubTypeList size ->: " + uidDtoProductSubTypeList.size());
   	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	

	
	
	
	
	@PostMapping(value = "/processProductSubTypeSelected")
	public String processProductSubTypeSelected (@ModelAttribute(name = "uidGeneratorFormDTO") UidGeneratorFormDTO uidGeneratorFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting processProductSubTypeSelected()...");
		 
		// Get the Industry Selected
		int wrkIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedIndustryPkId());
		int wrkSubIndustryPkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedSubIndustryPkId());		
		int wrkProductTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductTypePkId());	
		int wrkProductSubTypePkId = Integer.valueOf(uidGeneratorFormDTO.getSelectedProductSubTypePkId());

		
	   	// SELECTED Industry & SUB INDUSTRY & Product Type
		List <UidDTO> uidDtoList = industryDao.getUidDTO(wrkIndustryPkId, wrkSubIndustryPkId, 0, wrkProductTypePkId, wrkProductSubTypePkId);
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
		   					
		// Product Type List
	    List<UidDTO> uidDtoProductTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductTypePkId)
		    .filter(f -> !(f.getProductTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductTypeList", uidDtoProductTypeList);	 
		logger.debug("uidDtoProductTypeList size ->: " + uidDtoProductTypeList.size());
		
		// Sub Product Type List
	    List<UidDTO> uidDtoProductSubTypeList = io.vavr.collection.List.ofAll(uidDtoList)	   			
		    .distinctBy(UidDTO::getProductSubTypePkId)
		    .filter(f -> !(f.getProductSubTypePkId() == null) )	
		    .toJavaList();
		model.addAttribute("uidDtoProductSubTypeList", uidDtoProductSubTypeList);	 
		logger.debug("uidDtoProductSubTypeList size ->: " + uidDtoProductSubTypeList.size());
		
 	
	   	model.addAttribute("uidGeneratorFormDTO", uidGeneratorFormDTO);
	   	
		return "fragments/testing_harness2";
	}
	
	
	
	
	
	
	
	
	
}