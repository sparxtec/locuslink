/**
 *  Copyright 2018 Duke Energy Corporation
 *  No part of this computer program may be reproduced, transmitted,
 *  transcribed, stored in a retrieval system, or translated into any
 *  language in any form by any means without the prior written consent
 *  of Duke Energy Corporation.
 */
package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_locuslink")
public class UserLocuslink extends Common {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_locuslink_pkid", unique = true, nullable = false)
	private int userLocusLinkPkId;
	
	@Column(name = "user_type_code", nullable = false)
	private String userTypeCode;
	
	@Column(name = "login_name", nullable = false)
	private String loginName;
		
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name_bus_name")
	private String lastNameBusName;
	
	@Column(name = "Active_Flag", nullable = false)
	private String activeFlag;
	
	
	@Column(name = "email_address")
	private String emailAddress;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
}
