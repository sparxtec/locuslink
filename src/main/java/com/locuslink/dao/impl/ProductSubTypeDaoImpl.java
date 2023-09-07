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

import com.locuslink.dao.ProductSubTypeDao;
import com.locuslink.model.ProductSubType;

/**
 * This is the DAO implementation class for ProductSubType
 *  
 * @author C.Sparks
 * @since 1.0.0 - Aug 31, 2023 - Initial version
 */
@Transactional
@Repository(ProductSubTypeDao.BEAN_NAME)
public class ProductSubTypeDaoImpl extends DaoSupport implements ProductSubTypeDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public ProductSubTypeDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public ProductSubType getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(ProductSubType.class, pkid);				
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<ProductSubType>  getAll () 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(ProductSubType.class, "productSubType");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.addOrder(Order.asc ("productSubTypeCode"));
		return (List<ProductSubType>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	
	
	@SuppressWarnings("unchecked")
	public  List<ProductSubType>  getByProductType (int productTypePkId) 	{	
		DetachedCriteria criteria= DetachedCriteria.forClass(ProductSubType.class, "productSubType");  			
		criteria.add(Restrictions.eq("productTypePkId", productTypePkId));
		criteria.addOrder(Order.asc ("productSubTypeCode"));		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		return (List<ProductSubType>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();		
	}
	
	
	
		

	@Override
	public void saveOrUpdate(ProductSubType productSubType) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(productSubType);
	}	
	
	@Override
	public void save (ProductSubType productSubType) {
		this.sessionFactory.getCurrentSession().save(productSubType);
	}	
	
	
	@Override
	public void delete(ProductSubType productSubType) {
		this.sessionFactory.getCurrentSession().delete(productSubType);
	}
	
}
