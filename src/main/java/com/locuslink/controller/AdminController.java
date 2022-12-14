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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.DashboardFormDTO;
/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - March 19, 2021 - Initial version
 */
@Controller
@Service
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class);
	private static final String GENERIC_MESSAGE_REQUEST_DATA_KEY = "data";
	private static final String GENERIC_MESSAGW_REQUEST_ACTION_KEY = "action";
	private static final String INLINE_EDIT_ACTION_CREATE = "create";
	private static final String INLINE_EDIT_ACTION_EDIT = "edit";

    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;




	@PostMapping(value = "/initAdmin")
	public String initAdmin (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAdmin()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "fragments/viewsanctions";
	}


	@PostMapping(value = "/viewSanctions")
	public String viewSanctions(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting viewSanctions()...");
		model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "fragments/viewsanctions";

	}


	@RequestMapping(value = "/getSanctionList", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody GenericMessageResponse getSanctionList(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getSanctionList()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getSanctionList");
//		List<MedRef> sanctionList = new ArrayList<>();
//
//		sanctionList = medRefDao.getByCode("SN");
//        response.setField("sanctionList",sanctionList);
//
//        // 6-2-2022
//	    String credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString() ;
//	    response.setField("secRole", credentials.toUpperCase());


		return response;
	 }


	@RequestMapping(value = "/editAdminSanctionCode", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse editAdminSanctionCode(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In editAdminSanctionCode()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getUserList");
//
//		MedRef medRef = setMedRef(request);
//		String action = request.getData().get(GENERIC_MESSAGW_REQUEST_ACTION_KEY).toString();
//
//        // 6-2-2022
//	    String credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString() ;
//	    response.setField("secRole", credentials.toUpperCase());
//
//
//	    // 6-8-2022
//	    if (! MedDBUtils.isDataSpecialCharacterFree(medRef)) {
//	    	logger.debug("Specials Chars are not allowed");
//	    	return response;
//	    }
//
//
//	    // Data is good at this point
//		crudMedRef(medRef, action, medRefDao);


		return response;
	 }








	@PostMapping(value = "/viewErrors")
	public String viewErrors(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting viewErrors()...");
		model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "fragments/viewerrors";

	}

	@PostMapping(value = "/viewStates")
	public String viewStates(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting viewStates()...");
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "fragments/viewstates";
	}

	@PostMapping(value = "/viewGeneralSpecialties")
	public String viewGeneralSpecialties(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting viewGeneralSpecialties()...");
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "fragments/viewgeneralspecialties";
	}









	@RequestMapping(value = "/getErrorList", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getErrorList(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getErrorList()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getErrorList");

//		List<MedRef> errorList = new ArrayList<>();
//		errorList = medRefDao.getByCode("ER");
//		response.setField("errorList",errorList);
//
//        // 6-2-2022
//	    String credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString() ;
//	    response.setField("secRole", credentials.toUpperCase());

		return response;
	 }


	@RequestMapping(value = "/getStateList", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getStateList(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getStateList()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getStateList");
//		List<MedRef> stateList = new ArrayList<>();
//
//		stateList = medRefDao.getByCode("ST");
//		response.setField("stateList",stateList);
//
//        // 6-2-2022
//	    String credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString() ;
//	    response.setField("secRole", credentials.toUpperCase());

		return response;
	 }



	/**
	 * CRUD MedRef Entity
	 * @param medRef
	 * @param action
	 * @param medRefDao
	 */
//	private static void crudMedRef(MedRef medRef, String action, MedRefDao medRefDao) {
//
//		if(INLINE_EDIT_ACTION_EDIT.equals(action)) {
//
//			medRefDao.updateMedRef(medRef);
//		}else if(INLINE_EDIT_ACTION_CREATE.equals(action)) {
//
//			medRefDao.saveMedRef(medRef);
//		}
//	}



}