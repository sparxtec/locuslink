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

import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.model.AssemblyAttachment;

/**
 * This is the DAO implementation class for
 *  
 * @author C.Sparks
 * @since 1.0.0 - May 01, 2024 - Initial version
 */
@Transactional
@Repository(AssemblyAttachmentDao.BEAN_NAME)
public class AssemblyAttachmentDaoImpl extends DaoSupport implements AssemblyAttachmentDao, ApplicationContextAware {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
    public AssemblyAttachmentDaoImpl() {
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	
	@Override
	public AssemblyAttachment getById(int pkid) {	
		return this.sessionFactory.getCurrentSession().get(AssemblyAttachment.class, pkid);				
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public  List<AssemblyAttachment>  getAllById( int assemblyPkid ) {					
		DetachedCriteria criteria= DetachedCriteria.forClass(AssemblyAttachment.class, "assemblyAttachment");  		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		criteria.add(Restrictions.eq("assemblyPkid", assemblyPkid));
		return (List<AssemblyAttachment>) criteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();				
	}
	
	
	
	
	@Override
	public AssemblyAttachmentDTO getDtoById(int pkid) {	

		AssemblyAttachmentDTO dto = entityManager.createQuery("""
			select new com.locuslink.dto.AssemblyAttachmentDTO(				
				aa.aaPkid,
				aa.assemblyPkid,
		 		aa.docTypePkid,
		 		aa.filenameFullpath,
				aa.attributesJson,
			    aa.attrFlag,
				dt.docTypeCode,
				dt.docTypeDesc,
				ard.docDescription	 							 				
			)
			from AssemblyAttachment aa			
		    left outer join DocumentType dt on aa.docTypePkid = dt.docTypePkId	
		    left outer join AssemblyReqDoc ard on ard.ardPkid = aa.ardPkid							 																	
			where aa.aaPkid = :pkid
									
			""", AssemblyAttachmentDTO.class)
				 .setParameter("pkid", pkid)
				 .getSingleResult();				 
		 return dto;		
	}
	



	@Override
	public  List<AssemblyAttachmentDTO>  getAllDTObyAssemblyId( int assemblyPkid) 	{	
				
		 List <AssemblyAttachmentDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.AssemblyAttachmentDTO(
				aa.aaPkid,
				aa.assemblyPkid,
		 		aa.docTypePkid,
		 		aa.filenameFullpath,
				aa.attributesJson,
				aa.attrFlag,
				dt.docTypeCode,
				dt.docTypeDesc,
				ard.docDescription	 													
			)
			from AssemblyAttachment aa
	        left outer join DocumentType dt on aa.docTypePkid = dt.docTypePkId	
	        left outer join AssemblyReqDoc ard on ard.ardPkid = aa.ardPkid	
	         
	         
	        where aa.assemblyPkid = :assemblyPkid			
	 	      and aa.docTypePkid  in (60,61)
	 			 																																	
			""", AssemblyAttachmentDTO.class)
				 .setParameter("assemblyPkid", assemblyPkid)
				 .getResultList();	
		 
		 return dtoList;
	}

	
	
	
	@Override
	public  List<AssemblyAttachmentDTO>  getAllDTOMtrByAssemblyId( int assemblyPkid) 	{	
				
		 List <AssemblyAttachmentDTO> dtoList = entityManager.createQuery("""
			select new com.locuslink.dto.AssemblyAttachmentDTO(
				aa.aaPkid,
				aa.assemblyPkid,
		 		aa.docTypePkid,
		 		aa.filenameFullpath,
				aa.attributesJson,
				aa.attrFlag,
				dt.docTypeCode,
				dt.docTypeDesc,
				ard.docDescription	 													
			)
			from AssemblyAttachment aa
	        left outer join DocumentType dt on aa.docTypePkid = dt.docTypePkId	
	        left outer join AssemblyReqDoc ard on ard.ardPkid = aa.ardPkid	
	         
	         
	        where aa.assemblyPkid = :assemblyPkid		
	          and aa.docTypePkid  = 62		
	 																																				
			""", AssemblyAttachmentDTO.class)
				 .setParameter("assemblyPkid", assemblyPkid)
				 .getResultList();	
		 
		 return dtoList;
	}
	

	@Override
	public void saveOrUpdate(AssemblyAttachment assemblyAttachment) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(assemblyAttachment);
	}	
	
	@Override
	public void save (AssemblyAttachment assemblyAttachment) {
		this.sessionFactory.getCurrentSession().save(assemblyAttachment);
	}	
	
	
	@Override
	public void delete(AssemblyAttachment assemblyAttachment) {
		this.sessionFactory.getCurrentSession().delete(assemblyAttachment);
	}
	
}
