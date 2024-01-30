package com.locuslink.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.UserLocuslinkDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UserDTO;
import com.locuslink.model.UserLocuslink;

/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - January 03, 2023 - Initial version
 */
@Controller
@Service
public class DashboardController {

	private static final Logger logger = Logger.getLogger(DashboardController.class);

    @Value("${https.enabled}")
    private boolean httpsEnabled;

    @Value("${app.logout.url}")
    private String appLogoutUrl;

    @Autowired
    private SecurityContextManager securityContextManager;

    
    @Autowired
    private UserLocuslinkDao userLocuslinkDao;
    

    
	@PostMapping(value = "/initDashboard")
	//public String initDashboard(Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {
	public String initDashboardPost(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {
	
		logger.debug("Starting DashbaordPost()...");

		// Common Routine
		if (securityContextManager.isSecurityCredentialsGood()) {
		   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
			return "dashboard";
		} else {
			return "redirect:/initLogin";
		}
	}
		
	@RequestMapping(value = "/initDashboard")
	//public String initDashboard(Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {
	public String initDashboard(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {
		
		logger.debug("Starting Dashbaord()...");

		// SQL Testing RDS
		UserLocuslink  userLocuslink= userLocuslinkDao.getById(1);
		if (userLocuslink == null) {
			logger.debug("SQL Error could not find the logged in User.");
		}
		
		// Common Routine
		if (securityContextManager.isSecurityCredentialsGood()) {			
		   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);			
			return "dashboard";
		} else {
			return "redirect:/initLogin";
		}

	}



}