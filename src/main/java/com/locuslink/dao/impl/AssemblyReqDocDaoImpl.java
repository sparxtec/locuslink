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

import com.locuslink.dao.AssemblyReqDocDao;
import com.locuslink.model.AssemblyReqDoc;

/**
 * This is the DAO implementation class for
 *  
 * @author C.Sparks
 * @since 1.0.0 - May 01, 2024 - Initial version
 */
@Transactional
@Repository(AssemblyReqDocDao.BEAN_NAME)
public class AssemblyReqDocDaoImpl extends DaoSupport implements AssemblyReqDocDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public AssemblyReqDocDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public AssemblyReqDoc getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(AssemblyReqDoc.class, pkid);				
	}
	
	


//	@SuppressWarnings("unchecked")
//	@Override
//	public  List<AssemblyReqDoc>  getAllById( int assemblyPkid ) {					
//		DetachedCriteria criteria= DetachedCriteria.forClass(AssemblyReqDoc.class, "assemblyReqDoc");  		
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
//		criteria.add(Restrictions.eq("assemblyPkid", assemblyPkid));
//		criteria.addOrder(Order.asc ("docTypePkid"));
//		criteria.addOrder(Order.asc ("docDescription"));
//		return (List<AssemblyReqDoc>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<AssemblyReqDoc>  getAll( ) {					
		DetachedCriteria criteria= DetachedCriteria.forClass(AssemblyReqDoc.class, "assemblyReqDoc");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.addOrder(Order.asc ("docTypePkid"));
		criteria.addOrder(Order.asc ("docDescription"));
		return (List<AssemblyReqDoc>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}

	@Override
	public void saveOrUpdate(AssemblyReqDoc sssemblyReqDoc) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(sssemblyReqDoc);
	}	
	
	@Override
	public void save (AssemblyReqDoc sssemblyReqDoc) {
		this.sessionFactory.getCurrentSession().save(sssemblyReqDoc);
	}	
	
	
	@Override
	public void delete(AssemblyReqDoc sssemblyReqDoc) {
		this.sessionFactory.getCurrentSession().delete(sssemblyReqDoc);
	}
	
}
