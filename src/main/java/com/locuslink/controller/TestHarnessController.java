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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
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
    
//    @Autowired
//    private UidProductAttributeListDao uidProductAttributeListDao;   
    
    
    
    
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
	
	
	
	
	@RequestMapping(value = "/getUidProductAttributes", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getUidProductAttributes(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getUidProductAttributes()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getUidProductAttributes");
	  	
		int industryPkId = request.getFieldAsInt("industryPkId");
		int subIndustryPkId = request.getFieldAsInt("subIndustryPkId");
		int productTypePkId = request.getFieldAsInt("productTypePkId");
		int productSubTypePkId = request.getFieldAsInt("productSubTypePkId");
		logger.debug(" industryPkId ->: " + industryPkId);
		logger.debug(" subIndustryPkId ->: " + subIndustryPkId);
		logger.debug(" productTypePkId ->: " + productTypePkId);
		logger.debug(" productSubTypePkId ->: " + productSubTypePkId);

		
		
		
		
		// 10-19-2023 replace this TODO		
//		// TESTING
//		List <UidProductAttributeListDTO> uidProductAttributeList_List =  uidProductAttributeListDao.getDtoByProductType(industryPkId,subIndustryPkId,productTypePkId,productSubTypePkId ) ;
//		if (uidProductAttributeList_List == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
			
		
		
		
		
        // Convert the POJO array to json, for the UI
		ObjectMapper mapper = new ObjectMapper();		
		String json = "";			
		try {
			// TODO fix this json = mapper.writeValueAsString(uidProductAttributeList_List);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("json ->: " + json);		
		response.setField("uidProductAttributeList_List",  json);

		return response;
	 }
	
	
	
	
}