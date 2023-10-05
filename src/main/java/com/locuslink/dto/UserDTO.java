package com.locuslink.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDTO {

	public UserDTO() {
		
	};
	
	public UserDTO( 
			int userLocusLinkPkId,			  
	        String userTypeCode, 
	        String loginName, 
	        String firstName,    
	        String lastNameBusName, 
	        String activeFlag	) {

		this.userLocusLinkPkId = userLocusLinkPkId; 
		this.userTypeCode = userTypeCode; 
		this.loginName = loginName;				
		this.firstName = firstName;			
		this.lastNameBusName = lastNameBusName;	
		this.activeFlag = activeFlag;						
	}
	
	
	// User Table
	int userLocusLinkPkId;	
	String userTypeCode;	
	String loginName;
	String firstName;	
	String lastNameBusName;	
	String activeFlag;
	
	// Role Table
	
	// Membership
    
}
