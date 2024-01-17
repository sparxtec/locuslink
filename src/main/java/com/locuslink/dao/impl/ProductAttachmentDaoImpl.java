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

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.ProductAttachmentDao;
import com.locuslink.dao.UniqueAssetDao;
import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.ProductAttachment;

/**
 * This is the DAO implementation class for ProductAttachment.
 *  
 * @author C.Sparks
 * @since 1.0.0 - Feb 01, 2023 - Initial version
 */
@Transactional
@Repository(ProductAttachmentDao.BEAN_NAME)
public class ProductAttachmentDaoImpl extends DaoSupport implements ProductAttachmentDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public ProductAttachmentDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public ProductAttachment getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(ProductAttachment.class, pkid);				
	}
	
	@Override
	public ProductAttachmentDTO getDtoById(int pkid) {	
			
		 return null;		
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public  List<ProductAttachmentDTO>  getDtoByUniqueAssetId(int uniqueAssetPkId) 	{	
		
		 List <ProductAttachmentDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.ProductAttachmentDTO(			
			    attach.productAttachPkId,
				attach.filenameFullpath,
				doc.docTypeCode,
				doc.docTypeDesc,								
				ua.uniqueAssetPkId,
				ua.uniqueAssetId,
	 			ua.traceCode,	 						
		 		mfg.name,		 							 										
				pt.productTypeCode,				
				tt.traceTypeCode,
				pt.productTypeDesc
						
			)
			from ProductAttachment attach
			join DocumentType doc on doc.docTypePkId = attach.docTypePkId							
			join UniqueAsset ua on ua.uniqueAssetPkId = attach.uniqueAssetPkId																																							
			join Manufacturer mfg on mfg.manufacturerPkId = ua.manufacturerPkId		
	        join TraceType tt on tt.traceTypePkId = ua.traceTypePkId	
	        
			left outer join UniversalCatalog uc on uc.ucatPkId = ua.ucatPkId	
			left outer join ProductAttribute pa on pa.ucatPkId = uc.ucatPkId			
			left outer join UniversalCatalogSelectedAttributes ucsa on ucsa.ucatPkId = ua.ucatPkId		
				
		 	left outer join CatalogDefUidGenTemplate cdugt on ucsa.cdugtPkId = cdugt.cdugtPkId										
		 	left outer join Industry i    			on	cdugt.industryPkId = i.industryPkId
		 	left outer join SubIndustry si	 		on	cdugt.subIndustryPkId = si.subIndustryPkId	        
		 	left outer join ProductType pt 			on 	cdugt.productTypePkId   = pt.productTypePkId    
		 	left outer join ProductSubType pst 		on 	cdugt.productSubTypePkId  = pst.productSubTypePkId		 												
					
				
			
			where attach.uniqueAssetPkId = :uniqueAssetPkId																
					
			""", ProductAttachmentDTO.class)
				 .setParameter("uniqueAssetPkId", uniqueAssetPkId)
		.getResultList();	
		 
		 return dtoList;
	}
	
	
	
	
	

	@Override
	public void saveOrUpdate(ProductAttachment productAttachment) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(productAttachment);
	}	
	
	@Override
	public void save (ProductAttachment productAttachment) {
		this.sessionFactory.getCurrentSession().save(productAttachment);
	}	
	
	
	@Override
	public void delete(ProductAttachment productAttachment) {
		this.sessionFactory.getCurrentSession().delete(productAttachment);
	}
	
}
