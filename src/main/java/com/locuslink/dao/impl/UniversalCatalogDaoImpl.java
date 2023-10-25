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

import com.locuslink.dao.UniversalCatalogDao;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.dto.UniversalCatalogDTO;
import com.locuslink.model.UniversalCatalog;

/**
 * This is the DAO implementation class for UniversalCatalog.
 *  
 * @author C.Sparks
 * @since 1.0.0 - Feb 01, 2023 - Initial version
 */
@Transactional
@Repository(UniversalCatalogDao.BEAN_NAME)
public class UniversalCatalogDaoImpl extends DaoSupport implements UniversalCatalogDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
    public UniversalCatalogDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public UniversalCatalog getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(UniversalCatalog.class, pkid);				
	}
	

	
	@Override
	public UniversalCatalogDTO getDtoById(int pkid) {	
			
		// TODO
		 return null;		
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UniversalCatalogDTO>  getAllDTO() 	{	
			
		
		 List <UniversalCatalogDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.UniversalCatalogDTO(
			
				 uc.ucatPkId, 
		 		 uc.universalCatalogId, 
		 		 uc.availFlag, 
		   
		         cdugt.cdugtPkId, 		    
		 		 cdugt.industryPkId, 
		 		 cdugt.subIndustryPkId, 
           	  	 cdugt.productTypePkId, 
           	  	 cdugt.productSubTypePkId, 
            	 cdugt.uidTemplateLen,
            	 cdugt.uidTemplate,
                    
				 i.industryDesc,
				 si.subIndustryDesc,
				 pt.productTypeDesc,
				 pst.productSubTypeDesc

			)
			from UniversalCatalog uc			
			left outer join UniversalCatalogSelectedAttributes ucsa on uc.ucatPkId = ucsa.ucatPkId			
			left outer join CatalogDefUidGenTemplate cdugt on ucsa.cdugtPkId = cdugt.cdugtPkId										
		    left outer join Industry i    					on	cdugt.industryPkId = i.industryPkId
		    left outer join SubIndustry si	 				on	cdugt.subIndustryPkId = si.subIndustryPkId	        
		    left outer join ProductType pt 				on 	cdugt.productTypePkId   = pt.productTypePkId    
		 	left outer join ProductSubType pst 			on 	cdugt.productSubTypePkId  = pst.productSubTypePkId		 												
			""", 
			UniversalCatalogDTO.class).getResultList();	
		 
		 return dtoList;
	}
	
	
//	 cdal.cdalPkId,
//	 cdal.uidAttrSeq,
//	 cdal.uidAttrListName,
//	 cdal.uidAttributesJson 
//	left outer join CatalogDefAttributeList cdal 	on cdugt.cdugtPkId = cdal.cdugtPkId

	

	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UniversalCatalog>  getAll() {					
		DetachedCriteria criteria= DetachedCriteria.forClass(UniversalCatalog.class, "universalCatalog");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
	//	criteria.addOrder(Order.asc ("customerPkId"));
		return (List<UniversalCatalog>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	

	@Override
	public void saveOrUpdate(UniversalCatalog universalCatalog) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(universalCatalog);
	}	
	
	@Override
	public void delete(UniversalCatalog universalCatalog) {
		this.sessionFactory.getCurrentSession().delete(universalCatalog);
	}
	
}
