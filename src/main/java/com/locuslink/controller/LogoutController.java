package com.locuslink.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locuslink.common.SecurityContextManager;
import com.locuslink.dto.LoginFormDTO;


/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - April 20, 2022 - Initial version
 */
@Controller
@Service
public class LogoutController {

    @Autowired
    private SecurityContextManager securityContextManager;


	private static final Logger logger = Logger.getLogger(LogoutController.class);

    @Value("${https.enabled}")
    private boolean httpsEnabled;

    @Value("${app.logout.url}")
    private String appLogoutUrl;




    // Called by Spring security
    //@CrossOrigin()
    @RequestMapping(value = "/initLogout")
	public String initLogout(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		logger.debug(" initLogout()");

		loginFormDTO.setPassword("");
  	    loginFormDTO.setUsername("");

		session.invalidate();
		SecurityContextHolder.clearContext();

	    return "logout";

    }
}

    
//    	logger.debug("Starting Cognito logout process");
//
//        Cookie cookie = new Cookie("bogus", null);
//        for (Cookie c : request.getCookies()) {
//            if (c.getName().contains("AWS") || c.getName().contains("aws") ) {
//	            cookie = new Cookie(c.getName(), null);
//	            cookie.setMaxAge(0);
//	            cookie.setSecure(true);
//	            cookie.setHttpOnly(true);
//	            cookie.setPath("/");
//	            response.addCookie(cookie);
//            }
//        }
//
//
//        // delete cookie
//        cookie = new Cookie("MEDAWSELBAuthSessionCookie-0", null);
//        cookie.setMaxAge(0);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        cookie = new Cookie("MEDAWSELBAuthSessionCookie-1", null);
//        cookie.setMaxAge(0);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//
//		/*
//		 * response.setStatus(302); response.setHeader("Location", appLogoutUrl);
//		 */
//
//	   	model.addAttribute("appLogoutUrl",appLogoutUrl);
//
//        return "redirect:/initCognitoLogout";






