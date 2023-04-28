package com.locuslink.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.LoginFormDTO;


/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - April 20, 2022 - Initial version
 */
@Controller
@Service
public class LoginController {

    @Autowired
    private SecurityContextManager securityContextManager;


	private static final Logger logger = Logger.getLogger(LoginController.class);

    @Value("${https.enabled}")
    private boolean httpsEnabled;

    @Value("${app.logout.url}")
    private String appLogoutUrl;



//    @RequestMapping(value = "/initCognitoLogout")
//	public String initCognitoLogout(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session) {
//
//		logger.debug(" initCognitoLogout()");
//
//	   	model.addAttribute("appLogoutUrl",appLogoutUrl);
//
//		return "logout";
//    }


	/**
	 * 	C.Sparks  04-20-2022
	 *  	This routine should only be used in the lower environments, when:
	 *  	 	-https is not turned on
	 *  		- the initLogin failed for some reason
	 *  		- come here manually, is using the ALB instead of the DNS. ALB should be off in the higher environments.
	 */
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String login(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		logger.debug("Starting login()..." + headers);

		// Common Routine
		if (loginFormDTO.getUsername().equals("admin")) {
			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		} else if (loginFormDTO.getUsername().equals("user")) {
			securityContextManager.processSecurityContext( session , "User", "locuslink-user", appLogoutUrl);	
		} else {
			return "login";
		}

	   	model.addAttribute("appLogoutUrl",appLogoutUrl);

		return "dashboard";
	}

  
// 4-28-2023 demo settings
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String loginGet(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		logger.debug("Starting loginGet()..." + headers);

		loginFormDTO.setUsername("admin");
		securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
	   	model.addAttribute("appLogoutUrl",appLogoutUrl);

	   	
	   	// TODO
	   	model.addAttribute("cameFromLocusViewBarCodeViewer","yes");
	   	
	   	
		return "dashboard";
	}
	
	
	
	
	
	
	
	
	
	
		// 4-28-2023 demo settings
		@RequestMapping(value = "/viewAsset", method=RequestMethod.GET)
		public String viewAsset(@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

			logger.debug("Starting viewAsset()..." + headers);

			securityContextManager.processSecurityContext( session , "Admin", "locuslink-admin", appLogoutUrl);
		   	model.addAttribute("appLogoutUrl",appLogoutUrl);

		   	// TODO
		   	model.addAttribute("cameFromLocusViewBarCodeViewer","yes");
		   			   	
		   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		   				
			return "dashboard";
		}
	


		
		
		
		
		
		
		
		
		
		
		
	/**
	 * 	C.Sparks  04-20-2022
	 *  	This is the main entry point. If https is enabled, then we need the JWT token or its an error.
	 */
	@RequestMapping(value = "/initLogin")
	public String initLogin(@ModelAttribute(name = "loginFormDTO") LoginFormDTO loginFormDTO, Model model, HttpSession session, HttpServletRequest request, @RequestHeader Map<String,String> headers) {

		logger.debug("Starting login()...");

		if ( !processAuthenticateUser(request, loginFormDTO)) {
			loginFormDTO.setSAMLResponse("  Error. Some kind of authentication errored occured. Call technical support.");
	        model.addAttribute("loginFormDTO", loginFormDTO);
		   	model.addAttribute("appLogoutUrl",appLogoutUrl);
			return "login";
		}

	   	model.addAttribute("appLogoutUrl",appLogoutUrl);

		logger.debug("  SAML and JWT processing successful, letting the user proceed to the application.");

		return "dashboard";
	}



