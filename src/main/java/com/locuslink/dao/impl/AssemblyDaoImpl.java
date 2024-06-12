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

import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.model.Assembly;
import com.locuslink.model.UniqueAsset;

/**
 * This is the DAO implementation class for Assemblies..
 *  
 * @author C.Sparks
 * @since 1.0.0 - May 01, 2024 - Initial version
 */
@Transactional
@Repository(AssemblyDao.BEAN_NAME)
public class AssemblyDaoImpl extends DaoSupport implements AssemblyDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public AssemblyDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public Assembly getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(Assembly.class, pkid);				
	}
	
	@Override
	public AssemblyDTO getDtoById(int pkid) {	

		 //  1-11-2024
		 AssemblyDTO dto = entityManager.createQuery("""
			select new com.locuslink.dto.AssemblyDTO(
				
				a.assemblyPkid,
				a.jobNumber,
		 		a.jobDescription,
		 		a.location,
				a.stationNumber,
		 		a.traceNumber,
		 		
		 	    a.stationName,
		 		a.designSpecNumber, 
		 		a.drawingNumber, 
		 		a.fabricatorCompanyName, 
		 		a.customerSpecNumber		 				
			)
			from Assembly a
							
 																		
			where a.assemblyPkid = :pkid
									
			""", AssemblyDTO.class)
				 .setParameter("pkid", pkid)
				 .getSingleResult();				 
		 return dto;		
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<AssemblyDTO>  getAllDTO() 	{	

		 List <AssemblyDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.AssemblyDTO(				
				a.assemblyPkid,
				a.jobNumber,
		 		a.jobDescription,
		 		a.location,
				a.stationNumber,
		 		a.traceNumber,
		 		
		 	    a.stationName,
		 		a.designSpecNumber, 
		 		a.drawingNumber, 
		 		a.fabricatorCompanyName, 
		 		a.customerSpecNumber			 													
			)
			from Assembly a               
			""", AssemblyDTO.class)
		.getResultList();	
		 
		 return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<Assembly>  getAll() {					
		DetachedCriteria criteria= DetachedCriteria.forClass(UniqueAsset.class, "assembly");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
	//	criteria.addOrder(Order.asc ("customerPkId"));
		return (List<Assembly>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	@Override
	public void saveOrUpdate(Assembly assembly) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(assembly);
	}	
	
	@Override
	public void save (Assembly assembly) {
		this.sessionFactory.getCurrentSession().save(assembly);
	}	
	
	
	@Override
	public void delete(Assembly assembly) {
		this.sessionFactory.getCurrentSession().delete(assembly);
	}
	
}
