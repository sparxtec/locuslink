package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.DashboardFormDTO;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 05, 2023 - Initial version
 */
@Controller
@Service
public class UploadController {

	private static final Logger logger = Logger.getLogger(UploadController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;

	//@Value("${spring.upload.nfs.fileDirectoryPath}")
	//String nfsPath;
	
	//String csvUploadPath = "/lv_storage/All_IntegrityManagement_CSVFiles";
	
	
//	@Autowired
//	private FileStorageService fileStorageService;


	@PostMapping(value = "/initUpload")
	public String initUpload (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initUpload()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/upload";
	}


	@PostMapping(value = "/initUploadStep2")
	public String initUploadStep2 (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initUploadStep2()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/uploadstep2";
	}

	
	
	
	
	@PostMapping(value = "/processCsvFileUpload", produces = "application/json")
	public @ResponseBody GenericMessageResponse processCsvFileUpload(@RequestParam("file") MultipartFile file,
			Model model, @ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,
			HttpSession session) {

//		UserTrace user = (UserTrace) session.getAttribute("userTrace");
//		if (user == null) {
//			logger.debug(" userTrace in session does not exist");
//		}
		logger.debug("Starting processFileUpload()..file ->:" + file.getOriginalFilename());
		GenericMessageResponse response = new GenericMessageResponse("1.0", "json", "trace - processCsvFileUpload");

		try {
//			String jobFilePath = nfsPath + csvUploadPath;
//
//			if (file.getOriginalFilename().contains(".csv") || file.getOriginalFilename().contains(".xlsx")) {
//				fileStorageService.storeAtGivenTotalPath(file, jobFilePath, file.getOriginalFilename());
//			}
			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
			logger.debug(" CSV fileUpload Worked,  size ->: " + file.getSize());

		} catch (Exception e) {
			logger.debug("  ERROR csvFileUpload failed ->: " + e.getMessage());
		}
		return response;
	}
	
	
	
	
	
	/*
	 * MultipartFile Upload - DropZone
	 */
	@PostMapping(value = "/processFileUpload")
	public String processFileUpload(@RequestParam("file") MultipartFile file, Model model,
			@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO) {

		logger.debug("Starting processFileUpload()..file ->:" + file.getOriginalFilename());

//		// CS 5-14-2020
//		String itemNumber = docUploadFormDTO.getItemNumber();
//		String poNumber = docUploadFormDTO.getPurchaseOrderNumber();
//		boolean isItemUploadProcessing = false;
//		if (poNumber.equalsIgnoreCase("notUSed") && !itemNumber.isEmpty()) {
//			logger.debug("Processing an MTR uploading to an ITEM.  {no po}");
//			isItemUploadProcessing = true;
//		}

		// CS 5-14-2020 - if this is an Item Upload, store the files in a different
		// folder location
		try {
//			String purchaseOrderNum = "";
//			String jobFilePath = "";
//			if (isItemUploadProcessing) {
//				jobFilePath = "/allItemUploads/Item" + itemNumber;
//			} else {
//				EhubPurchaseOrder ehubPurchaseOrder = ehubPurchaseOrderService
//						.getByPoNumber(docUploadFormDTO.getPurchaseOrderNumber());
//				purchaseOrderNum = ehubPurchaseOrder.getPurchaseOrderNum();
//				jobFilePath = "/po" + purchaseOrderNum;
//			}
//
//			fileStorageService.store(file, jobFilePath);

			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
			logger.debug("  fileUpload Worked,  size ->: " + file.getSize());
			
			model.addAttribute("dashboardFormDTO", dashboardFormDTO);
			
			return "fragments/docuploadpage3";

		} catch (Exception e) {
			logger.debug("  ERROR fileUpload failed ->: " + e.getMessage());
			return "fragments/docuploadstep2";
		}
	}

}