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

import com.locuslink.dao.SubIndustryDao;
import com.locuslink.model.SubIndustry;

/**
 * This is the DAO implementation class for Sub Industry
 *  
 * @author C.Sparks
 * @since 1.0.0 - Aug 31, 2023 - Initial version
 */
@Transactional
@Repository(SubIndustryDao.BEAN_NAME)
public class SubIndustryDaoImpl extends DaoSupport implements SubIndustryDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public SubIndustryDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public SubIndustry getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(SubIndustry.class, pkid);				
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<SubIndustry>  getAll () 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(SubIndustry.class, "subIndustry");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.addOrder(Order.asc ("subIndustryCode"));
		return (List<SubIndustry>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	

	
	@SuppressWarnings("unchecked")
	public  List<SubIndustry>  getByIndustry (int industryPkId) 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(SubIndustry.class, "subIndustry");		
		criteria.add(Restrictions.eq("industryPkId", industryPkId));
		criteria.addOrder(Order.asc ("subIndustryCode"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		return (List<SubIndustry>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	

	@Override
	public void saveOrUpdate(SubIndustry subIndustry) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(subIndustry);
	}	
	
	@Override
	public void save (SubIndustry subIndustry) {
		this.sessionFactory.getCurrentSession().save(subIndustry);
	}	
	
	
	@Override
	public void delete(SubIndustry subIndustry) {
		this.sessionFactory.getCurrentSession().delete(subIndustry);
	}

}
