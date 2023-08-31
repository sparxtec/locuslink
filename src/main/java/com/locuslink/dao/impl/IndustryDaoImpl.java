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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.IndustryDao;
import com.locuslink.model.Industry;

/**
 * This is the DAO implementation class for Industry
 *  
 * @author C.Sparks
 * @since 1.0.0 - Aug 31, 2023 - Initial version
 */
@Transactional
@Repository(IndustryDao.BEAN_NAME)
public class IndustryDaoImpl extends DaoSupport implements IndustryDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public IndustryDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public Industry getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(Industry.class, pkid);				
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<Industry>  getAll () 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(Industry.class, "industry");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.addOrder(Order.asc ("industryCode"));
		return (List<Industry>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	
	
		

	@Override
	public void saveOrUpdate(Industry industry) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(industry);
	}	
	
	@Override
	public void save (Industry industry) {
		this.sessionFactory.getCurrentSession().save(industry);
	}	
	
	
	@Override
	public void delete(Industry industry) {
		this.sessionFactory.getCurrentSession().delete(industry);
	}
	
}
