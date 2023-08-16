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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.ProductAttributeDao;
import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.model.ProductAttachment;
import com.locuslink.model.ProductAttribute;
import com.locuslink.model.UniversalCatalog;

/**
 * This is the DAO implementation class for ProductAttribute
 *  
 * @author C.Sparks
 * @since 1.0.0 - Aug 01, 2023 - Initial version
 */
@Transactional
@Repository(ProductAttributeDao.BEAN_NAME)
public class ProductAttributeDaoImpl extends DaoSupport implements ProductAttributeDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public ProductAttributeDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public ProductAttribute getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(ProductAttribute.class, pkid);				
	}
	

	@Override
	public ProductAttribute getByUniversalCatalogId(int ucatPkId) {
		String sql = "select pa from ProductAttribute pa where pa.ucatPkId = :ucatPkId  ";		
		return (ProductAttribute) this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("ucatPkId", ucatPkId).uniqueResult();
	}
	
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public  List<ProductAttribute>  getByUniversalCatalogId(int id) {					
//		DetachedCriteria criteria= DetachedCriteria.forClass(ProductAttribute.class, "productAttribute");  		
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
//	//	criteria.addOrder(Order.asc ("customerPkId"));
//		return (List<ProductAttribute>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
//	}
	
	
	
	
	

	@Override
	public void saveOrUpdate(ProductAttribute productAttribute) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(productAttribute);
	}	
	
	@Override
	public void save (ProductAttribute productAttribute) {
		this.sessionFactory.getCurrentSession().save(productAttribute);
	}	
	
	
	@Override
	public void delete(ProductAttribute productAttribute) {
		this.sessionFactory.getCurrentSession().delete(productAttribute);
	}
	
}
