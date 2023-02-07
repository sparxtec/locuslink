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

import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.model.UniqueAsset;

/**
 * This is the DAO implementation class for UniversalCatalog.
 *  
 * @author C.Sparks
 * @since 1.0.0 - Feb 01, 2023 - Initial version
 */
@Transactional
@Repository(UniqueAssetDao.BEAN_NAME)
public class UniqueAssetDaoImpl extends DaoSupport implements UniqueAssetDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
    public UniqueAssetDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public UniqueAsset getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(UniqueAsset.class, pkid);				
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UniqueAsset>  getAll() {					
		DetachedCriteria criteria= DetachedCriteria.forClass(UniqueAsset.class, "uniqueAsset");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
	//	criteria.addOrder(Order.asc ("customerPkId"));
		return (List<UniqueAsset>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	@Override
	public void saveOrUpdate(UniqueAsset uniqueAsset) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(uniqueAsset);
	}	
	
	@Override
	public void delete(UniqueAsset uniqueAsset) {
		this.sessionFactory.getCurrentSession().delete(uniqueAsset);
	}
	
}
