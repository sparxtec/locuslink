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
import com.locuslink.dto.DashboardFormDTO;

/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - March 19, 2021 - Initial version
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


	@RequestMapping(value = "/initDashboard")
	public String initDashboard(Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		logger.debug("Starting Dashbaord()...");

		model.addAttribute("appLogoutUrl",appLogoutUrl);

		// Common Routine
		if (securityContextManager.isSecurityCredentialsGood()) {
			return "dashboard";
		} else {
			return "redirect:/initLogin";
		}

	}


	@PostMapping(value = "/initProfile")
	public String initProfile (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initProfile()...");

		model.addAttribute("appLogoutUrl",appLogoutUrl);

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		return "fragments/profile";
	}


}