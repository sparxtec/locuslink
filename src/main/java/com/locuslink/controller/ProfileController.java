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

import com.locuslink.dao.UserLocuslinkDao;
import com.locuslink.dao.UserMembershipDao;
import com.locuslink.dao.UserRoleTypeDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UserDTO;
import com.locuslink.model.UserLocuslink;
import com.locuslink.model.UserMembership;
import com.locuslink.model.UserRoleType;



/**
 * This is a Spring MVC Controller.
 * 
 * @author C.Sparks
 * @since 1.0.0 - October 9, 2018 - Initial version
 */
@Controller
@Service
public class ProfileController {
 
	private static final Logger logger = Logger.getLogger(ProfileController.class);
	
    @Value("${app.logout.url}")
    private String appLogoutUrl;
    
    @Autowired
    private UserLocuslinkDao userLocuslinkDao;
    
    
	@Autowired
	private UserMembershipDao userMembershipDao;
	
	
	@Autowired
	private UserRoleTypeDao userRoleTypeDao;
	


	
	
	@PostMapping(value = "/initProfile")
	public String initProfile (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProfile()...");

//		UserTrace user=(UserTrace)session.getAttribute ("userTrace");
		
		
		
		//model.addAttribute("appLogoutUrl",appLogoutUrl);
		
		
	   	model.addAttribute("userDTO", new UserDTO());
	   	

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		return "fragments/profile";
	}

    
	@PostMapping(value = "/initProfileAccount")
	public String initProfileAccount (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProfileAccount()...");

		UserLocuslink userLocuslink=(UserLocuslink)session.getAttribute ("userLocuslink");		 
		if (userLocuslink == null) {
			logger.debug(" userLocuslink in session does not exist");
			return "login";
		} 		
		logger.debug(userLocuslink.getLoginName());
		
			
		List<UserRoleType> roleList=userRoleTypeDao.getAll();
		model.addAttribute("roleList",roleList);
			
	   	model.addAttribute("userLocuslink", userLocuslink);
	   	
		return "fragments/profile-account";
	}
	
	
	
	
	@PostMapping(value = "/saveProfileAccount")
	public String saveProfileAccount (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting saveProfileAccount()...");

		UserLocuslink userLocuslink=(UserLocuslink)session.getAttribute ("userLocuslink");		 
		if (userLocuslink == null) {
			logger.debug(" userLocuslink in session does not exist");
			return "login";
		} 		
		logger.debug(userLocuslink.getLoginName());
		
		
		
		// TODO Do the work o update values
		// If the value on the UI is different, then update the DB
		boolean userDataChanged = false;
		if (userLocuslink.getUserTypeCode() != dashboardFormDTO.getSelectedUserRoleCode() ) {
			
			// Role Changed, so we need to change the membership entry
			UserRoleType oldRoleType = userRoleTypeDao.getByCode(userLocuslink.getUserTypeCode());
			UserRoleType newRoleType = userRoleTypeDao.getByCode(dashboardFormDTO.getSelectedUserRoleCode());
			
			// Get  Membership Rows - and change the Role ype
			UserMembership userMembership = userMembershipDao.getById(oldRoleType.getUserRoleTypePkId());
			userMembership.setUserRoleTypePkId(newRoleType.getUserRoleTypePkId());
			userMembershipDao.saveOrUpdate(userMembership);
			

			userLocuslink.setUserTypeCode(dashboardFormDTO.getSelectedUserRoleCode());
			userDataChanged = true;						
		}
		
		if (userLocuslink.getFirstName() != dashboardFormDTO.getSelectedFirstName() ) {
			userLocuslink.setFirstName( dashboardFormDTO.getSelectedFirstName());
			userDataChanged = true;
		}
		if (userLocuslink.getLastNameBusName() != dashboardFormDTO.getSelectedLastNameBusName() ) {
			userLocuslink.setLastNameBusName( dashboardFormDTO.getSelectedLastNameBusName());
			userDataChanged = true;
		}
		if (userLocuslink.getEmailAddress()  != dashboardFormDTO.getSelectedEmailId() ) {
			userLocuslink.setEmailAddress(dashboardFormDTO.getSelectedEmailId());
			userDataChanged = true;
		}
		if (userLocuslink.getPhoneNumber() != dashboardFormDTO.getSelectedPhoneNumber() ) {
			userLocuslink.setPhoneNumber( dashboardFormDTO.getSelectedPhoneNumber());
			userDataChanged = true;
		}
		
		// Update the DB
		if (userDataChanged) {
			userLocuslinkDao.saveOrUpdate(userLocuslink);
		}
		
		// TODO Session update ??
		
			
		List<UserRoleType> roleList=userRoleTypeDao.getAll();
		model.addAttribute("roleList",roleList);
			
	   	model.addAttribute("userLocuslink", userLocuslink);
	   	
		return "fragments/profile-account";
	}

	
	
	
	@PostMapping(value = "/initProfileSettings")
	public String initProfileSettings (@ModelAttribute(name = "userDTO") UserDTO userDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProfileSettings()...");
		
		UserLocuslink userLocuslink=(UserLocuslink)session.getAttribute ("userLocuslink");		 
		if (userLocuslink == null) {
			logger.debug(" userLocuslink in session does not exist");
			return "login";
		} 		
		logger.debug(userLocuslink.getLoginName());


		
//		List<UserRoleType> roleList=userRoleTypeDao.getAllUserRoles();
//		model.addAttribute("roleList",roleList);
		  
	   	model.addAttribute("userLocuslink", userLocuslink);
	   	
	   	
	   //	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
	   	
		return "fragments/profile-settings";
	}
 
	
	@PostMapping(value = "/initProfileSupport")
	public String initProfileSupport (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProfileSupport()...");


	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		return "fragments/profile-support";
	}
	
	
}