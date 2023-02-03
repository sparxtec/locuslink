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

import com.locuslink.dao.CustomerDao;
import com.locuslink.model.Customer;

/**
 * This is the DAO implementation class for Customer.
 *  
 * @author C.Sparks
 * @since 1.0.0 - Feb 01, 2023 - Initial version
 */
@Transactional
@Repository(CustomerDao.BEAN_NAME)
public class CustomerDaoImpl extends DaoSupport implements CustomerDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
    public CustomerDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public Customer getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(Customer.class, pkid);				
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<Customer>  getAll() {					
		DetachedCriteria criteria= DetachedCriteria.forClass(Customer.class, "customer");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		return (List<Customer>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	@Override
	public void saveOrUpdate(Customer customer) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(customer);
	}	
	
	@Override
	public void delete(Customer customer) {
		this.sessionFactory.getCurrentSession().delete(customer);
	}
	
}
