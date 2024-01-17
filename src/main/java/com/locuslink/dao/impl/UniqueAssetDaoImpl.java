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

import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dto.UniqueAssetDTO;
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
	
	@PersistenceContext
	EntityManager entityManager;
	
	
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
	
	@Override
	public UniqueAssetDTO getDtoById(int pkid) {	

		 //  1-11-2024
		 UniqueAssetDTO dto = entityManager.createQuery("""
			select new com.locuslink.dto.UniqueAssetDTO(
				
				ua.uniqueAssetPkId,
				ua.uniqueAssetId,
		 		ua.traceCode,
		 				
				tt.traceTypeCode,							 			 			 	
		 		mfg.name,
		 		cus.customerName,
		 				 							 											 							 										
				uc.ucatPkId, 
		 		uc.universalCatalogId, 
		 		uc.availFlag,
		 		
		 		pa.attributesJson,	 
		   
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
				pst.productSubTypeDesc,
				pt.productTypeCode
									
			)
			from UniqueAsset ua	
				left outer join Manufacturer mfg on mfg.manufacturerPkId = ua.manufacturerPkId		
		        left outer join TraceType tt on tt.traceTypePkId = ua.traceTypePkId	
		        left outer join Customer cus on cus.customerPkId = ua.customerPkId		        
				left outer join UniversalCatalog uc on uc.ucatPkId = ua.ucatPkId	
				left outer join ProductAttribute pa on pa.ucatPkId = uc.ucatPkId			
				left outer join UniversalCatalogSelectedAttributes ucsa on ucsa.ucatPkId = ua.ucatPkId		
				
		 		left outer join CatalogDefUidGenTemplate cdugt on ucsa.cdugtPkId = cdugt.cdugtPkId										
		 		left outer join Industry i    			on	cdugt.industryPkId = i.industryPkId
		 		left outer join SubIndustry si	 		on	cdugt.subIndustryPkId = si.subIndustryPkId	        
		 		left outer join ProductType pt 			on 	cdugt.productTypePkId   = pt.productTypePkId    
		 	    left outer join ProductSubType pst 		on 	cdugt.productSubTypePkId  = pst.productSubTypePkId		 												
							
			where ua.uniqueAssetPkId = :pkid
									
			""", UniqueAssetDTO.class)
				 .setParameter("pkid", pkid)
				 .getSingleResult();				 
		 return dto;		
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<UniqueAssetDTO>  getAllDTO() 	{	

		// 1-11-2024
		 List <UniqueAssetDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.UniqueAssetDTO(
				ua.uniqueAssetPkId,
				ua.uniqueAssetId,
		 		ua.traceCode,
		 				
				tt.traceTypeCode,							 			 			 	
		 		mfg.name,
		 		cus.customerName,
		 				 							 											 							 										
				uc.ucatPkId, 
		 		uc.universalCatalogId, 
		 		uc.availFlag,
		 		
		 		pa.attributesJson,	 
		   
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
				pst.productSubTypeDesc,
				pt.productTypeCode														
			)
			from UniqueAsset ua
				left outer join Manufacturer mfg on mfg.manufacturerPkId = ua.manufacturerPkId		
		        left outer join TraceType tt on tt.traceTypePkId = ua.traceTypePkId	
		        left outer join Customer cus on cus.customerPkId = ua.customerPkId		        
				left outer join UniversalCatalog uc on uc.ucatPkId = ua.ucatPkId	
				left outer join ProductAttribute pa on pa.ucatPkId = uc.ucatPkId			
				left outer join UniversalCatalogSelectedAttributes ucsa on ucsa.ucatPkId = ua.ucatPkId		
				
		 		left outer join CatalogDefUidGenTemplate cdugt on ucsa.cdugtPkId = cdugt.cdugtPkId										
		 		left outer join Industry i    			on	cdugt.industryPkId = i.industryPkId
		 		left outer join SubIndustry si	 		on	cdugt.subIndustryPkId = si.subIndustryPkId	        
		 		left outer join ProductType pt 			on 	cdugt.productTypePkId   = pt.productTypePkId    
		 	    left outer join ProductSubType pst 		on 	cdugt.productSubTypePkId  = pst.productSubTypePkId		 												
																									
			""", UniqueAssetDTO.class)
		.getResultList();	
		 
		 return dtoList;
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
	public void save (UniqueAsset uniqueAsset) {
		this.sessionFactory.getCurrentSession().save(uniqueAsset);
	}	
	
	
	@Override
	public void delete(UniqueAsset uniqueAsset) {
		this.sessionFactory.getCurrentSession().delete(uniqueAsset);
	}
	
}
