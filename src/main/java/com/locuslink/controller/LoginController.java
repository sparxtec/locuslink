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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.UserLocuslinkDao;
import com.locuslink.dto.LoginFormDTO;
import com.locuslink.model.UserLocuslink;

@Controller
@Service
public class LoginController {

   @Autowired
   private SecurityContextManager securityContextManager;


	private static final Logger logger = Logger.getLogger(LoginController.class);

	
    @Value("${app.logout.url}")
    private String appLogoutUrl;
    
    @Autowired
    private UserLocuslinkDao userLocuslinkDao;
    
    
	@RequestMapping(value = "/initLogin")
	public String initLogin(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		logger.debug("Starting initLogin()...");
		
		return "login";
	}

	@RequestMapping(value = "/processLogin", method=RequestMethod.POST)
	public String loginPost(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		//logger.debug("Starting loginPost()..." + headers);
		logger.debug("Starting loginPost()...");
	  
		// Common Routine
		if (loginFormDTO.getUsername().equalsIgnoreCase("admin")) {
			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		} else if (loginFormDTO.getUsername().equalsIgnoreCase("view")) {
			securityContextManager.processSecurityContext( session , "View", "locuslink-user", appLogoutUrl);	
		} else {
			return "login";
		}
		
		
		// 2-23-2024		
		UserLocuslink userLocuslink = userLocuslinkDao.getUserByLanId(loginFormDTO.getUsername().toLowerCase());
		//String displayName	= 	userLocuslink.getFirstName() + " " + userLocuslink.getLastNameBusName();
		session.setAttribute("userLocuslink",userLocuslink ); 
		logger.debug(" Setting User for the Session ->: " + userLocuslink.getFirstName() + " " + userLocuslink.getLastNameBusName());
 
	   	
		return "dashboard";
	}
	
	
	@RequestMapping(value = "/processLogin", method=RequestMethod.GET)
	public String loginGet(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		//logger.debug("Starting loginGet()..." + headers);
		logger.debug("Starting loginGet()...");
	  
		// Common Routine
		if (loginFormDTO.getUsername().equalsIgnoreCase("admin")) {
			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		} else if (loginFormDTO.getUsername().equalsIgnoreCase("view")) {
			securityContextManager.processSecurityContext( session , "View", "locuslink-user", appLogoutUrl);	
		} else {
			return "login";
		}
		
		// 2-23-2024		
		UserLocuslink userLocuslink = userLocuslinkDao.getUserByLanId(loginFormDTO.getUsername().toLowerCase());
		//String displayName	= 	userLocuslink.getFirstName() + " " + userLocuslink.getLastNameBusName();
		session.setAttribute("userLocuslink",userLocuslink ); 
		logger.debug(" Setting User for the Session ->: " + userLocuslink.getFirstName() + " " + userLocuslink.getLastNameBusName());
		
		
		return "dashboard";
	}
	
	

	
}