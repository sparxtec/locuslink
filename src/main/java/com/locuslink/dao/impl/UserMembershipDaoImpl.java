/**
 *  Copyright 2013 Duke Energy Corporation
 *  No part of this computer program may be reproduced, transmitted,
 *  transcribed, stored in a retrieval system, or translated into any
 *  language in any form by any means without the prior written consent
 *  of Duke Energy Corporation.
 */

package com.locuslink.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.UserMembershipDao;
import com.locuslink.model.UserLocuslink;
import com.locuslink.model.UserMembership;

@Transactional
@Repository(UserMembershipDao.BEAN_NAME)
public class UserMembershipDaoImpl extends DaoSupport implements UserMembershipDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
    /**
     * Default constructor. Provides ability to specify the session factory in the Application Context file
     * as a reference.
     */
    public UserMembershipDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public UserMembership getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(UserMembership.class, pkid);				
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UserMembership>  getAll() {			
		
		DetachedCriteria criteria= DetachedCriteria.forClass(UserLocuslink.class, "userMembership");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 

		return (List<UserMembership>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	@Override
	public void save(UserMembership userMembership) {
		this.sessionFactory.getCurrentSession().save(userMembership);
	}
	
	@Override
	public void saveOrUpdate(UserMembership userMembership) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(userMembership);
	}	
	
	@Override
	public void delete(UserMembership userMembership) {
		this.sessionFactory.getCurrentSession().delete(userMembership);
	}

	

}
