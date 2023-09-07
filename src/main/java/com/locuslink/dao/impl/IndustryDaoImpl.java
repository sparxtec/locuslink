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
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.IndustryDao;
import com.locuslink.dto.UidDTO;
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
	
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public  List<UidDTO>  getUidDTO (int iPkId, int siPkId, int vPkId, int ptPkId, int pstPkId) 	{	
		
		String whereClause = uidSqlBuilder( iPkId,  siPkId,  vPkId,  ptPkId,  pstPkId);

		
		String sql = "Select   "				 
			 	+ " i.industry_pkid as " + '"' + "industryPkId" + '"' 			
			 	+ ", i.uid as " + '"' + "industryUid" + '"'
			 	+ ", i.industry_code as " + '"' + "industryCode" + '"'
			 	+ ", i.industry_desc as " + '"' + "industryDesc" + '"'
			 	
			 	+ ", si.sub_industry_pkid as " + '"' + "subIndustryPkId" + '"' 			
			 	+ ", si.uid as " + '"' + "subIndustryUid" + '"'
			 	+ ", si.sub_industry_code as " + '"' + "subIndustryCode" + '"'
			 	+ ", si.sub_industry_desc as " + '"' + "subIndustryDesc" + '"'
			 	
				
				+ " from Industry i "					
				+ " left  join sub_industry si       on i.industry_pkid = si.industry_pkid "	
				+ " left  join product_type pt       on si.sub_industry_pkid = pt.sub_industry_pkid  "	
				+ " left  join product_sub_type pst  on pt.product_type_pkid = pst.product_type_pkid "	
				
				/* + " where i.industry_pkId between :industryPkId_lo and :industryPkId_hi " */
				+ whereClause					
				+ " order by i.uid asc, si.uid asc, pt.uid asc, pst.uid asc";
		

		@SuppressWarnings("rawtypes")
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)	;		
		//	.setParameter("industryPkId", iPkId);	
			 query.setResultTransformer(new AliasToBeanResultTransformer(UidDTO.class));
			
		return (List<UidDTO>) ( query).list();		
	}
	
	
	private  String  uidSqlBuilder (int iPkId, int siPkId, int vPkId, int ptPkId, int pstPkId) 	{	
		String whereClause = "";
			
		// Industry
		if (iPkId > 0) {
			whereClause = " where i.industry_pkId = " + iPkId ;
		} else {
			whereClause = " where i.industry_pkId between 0 and 99999 " ;
		}
		
		// Sub Industry
		//    and (si.sub_industry_pkid between 0 and 99999 or si.sub_industry_pkid is null)
		if (siPkId > 0) {
			whereClause = whereClause + " and si.sub_industry_pkid =  " + siPkId;
		} else {
			whereClause = whereClause + " and si.sub_industry_pkid between 0  and 99999" ;	
		}
		
	    return whereClause;
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