	public boolean processAuthenticateUser(HttpServletRequest request, LoginFormDTO loginFormDTO) {

		/////////////////////////  JWT Token Processing /////////////////////////////////
        String encryptedJwt = "";
        if (httpsEnabled) {
        	encryptedJwt = request.getHeader("x-amzn-oidc-data");
        } else {
        	//encryptedJwt = "eyJ0eXAiOiJKV1QiLCJraWQiOiIwZGE2OWE2MC00NThlLTRlMjAtYmJlZS0wMmRjYWFiZDI5OTAiLCJhbGciOiJFUzI1NiIsImlzcyI6Imh0dHBzOi8vY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb20vdXMtZWFzdC0xX2V4WlFqN3NiVSIsImNsaWVudCI6IjU4M21rc3A4aGRlNGx0bG1oYmlrMDhoMnQ2Iiwic2lnbmVyIjoiYXJuOmF3czplbGFzdGljbG9hZGJhbGFuY2luZzp1cy1lYXN0LTE6NzYxOTQwODk4NDUzOmxvYWRiYWxhbmNlci9hcHAvbWVkYXBwLXRlc3QtaW50ZXJuYWwtbGIvMjYzMjUxMGE3ZmFlNjJkNyIsImV4cCI6MTY1MDM4OTIzOH0=.eyJzdWIiOiI4ZDMzN2FmOC0zOWE3LTQ4ZDYtYTFiZS1hNDcwMWYyOGM4NjkiLCJjdXN0b206VUlEIjoiTUVEQURNSU5UV08iLCJjdXN0b206Y21zUm9sZXMiOiJtZWQtYWRtaW4iLCJ1c2VybmFtZSI6ImNtcy1tZWQtdGVzdC1pZG1va3RhX21lZGFkbWludHdvIiwiZXhwIjoxNjUwMzg5MjM4LCJpc3MiOiJodHRwczovL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tL3VzLWVhc3QtMV9leFpRajdzYlUifQ==.Ibn2NN034bF4awG_V_Yg1raNTCZNBo7XnPMyfn2V-Kicjd8j76YVq9VQIJbOfzF7KqjhQUuQCwkRt6W-BH7RKA==";
        	return false;
        }


        String jwtHeader = "notFound";
        String jwtBody = "notFound";
        boolean isJwtTokenFound = false;
        if (encryptedJwt != null && !encryptedJwt.equals("")) {

            java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();

        	String[] chunks = encryptedJwt.split("\\.");

            String base64EncodedHeader = chunks[0];
            String base64EncodedBody = chunks[1];
           // String base64EncodedSignature = chunks[2];

         //   System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
            jwtHeader = new String(decoder.decode(base64EncodedHeader));

        //    System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
            jwtBody = new String(decoder.decode(base64EncodedBody));

            isJwtTokenFound = true;
        } else {
            loginFormDTO.setHeaderInfo(" Error JWT processing failed. JWT was not passed in the Header.   SAML  JWT_Header->:" + jwtHeader + "   JWT_Body ->:" + jwtBody);
    		logger.debug(" Error JWT processing failed. JWT was not passed in the Header.");
        	return false;
        }
        loginFormDTO.setHeaderInfo(" SAML  JWT_Header->:" + jwtHeader + "   JWT_Body ->:" + jwtBody);
        logger.debug(" SAML  JWT_Header->:" + jwtHeader + "   JWT_Body ->:" + jwtBody);

		/////////////////////////  UID and Role Parsing  /////////////////////////////////
        String jwtUid = "notFound";
        String jwtCmsRoles = "notFound";
        if (!isJwtTokenFound) {
            loginFormDTO.setSAMLResponse(" Error JWT processing failed. Could not extract the UID or Role.:   UID ->:" + jwtUid + "   ROLE->:" + jwtCmsRoles);
      		logger.debug(" Error JWT processing failed. Could not extract the UID or Role.");
      		return false;
        }

        //jwtUid = "MEDADMINTWO";
        //jwtCmsRoles = "med-admin";
        JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jwtBody);
			jwtUid = jsonObject.getString("custom:UID");
			jwtCmsRoles = jsonObject.getString("custom:cmsRoles");
		} catch (JSONException e) {
            loginFormDTO.setSAMLResponse("  Error could not parse the jwtBody.    JWT_Header->:" + jwtHeader + "   JWT_Body ->:" + jwtBody);
      		logger.debug("  JWT processing failed. Error could not parse the jwtBody. ");
      		return false;
		}

        loginFormDTO.setSAMLResponse("  JWT:    UID ->:" + jwtUid + "   ROLE->:" + jwtCmsRoles);
		logger.debug("  JWT:    UID ->:" + jwtUid + "   ROLE->:" + jwtCmsRoles);


		/////////////////////////  Spring Security Context  /////////////////////////////////
		//processSpringSecurityContext(request.getSession(true) , jwtUid, jwtCmsRoles.toUpperCase());

		// Common Routine
		if (securityContextManager.processSecurityContext(request.getSession(true) , jwtUid, jwtCmsRoles.toUpperCase(), appLogoutUrl)) {
			return true;
		}

		return true;
	}



}