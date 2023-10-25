///**
// *  Copyright 2013 Duke Energy Corporation
// *  No part of this computer program may be reproduced, transmitted,
// *  transcribed, stored in a retrieval system, or translated into any
// *  language in any form by any means without the prior written consent
// *  of Duke Energy Corporation.
// */
//
//package com.locuslink.dao.impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.hibernate.Criteria;
//import org.hibernate.SessionFactory;
//import org.hibernate.criterion.DetachedCriteria;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.dao.support.DaoSupport;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.locuslink.dao.UidProductAttributeListDao;
//import com.locuslink.dto.UidProductAttributeListDTO;
//import com.locuslink.model.UidProductAttributeList;
//
///**
// * This is the DAO implementation class for .....
// *  
// * @author C.Sparks
// * @since 1.0.0 - Sept 01, 2023 - Initial version
// */
//@Transactional
//@Repository(UidProductAttributeListDao.BEAN_NAME)
//public class UidProductAttributeListDaoImpl extends DaoSupport implements UidProductAttributeListDao, ApplicationContextAware {
//    
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@PersistenceContext
//	EntityManager entityManager;
//	
//	
//    public UidProductAttributeListDaoImpl() {
//    }
//    
//	@Override
//	protected void checkDaoConfig() throws IllegalArgumentException {
//	}
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//	}
//	
//	@Override
//	public UidProductAttributeList getById(int pkid) {	
//		return this.sessionFactory.getCurrentSession().get(UidProductAttributeList.class, pkid);				
//	}
//	
//	@Override
//	public UidProductAttributeListDTO getDtoById(int pkid) {	
//
//		return null;
//	
////		UidProductAttributeListDTO dto = entityManager.createQuery("""
////			select new com.locuslink.dto.UidProductAttributeListDTO(
////			
////		 		i.industryPkId,  
////		 		i.uid,   
////		 		i.industryCode,
////		 		i.industryDesc,
////		 		 	
////		 		si.subIndustryPkId,  
////		 		si.uid,   
////		 		si.subIndustryCode,
////		 		si.subIndustryDesc,
////		 		 				 
////		 		pt.productTypePkId,
////		 		pt.uid,			 				
////		 		pt.productTypeCode,				 						
////		 		pt.productTypeDesc,
////		 		 				 										
////		 		pst.productSubTypePkId,
////		 		pst.uid,			 				
////		 		pst.productSubTypeCode,				 						
////		 		pst.productSubTypeDesc,
////		 		
////		 		upal.uidPalPkId,
////		 		upal.uidGenSeq,			 				
////		 		upal.uidPalName,				 						
////		 		upal.uidPalAttributesJson		 										
////			)
////					from UidProductAttributeList upal			
////			    left  join Industry i            on i.industryPkId = upal.industryPkId  
////		 		left  join SubIndustry si       on si.subIndustryPkId = upal.subIndustryPkId 
////		 		left  join ProductType pt       on pt.productTypePkId = upal.productTypePkId
////		 		left join  ProductSubType pst  on pst.productSubTypePkId = upal.productSubTypePkId 				 			
////				where upal.industryPkId = :iPkId  
////				  and upal.subIndustryPkId =  :siPkId 
////				  and upal.productTypePkId =  :ptPkId			
////				  and (upal.productSubTypePkId = :sptPkId or upal.productSubTypePkId is null)  				 				
////			""", UidProductAttributeListDTO.class)
////				 .setParameter("iPkId", iPkId)
////				 .getSingleResult();
//			
//			
//	}
//	
//
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public  List<UidProductAttributeListDTO>  getDtoByProductType(int iPkId, int siPkId, int ptPkId, int sptPkId) 	{	
//
//		 List <UidProductAttributeListDTO> dtoList = entityManager.createQuery("""
//			select new com.locuslink.dto.UidProductAttributeListDTO(
//			
//		 		i.industryPkId,  
//		 		i.uid,   
//		 		i.industryCode,
//		 		i.industryDesc,
//		 		 	
//		 		si.subIndustryPkId,  
//		 		si.uid,   
//		 		si.subIndustryCode,
//		 		si.subIndustryDesc,
//		 		 				 
//		 		pt.productTypePkId,
//		 		pt.uid,			 				
//		 		pt.productTypeCode,				 						
//		 		pt.productTypeDesc,
//		 		 				 										
//		 		pst.productSubTypePkId,
//		 		pst.uid,			 				
//		 		pst.productSubTypeCode,				 						
//		 		pst.productSubTypeDesc,
//		 		
//		 		upal.uidPalPkId,
//		 		upal.uidGenSeq,		
//	 				
//		 		upal.uidPalName,				 						
//		 		upal.uidPalAttributesJson		 										
//			)
//			from UidProductAttributeList upal			
//			    left  join Industry i            on i.industryPkId = upal.industryPkId  
//		 		left  join SubIndustry si       on si.subIndustryPkId = upal.subIndustryPkId 
//		 		left  join ProductType pt       on pt.productTypePkId = upal.productTypePkId
//		 		left join  ProductSubType pst  on pst.productSubTypePkId = upal.productSubTypePkId 				 			
//				where upal.industryPkId = :iPkId  
//				  and upal.subIndustryPkId =  :siPkId 
//				  and upal.productTypePkId =  :ptPkId			
//				  and (upal.productSubTypePkId = :sptPkId or upal.productSubTypePkId is null)  				 				
//			""", UidProductAttributeListDTO.class)
//				 .setParameter("iPkId", iPkId)
//				 .setParameter("siPkId", siPkId)
//				 .setParameter("ptPkId", ptPkId)
//				 .setParameter("sptPkId", sptPkId)
//		.getResultList();	
//		 
//		 return dtoList;
//	}
//	
//	
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public  List<UidProductAttributeList>  getAll() {					
//		DetachedCriteria criteria= DetachedCriteria.forClass(UidProductAttributeList.class, "uidProductAttributeList");  		
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
//	//	criteria.addOrder(Order.asc ("customerPkId"));
//		return (List<UidProductAttributeList>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
//	}
//	
//
//	@Override
//	public void saveOrUpdate(UidProductAttributeList uidProductAttributeList) {
//		this.sessionFactory.getCurrentSession().saveOrUpdate(uidProductAttributeList);
//	}	
//	
//	@Override
//	public void save (UidProductAttributeList uidProductAttributeList) {
//		this.sessionFactory.getCurrentSession().save(uidProductAttributeList);
//	}	
//	
//	
//	@Override
//	public void delete(UidProductAttributeList uidProductAttributeList) {
//		this.sessionFactory.getCurrentSession().delete(uidProductAttributeList);
//	}
//	
//}
