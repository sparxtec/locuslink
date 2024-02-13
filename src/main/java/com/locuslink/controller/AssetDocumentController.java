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

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.dto.UniqueAssetDTO;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - February 01, 2024 - Initial version
 */
@Controller
@Service
public class AssetDocumentController {

	private static final Logger logger = Logger.getLogger(AssetDocumentController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

    @Autowired
    private UniversalCatalogDao universalCatalogDao;
    
    @Autowired
    private UniqueAssetDao uniqueAssetDao;
    
    @Autowired
    private ProductAttachmentDao productAttachmentDao;
    
     
	@PostMapping(value = "/initAssetDocuments")
	public String initAssetDocuments (@ModelAttribute(name = "uniqueAssetDTO") UniqueAssetDTO uniqueAssetDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initAssetDocuments()...");
		
		// TODO
		uniqueAssetDTO =  uniqueAssetDao.getDtoById(1);
		if (uniqueAssetDTO == null) {
			logger.debug("  ERROR:  DTO not found.");
		} 
		
		// TODO
		List <ProductAttachmentDTO> productAttachmentDTO_List = productAttachmentDao.getDtoByUniqueAssetId(62);
		if (productAttachmentDTO_List.size() > 0) {
			logger.debug("  Number attachments ->: " + productAttachmentDTO_List.size());
		} else {
			logger.debug("  Note:  No Attachments Found......");
		}
		
	   	model.addAttribute("uniqueAssetDTO", uniqueAssetDTO);
	   	model.addAttribute("productAttachmentDTO_List", productAttachmentDTO_List);	

	   	return "fragments/asset-document";
	}

}