/**
 *  Copyright 2013 Duke Energy Corporation
 *  No part of this computer program may be reproduced, transmitted,
 *  transcribed, stored in a retrieval system, or translated into any
 *  language in any form by any means without the prior written consent
 *  of Duke Energy Corporation.
 */

package com.locuslink.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.UserRoleTypeDao;
import com.locuslink.model.UserRoleType;


@Transactional
@Repository(UserRoleTypeDao.BEAN_NAME)
public class UserRoleTypeDaoImpl extends DaoSupport implements UserRoleTypeDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public UserRoleTypeDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public UserRoleType getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(UserRoleType.class, pkid);				
	}
	

	public  UserRoleType  getByCode (String userRoleCode)	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(UserRoleType.class, "userRoleType");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.add(Restrictions.eq("userRoleCode", userRoleCode).ignoreCase());
		return ( UserRoleType) ( criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).uniqueResult());		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UserRoleType>  getAll () 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(UserRoleType.class, "userRoleType");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.addOrder(Order.asc ("userRoleCode"));
		return (List<UserRoleType>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	
	

	
	
		

	@Override
	public void saveOrUpdate(UserRoleType userRoleType) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(userRoleType);
	}	
	
	@Override
	public void save (UserRoleType userRoleType) {
		this.sessionFactory.getCurrentSession().save(userRoleType);
	}	
	
	
	@Override
	public void delete(UserRoleType userRoleType) {
		this.sessionFactory.getCurrentSession().delete(userRoleType);
	}
	
}
