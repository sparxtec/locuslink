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
import com.locuslink.model.UserLocuslink;

/**
 * This is the DAO implementation class for UserLocuslink.
 *  
 * @author C.Sparks
 * @since 1.0.0 - December 31, 2022 - Initial version
 */
@Transactional
@Repository(UserLocuslinkDao.BEAN_NAME)
public class UserLocuslinkDaoImpl extends DaoSupport implements UserLocuslinkDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
    /**
     * Default constructor. Provides ability to specify the session factory in the Application Context file
     * as a reference.
     */
    public UserLocuslinkDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public UserLocuslink getById(int UserLocuslinkId) {	
		return this.sessionFactory.getCurrentSession().get(UserLocuslink.class, UserLocuslinkId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public UserLocuslink getUserByLanId(String lanId) {
		UserLocuslink usrData = null;
		DetachedCriteria criteria= DetachedCriteria.forClass(UserLocuslink.class, "usrTrace");
		criteria.add(Restrictions.eq("usrTrace.loginId",lanId));
		List<UserLocuslink> usrs = (List<UserLocuslink>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();
		if (!CollectionUtils.isEmpty(usrs)) {
			usrData = usrs.get(0);
		}
		return usrData;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UserLocuslink>  getAll() {			
		
		DetachedCriteria criteria= DetachedCriteria.forClass(UserLocuslink.class, "usrTrace");  		
		//criteria.add(Restrictions.ne("usrTrace.firstName", TraceConstants.defaultTemplateUserName));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 

		return (List<UserLocuslink>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	


	@Override
	public void saveOrUpdate(UserLocuslink newProfile) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().saveOrUpdate(newProfile);
	}	
	
	@Override
	public void delete(UserLocuslink UserLocuslink) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession().delete(UserLocuslink);
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
