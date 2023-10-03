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
@Table(name = "user_membership")
public class UserMembership extends Common implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "user_membership_pkid", unique = true, nullable = false)
	private int userMembershipPkId;

    @Column(name = "user_locuslink_pkid", nullable = false)
	private int userLocusLinkPkId;
	
    @Column(name="user_role_type_pkId", unique=true, nullable = false)
	private int userRoleTypePkId;
	



}
