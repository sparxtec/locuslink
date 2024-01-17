package com.locuslink.common;

import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author csparks
 * @since 1.0.0 - May 12, 2022 - Initial version
 *
 */
@Service(SecurityContextManager.BEAN_NAME)
public class SecurityContextManager {

    public static final String BEAN_NAME = "SecurityContextManager";

	private static final Logger logger = Logger.getLogger(SecurityContextManager.class);



	/**
	 * 6-2-2022 C.Sparks
	 *    Used by the back end, to verify user is authorized to call the URL's
	 *    PEN test prevention
	 */
	public boolean isUserAuthorized (String requiredRole ) {

	    SecurityContext sc = SecurityContextHolder.getContext();
	    if (sc == null || sc.getAuthentication() == null || sc.getAuthentication().getCredentials() == null ) {
	    	return false;
	    }

	    String credentials = sc.getAuthentication().getCredentials().toString() ;
		if (credentials.contains("med-admin") || credentials.contains("MED-ADMIN")) {
			// all good
		} else {
			logger.debug("  User came her in an un authenticated way. Calling app will decide what to do.");
	    	return false;
	    }

		return true;
	}


	/**
	 *  Called from various places on the back end to see if the Spring Security USER and Credentials are still good.
	 * @return
	 */
	public boolean isSecurityCredentialsGood ( ) {

	    SecurityContext sc = SecurityContextHolder.getContext();
	    if (sc == null || sc.getAuthentication() == null || sc.getAuthentication().getCredentials() == null ) {
	    	return false;
	    }

	    String credentials = sc.getAuthentication().getCredentials().toString() ;
		if (credentials.startsWith("locuslink-") || credentials.startsWith("LOCUSLINK-")) {
			// all good
		} else {
			logger.debug("  User came her in an un authenticated way, returngin to login.   credentials ->: " + credentials);
	    	return false;
	    }

		return true;
	}



	/**
	 *   Called from Login Controller to establish the Security Context passed to the UI pages on the front.
	 */
	public boolean processSecurityContext(HttpSession session, String jwtUid, String jwtCmsRoles, String appLogoutUrl ) {

		logger.debug(" in processSpringSecurityContext () " );

		// 7-11-2022
		//  The user can have multiple roles, from other systems, as well as multiple roles from within the application.
		//   we need to parse out just the LOCUSLINK roles
		//   then we need to take the lowest role found.

		StringTokenizer st = new StringTokenizer(jwtCmsRoles.toUpperCase(),",");
		String wrkRole = "";
		String locuslinkRoleWeWantToUse="";
	     while (st.hasMoreTokens()) {
	    	 wrkRole = st.nextToken().trim();
	    	 if (wrkRole.equals("LOCUSLINK-USER")) {
	    		 locuslinkRoleWeWantToUse = wrkRole;	    	
	    	 } else if (wrkRole.equals("LOCUSLINK-ADMIN")) {
	    		 if (locuslinkRoleWeWantToUse.equals("")) {
	    			 locuslinkRoleWeWantToUse = wrkRole;
	    		 }
	    	 }
	     }

       // UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(jwtUid, jwtCmsRoles,AuthorityUtils.createAuthorityList("ROLE_"+jwtCmsRoles.toUpperCase()));
	    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(jwtUid, locuslinkRoleWeWantToUse,AuthorityUtils.createAuthorityList("ROLE_"+locuslinkRoleWeWantToUse.toUpperCase()));
        SecurityContext sc = SecurityContextHolder.getContext();

        sc.setAuthentication(authReq);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
		session.setAttribute("locuslinkUser", jwtUid);
		session.setAttribute("locuslinkRole", locuslinkRoleWeWantToUse.toUpperCase());
		session.setAttribute("appLogoutUrl", appLogoutUrl);

		return true;
	}




}