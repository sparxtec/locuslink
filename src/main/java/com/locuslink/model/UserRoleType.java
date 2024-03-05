package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user_role_type")
public class UserRoleType extends Common {

	public UserRoleType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="user_role_type_pkid", unique=true, nullable = false)
    private int userRoleTypePkId;
      
    @Column(name="user_role_code", nullable = false)
    private String userRoleCode;

    @Column(name = "user_role_desc", nullable = false)
	private String userRoleDesc; 
	
	


}