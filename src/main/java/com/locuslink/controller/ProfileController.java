package com.locuslink.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.locuslink.dto.DashboardFormDTO;


/**
 * This is a Spring MVC Controller.
 * 
 * @author C.Sparks
 * @since 1.0.0 - December 22, 2023 - Initial version
 */
@Controller
@Service
public class ProfileController {
 
	private static final Logger logger = Logger.getLogger(ProfileController.class);
	
	
//	@Autowired
//	@Qualifier(UserMembershipDao.BEAN_NAME)
//	private UserMembershipDao userMembershipDao;
//	
//	@Autowired
//	@Qualifier(UserRoleTypeDao.BEAN_NAME)
//	private UserRoleTypeDao userRoleTypeDao;
	

	@PostMapping(value = "/initManageUsers")
	public String initManageUsers (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
		logger.debug("Starting initManageUsers()...");

		
//		UserTrace user=(UserTrace)session.getAttribute ("userTrace");
//	 
//		if (user == null) {
//			logger.debug(" userTrace in session does not exist");
//			return "login";
//		} 
//		logger.debug(user.getLoginId());
//		
//		UserTrace latestUserProfile=userTraceService.getUserByLanId(user.getLoginId());
//		UserMembership userMembership= userMembershipDao.getByUser(user);
//		
//		List<UserRoleType> roleList=userRoleTypeDao.getAllUserRoles();
//		System.out.println(user.getExternalContractorCompanyName());
//		
//		model.addAttribute("userTrace",latestUserProfile);
//		model.addAttribute("userMembership", userMembership);
//		model.addAttribute("roleList",roleList);
		
		
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

		return "fragments/manage-users";
	}
    

		

		  


//	 @RequestMapping(value = "/getAllActiveUsers", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
//     public @ResponseBody GenericMessageResponse getAllActiveUsers(@RequestBody GenericMessageRequest request, HttpSession session)  {
//
//		logger.debug("In getAllActiveUsers()");
//		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getAllUser");
//		
//		
//		//List<UserTrace> userTraceList =userTraceService.getAllActiveUsers();
//		//List<UserFormDTO> userFormDtoList = new ArrayList<UserFormDTO>();
//	
//		getUserList(userTraceList,userFormDtoList);
//		
//		response.setField("userList",userFormDtoList);
//		
//		return response;
//	 }
	 
	 

}