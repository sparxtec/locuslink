package com.locuslink.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.locuslink.common.GenericMessageRequest;
import com.locuslink.common.GenericMessageResponse;
import com.locuslink.dao.UserLocuslinkDao;
import com.locuslink.dao.UserMembershipDao;
import com.locuslink.dao.UserRoleTypeDao;
import com.locuslink.dto.DashboardFormDTO;
import com.locuslink.dto.UserDTO;
import com.locuslink.model.UserLocuslink;
import com.locuslink.model.UserMembership;



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

		model.addAttribute("appLogoutUrl",appLogoutUrl);

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
		return "fragments/profile";
	}

    
	
	@PostMapping(value = "/getAllUser", produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllUser(@RequestBody GenericMessageRequest request, HttpSession session)  {
	
		logger.debug("In getAllUser()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getAllUser");

		List<UserDTO> userDTOList =userLocuslinkDao.getAllDTO();
			
		response.setField("userList",userDTOList);
		return response;
	}
	
	
	
	@PostMapping(value = "/editUser")
	public String editUser (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		
//	@RequestMapping(value = "/editUser")	
   // public String editUser (Model model, HttpSession session) {
		logger.debug("In editUser()");
		
//		UserLocuslink user=(UserLocuslink)session.getAttribute ("userLocuslink");
//		 
//		 if (user == null) {
//				logger.debug(" userLocuslink in session does not exist");
//				return "login";
//			} 
//		
//		 logger.debug(user.getLoginId());
//
//		List<UserRoleType> roleList=userRoleTypeDao.getAllUserRoles();
//		model.addAttribute("roleList",roleList);
		  
	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
	   	
		return "fragments/edit_user";
      
    }	

	
// TODO
	
	@RequestMapping(value = "/addUser" , method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String addUser (@ModelAttribute(name="dashboardFormDTO") DashboardFormDTO dashboardFormDTO,Model model, HttpSession session) {
		logger.debug("In addUser()");
		return "";
		
//		UserLocuslink userLocuslink =new UserLocuslink();
//		UserRoleType userRoleType = new UserRoleType();
//
//		UserLocuslink loggedInUser=(UserLocuslink)session.getAttribute ("userLocuslink");
//		 //To check multiple entries of same user
//		UserLocuslink existingUserLocuslink =userLocuslinkService.getUserByLanId(userFormDTO.getLanId());
//		
//		 StringBuilder response = new StringBuilder(); 
//		 if(existingUserLocuslink!=null)
//		 {
//			 model.addAttribute("Error", "User Already present");
//			
//			    response.append("User Already present");
//			   return  response.toString();
//			// return "fragments/addUser";
//			 
//		 }

//		userRoleType=userRoleTypeDao.getByCode(UserRoleType.Code.valueOf(dashboardFormDTO.getRole()));
//		userLocuslink.setFirstName(userFormDTO.getFirstName().trim());
//		userLocuslink.setLastName(userFormDTO.getLastName().trim());
//		userLocuslink.setLoginId(userFormDTO.getLanId().trim());
//		userLocuslink.setDiscipline(disciplineService.getByCode(Discipline.Code.valueOf(userFormDTO.getDiscipline())));//disciplineService.getByCode(Code.valueOf(userFormDTO.getDiscipline())));//ds.getCode().valueOf(userFormDTO.getDiscipline())));
//		userLocuslink.setRegion(regionService.getByCode(Region.Code.valueOf(userFormDTO.getRegion())));
//		userLocuslink.setSessionExpirationTimeout(120);
//		userLocuslink.setSessionInactivityTimeout(60);
//		//userLocuslink.setExternalContractorCompanyName(userFormDTO.getCompany());
//		userLocuslink.setActiveFlag('Y');
//		userLocuslink.setCreateUserId(loggedInUser.getLoginId());
//		userLocuslinkService.saveUserLocuslinkFromMaximo(userLocuslink,userRoleType);
//		 response.append("Success");
//		  return  response.toString();
//		//return "fragments/addUser";
   
    }
	
	

    
    

	// 10-6-2023
	@RequestMapping(value = "/editRoles")	
    public String editRoles (Model model, HttpSession session) {
		logger.debug("In edit_roles()");
        return "fragments/edit_roles";
    }
	
	
	

	 
	// 10-6-23 
	@RequestMapping(value = "/getAllActiveUsers", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody GenericMessageResponse getAllActiveUsers(@RequestBody GenericMessageRequest request, HttpSession session)  {

		logger.debug("In getAllActiveUsers()");
		GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "getAllUser");
		
		
		// 10-6-2023 replace with a DTO 
		List<UserDTO> userDTOList =userLocuslinkDao.getAllDTO();
		
		response.setField("userList",userDTOList);
		
	
//		List<UserLocuslink> userLocuslinkList =userLocuslinkService.getAllActiveUsers();
//		List<DashboardFormDTO> dashboardFormDTOList = new ArrayList<UserFormDTO>();
//	
//		getUserList(userLocuslinkList,userFormDtoList);
		
		response.setField("userList",userDTOList);
		
		return response;
	 }
	 
	
	
	@RequestMapping(value = "/getAllRoles", method=RequestMethod.POST)
    public @ResponseBody List<String> getAllRoles (Model model, HttpSession session) {
		logger.debug("In getAllRoles()");
		return null;
//		UserLocuslink user =(UserLocuslink)session.getAttribute ("userLocuslink");
//		List<UserRoleType> roleList=userRoleTypeDao.getAllUserRoles();
//		List<String> roleDesc= roleList.stream().map(UserRoleType::getDescription).distinct().collect(Collectors.toList());
//		return roleDesc;
    }
	


	@RequestMapping(value = "/deActivateUser" , method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String deActivateUser (Model model,HttpSession session , String  lanId ,char activeStatus ) {

	    logger.debug("Starting deactivateUser()....");

	    return "";
//		if ((UserLocuslink)session.getAttribute ("userLocuslink") == null) {
//			logger.debug(" ERROR ->: User is NOT AuthenticateduserLocuslink is not in the session.");
//			return "timeout";
//		} 
//		
//		UserLocuslink userLocuslink = (UserLocuslink)session.getAttribute ("userLocuslink");
//		UserLocuslink deactivate= userLocuslinkService.getUserByLanId(lanId) ;
//	    deactivate.setActiveFlag('N');
//	    deactivate.setLastModifiedUserId(userLocuslink.getLoginId());
//	    boolean markAsActive;
//	    if(activeStatus=='Y')
//	    {
//	    	markAsActive=false;
//	    	
//	    }
//	    else
//	    {
//	    	markAsActive=true;
//	    }
//	    userLocuslinkService.updateUsrPrflActiveFlag(deactivate, userLocuslink, markAsActive);
//	
//	    String act= String.valueOf(userLocuslinkService.getById(deactivate.getUserLocuslinkId()).getActiveFlag());
//	    logger.debug("updated active status-----"+act);
//	    return act;
	}
	
	
	
	@RequestMapping(value = "/deleteUserProfile" , method = {RequestMethod.POST,RequestMethod.GET})
    public  @ResponseBody String deleteUserProfile (Model model,HttpSession session , String  lanId ) {

	    logger.debug("Starting deleteUserProfile()....");

		if ((UserLocuslink)session.getAttribute ("userLocuslink") == null) {
			logger.debug(" ERROR ->: User is NOT AuthenticateduserLocuslink is not in the session.");
			return "timeout";
		} 

		deleteUser(lanId);
	    return "fragments/editUser";
	}
	
	
	
	@RequestMapping(value = "/updateUserProfileRow", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody String updateUserProfileRow(@RequestBody GenericMessageRequest request, HttpSession session)  {
		 
		logger.debug(" Starting updateUserProfileRow()  data ->: " + request.getData());
	        
		return "";
//        String action = request.getFieldAsString("action").trim();
//        UserLocuslink userLocuslink = (UserLocuslink)session.getAttribute ("userLocuslink");
//        logger.debug("  action ->: " +  action);
//        GenericMessageResponse response = new GenericMessageResponse("1.0", "Trace", "updateUserProfileRow");
//        HashMap<String, Map<String,String>> rowDataMap = (HashMap<String, Map<String, String>>) request.getFieldAsObject("rowData");
//       
//      
//        HashMap<String,String> columnMap = (HashMap<String, String>) rowDataMap.get(rowDataMap.keySet().toArray()[0]);
//        logger.debug("  columnMap ->: " +  columnMap);
//		String columnName = columnMap.keySet().iterator().next();
//		logger.debug("  columnName ->: " +  columnMap.get("lanId"));
//      
//		String columnValue = columnMap.get(columnMap.keySet().toArray()[0]);
//		logger.debug("  columnValue ->: " +  String.valueOf(columnMap.get("userMembershipId")));
//		DashboardFormDTO dashboardFormDTO = new DashboardFormDTO();
//		String userMembershipId = rowDataMap.keySet().iterator().next();
//		dashboardFormDTO.setUserMembershipId(Integer.valueOf(userMembershipId));
//			
//			
//		if(action == "remove" || action.equals("remove"))   {
//			deleteUser(columnMap.get("lanId"));
//	    } else if (action == "edit" || action.equals("edit")) {
//			updateUser(Integer.valueOf(userMembershipId),dashboardFormDTO,columnMap,userLocuslink);
//		} else {
//			logger.error("Error ->: Bad Action Code got passed in here ->:" + action);
//		}
//		
//		ObjectMapper mapper = new ObjectMapper();
//	      
//		// Build response
//		String jsonString = "";        
//		StringBuffer wrkBuff = new StringBuffer( "{\"data\": [ ");	
//		  
//		try {
//			jsonString = mapper.writeValueAsString(dashboardFormDTO);
//			logger.debug(jsonString);	  
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}  
//	    wrkBuff.append(jsonString);
//	   
//	    wrkBuff.append(" ]} ");		  		  
//	    logger.debug("wrkBuff ->: " + wrkBuff.toString());
//
//	    return wrkBuff.toString();
	}
	
	
	public void deleteUser(String lanId) {
//		 UserLocuslink deletUser= userLocuslinkService.getUserByLanId(lanId) ;
//		 userLocuslinkService.deleteUserProfile(deletUser);
	}
	
	
	public void updateUser(int userMembershipId ,DashboardFormDTO dashboardFormDTO, HashMap<String, String> columnMap, UserLocuslink userLocuslink) {
		
//		 UserMembership existingUsermemberShip=userMembershipDao.getByUsermemberShip(UserFormDTO.getUserMembershipId());
//		 TraceUtils.buildCommonDetails(existingUsermemberShip, userLocuslink.getCreateUserId());
//		
//		 UserLocuslink existingtUser= userLocuslinkService.getById(existingUsermemberShip.getUserLocuslink().getUserLocuslinkId());
//		 TraceUtils.buildCommonDetails(existingtUser, userLocuslink.getCreateUserId());
//		if (columnMap.get("role")!=null) {
//			existingUsermemberShip.setUserRoleType(userRoleTypeDao.getByDesc(columnMap.get("role")));
//		}
//		
//		if (columnMap.get("contractor").equals("Not Assigned")) {
//			existingtUser.setExternalContractorCompanyName(columnMap.get("contractor"));
//			existingUsermemberShip.setContractor(null);
//		} else {
//			existingtUser.setExternalContractorCompanyName(columnMap.get("contractor"));
//			List<Contractors> contractorList = contractorsService.getContractorObjectListbyContractorName(columnMap.get("contractor"));
//			existingUsermemberShip.setContractor(contractorList.get(0));
//		}
//
//		 userLocuslinkService.saveOrUpdateuser(existingtUser, existingUsermemberShip);
//		 UserMembership updatedUsermemberShip= userMembershipDao.getByUsermemberShip(existingUsermemberShip.getUserMembershipId());
//		 getUpdatedUser (updatedUsermemberShip,UserFormDTO);
		 
		    
	}
	
	
	public  void getUpdatedUser (UserMembership UserMembership ,DashboardFormDTO dashboardFormDTO) 	{
		
//		UserLocuslink userLocuslink = UserMembership.getUserLocuslink();
//		//UserFormDTO.setCompany(userLocuslink.getExternalContractorCompanyName());
//		UserFormDTO.setLanId(userLocuslink.getLoginId());
//		UserFormDTO.setDiscipline(userLocuslink.getDiscipline().getDescription());
//		UserFormDTO.setFullName(userLocuslink.getFirstName().concat(" ").concat(userLocuslink.getLastName()));
//		UserFormDTO.setRegion(userLocuslink.getRegion().getDescription());
//		UserFormDTO.setActiveStatus(userLocuslink.getActiveFlag());
//		UserFormDTO.setState(userLocuslink.getRegion().getState());
//		UserFormDTO.setUserMembershipId(UserMembership.getUserMembershipId());
//		UserFormDTO.setUsertraceId(userLocuslink.getUserLocuslinkId());
//		if(UserMembership.getContractor() == null) {
//			UserFormDTO.setContractor("Not Assigned");
//		} else {
//			UserFormDTO.setContractor(UserMembership.getContractor().getContractorName());
//		}
//		UserFormDTO.setRole(UserMembership.getUserRoleType().getDescription());
	}
	
	
	
	
	public void getUserData(DashboardFormDTO dashboardFormDTO , HashMap<String,String> columnMap) 	{
		
		
//		 if (columnMap.get("discipline")!=null)
//		{
//			UserFormDTO.setDiscipline(columnMap.get("discipline"));
//		}
//		else if (columnMap.get("region")!=null)
//		{
//			UserFormDTO.setRegion(columnMap.get("region"));
//		}
//		else if (columnMap.get("state")!=null)
//		{
//			UserFormDTO.setState(columnMap.get("state"));
//		}
//		else if (columnMap.get("lanId")!=null)
//		{
//			UserFormDTO.setLanId(columnMap.get("lanId"));
//		}
//		else if (columnMap.get("usertraceId")!=null)
//		{
//			UserFormDTO.setUsertraceId(Integer.parseInt(String.valueOf(columnMap.get("usertraceId"))));
//		}
//		else if (columnMap.get("fullName")!=null)
//		{
//			UserFormDTO.setFullName(columnMap.get("fullName"));
//		}
		    
	}
	
	
 
}