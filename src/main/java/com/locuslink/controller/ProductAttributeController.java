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
	
	
    
	
	
	
//	/**
//	 *   5-17-2023   Get all the attachments for a clicked Asset.
//	 */		
//	@RequestMapping(value = "/getAllAssetAttachments", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
//	public @ResponseBody GenericMessageResponse getAllAssetAttachments(@RequestBody GenericMessageRequest request, HttpSession session)  {
//
//		logger.debug("In getAllAssetAttachments()");
//		GenericMessageResponse response = new GenericMessageResponse("1.0", "LocusView", "getAllAssetAttachments");
//	  	
//		
//		String uniqueAssetPkId = request.getFieldAsString("uniqueAssetPkId");
//		if (uniqueAssetPkId == null || uniqueAssetPkId.length() < 1) {
//			logger.debug("Error - uniqueAssetPkId is missing");
//		} else {
//			logger.debug("  uniqueAssetPkId ->: " + uniqueAssetPkId);
//		}
//		
//		// TESTING
//		List <ProductAttachmentDTO> productAttachmentListDTO =  productAttachmentDao.getDtoByUniqueAssetId(Integer.valueOf(uniqueAssetPkId));
//		if (productAttachmentListDTO == null) {
//			logger.debug("  Note:  No Data Found......");
//		}
//			
//        // Convert the POJO array to json, for the UI
//		ObjectMapper mapper = new ObjectMapper();		
//		String json = "";			
//		try {
//			json = mapper.writeValueAsString(productAttachmentListDTO);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		logger.debug("json ->: " + json);		
//		response.setField("assetAttachmentlist",  json);
//
//		return response;
//	 }

	
	
	
	
//	@PostMapping(value = "/getAttachmentForAsset")
//	public String getAttachmentForAsset (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
//	
//		logger.debug("Starting getAttachmentForAsset()...");
//
//		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(dashboardFormDTO.getProductAttachPkId()));
//		if (productAttachment == null) {
//			logger.debug("  Error:  Product Attachment Lookup failed...");
//		}
//					
//        GetObjectRequest request = new GetObjectRequest(
//        	awsS3BucketName, 
//        	productAttachment.getFilenameFullpath() );
//                
//        S3Object s3Object = awsS3Client.getObject(request);
//        S3ObjectInputStream s3is = s3Object.getObjectContent();
//          
//		ByteArrayResource byteArrayResource = null;
//	
//		
//		
//		// TODO 6-22-2023
//		String encodedPDFBarcdeString = "";
//		JSONObject xlsJson = null;
//		if (s3Object.getKey().contains("xls")) {
//			dashboardFormDTO.setPdf(false);		
//			
//			//boolean result = convertExcelToPDF( s3is );			
//			//InputStream in = new ByteArrayInputStream(convertExcelToPDF( s3is ).toByteArray());
//
//			byteArrayResource = new ByteArrayResource( convertExcelToPDF( s3is ).toByteArray());
//			
//			
//		} else if (s3Object.getKey().contains("pdf"))  {
//			dashboardFormDTO.setPdf(true);
//			
//			try {
//				byteArrayResource = new ByteArrayResource( s3is.readAllBytes());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}	
//			
//			//encodedPDFBarcdeString = Base64.getEncoder().encodeToString(byteArrayResource.getByteArray());
//		}
//		
//		
//		encodedPDFBarcdeString = Base64.getEncoder().encodeToString(byteArrayResource.getByteArray());	
//				
//	   	model.addAttribute("encodedPDFBarcdeString", encodedPDFBarcdeString);		   	
//	   	model.addAttribute("productAttachPkId", productAttachment.getProductAttachPkId());		
//	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
//
//		return "fragments/modal_attachment_viewer";
//	}

	
	
	
	
	
	
	
	

	
}