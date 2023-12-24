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
import com.locuslink.dto.LoginFormDTO;

@Controller
@Service
public class LoginController {

   @Autowired
   private SecurityContextManager securityContextManager;


	private static final Logger logger = Logger.getLogger(LoginController.class);

	
    @Value("${app.logout.url}")
    private String appLogoutUrl;
    
    
    
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
		if (loginFormDTO.getUsername().equals("admin")) {
			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		} else if (loginFormDTO.getUsername().equals("user")) {
			securityContextManager.processSecurityContext( session , "User", "locuslink-user", appLogoutUrl);	
		} else {
			return "login";
		}
		
		return "dashboard";
	}
	
	
	@RequestMapping(value = "/processLogin", method=RequestMethod.GET)
	public String loginGet(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		//logger.debug("Starting loginGet()..." + headers);
		logger.debug("Starting loginGet()...");
	  
		// Common Routine
		if (loginFormDTO.getUsername().equals("admin")) {
			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		} else if (loginFormDTO.getUsername().equals("user")) {
			securityContextManager.processSecurityContext( session , "User", "locuslink-user", appLogoutUrl);	
		} else {
			return "login";
		}
		
		return "dashboard";
	}
	
}