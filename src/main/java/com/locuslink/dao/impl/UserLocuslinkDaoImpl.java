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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.locuslink.dao.UserLocuslinkDao;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.dto.UserDTO;
import com.locuslink.model.UserLocuslink;


@Transactional
@Repository(UserLocuslinkDao.BEAN_NAME)
public class UserLocuslinkDaoImpl extends DaoSupport implements UserLocuslinkDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
    public UserLocuslinkDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	
	
	@Override
	public UserLocuslink getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(UserLocuslink.class, pkid);				
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public UserLocuslink getUserByLanId(String loginName) {
		UserLocuslink usrData = null;
		DetachedCriteria criteria= DetachedCriteria.forClass(UserLocuslink.class, "userLocuslink");
		criteria.add(Restrictions.eq("userLocuslink.loginName",loginName));
		List<UserLocuslink> usrs = (List<UserLocuslink>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();
		if (!CollectionUtils.isEmpty(usrs)) {
			usrData = usrs.get(0);
		}
		return usrData;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UserDTO>  getAllDTO() 	{	

		 List <UserDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.UserDTO(	
					
				ul.userLocusLinkPkId,
				ul.userTypeCode,
		 		ul.loginName,	
		 		ul.firstName,				 			 			 		
		 		ul.lastNameBusName,		 							 										
				ul.activeFlag
				
														
			)
			from UserLocuslink ul
																																																				
			""", UserDTO.class)
		.getResultList();	
		 
		 return dtoList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UserLocuslink>  getAll() {					
		DetachedCriteria criteria= DetachedCriteria.forClass(UserLocuslink.class, "userLocuslink");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		return (List<UserLocuslink>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	
	
	@Override
	public void save(UserLocuslink userLocuslink) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(userLocuslink);
	}	

	@Override
	public void saveOrUpdate(UserLocuslink userLocuslink) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(userLocuslink);
	}	
	
	@Override
	public void delete(UserLocuslink userLocuslink) {
		this.sessionFactory.getCurrentSession().delete(userLocuslink);
	}

	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<UserLocuslink> getAllActiveUsers() {
//		// TODO Auto-generated method stub
//		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(UserLocuslink.class, "usrTrace");
//	
//		      criteria.add(Restrictions.ne("usrTrace.firstName", TraceConstants.defaultTemplateUserName));
//		      criteria.add(Restrictions.ne("usrTrace.activeFlag", TraceConstants.IND_NO.charAt(0)));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
//
//		return (List<UserLocuslink>) criteria.list();	
//	
//	}
//	
//	@Override
//	public UserLocuslink getActiveUsersByFirstandLastName(String firstName, String lastName) {
//		String sql = "Select user from UserLocuslink user where user.firstName = :firstName and user.lastName = :lastName and user.activeFlag = 'Y' ";		
//		return (UserLocuslink)this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("firstName", firstName).setParameter("lastName", lastName).uniqueResult();
//	}
}
