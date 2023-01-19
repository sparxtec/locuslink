/**
 *  Copyright 2013 Duke Energy Corporation
 *  No part of this computer program may be reproduced, transmitted,
 *  transcribed, stored in a retrieval system, or translated into any
 *  language in any form by any means without the prior written consent
 *  of Duke Energy Corporation.
 */

package com.locuslink.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.locuslink.dao.MaterialItemDao;
import com.locuslink.model.MaterialItem;


@Transactional
@Repository(MaterialItemDao.BEAN_NAME)
public class MaterialtemDaoImpl extends DaoSupport implements MaterialItemDao, ApplicationContextAware {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(MaterialtemDaoImpl.class);
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
	}
	
		
	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialItem> getAllItems() {
		Session session = this.sessionFactory.getCurrentSession();	
		return session.createQuery("from Item").getResultList();	
	}
	
	@Override
	public MaterialItem getById(int matItemId) {
		return this.sessionFactory.getCurrentSession().get(MaterialItem.class, matItemId);	
	}
	

	
	
	public void saveItem(MaterialItem materialItem) {
		this.sessionFactory.getCurrentSession().save(materialItem);  
	}

	public void updateItem(MaterialItem materialItem) {
		this.sessionFactory.getCurrentSession().update(materialItem);  
	}
	
	public void deleteItem(MaterialItem materialItem) {
		this.sessionFactory.getCurrentSession().delete(materialItem);  
	}
	
	
//	@Override
//	public Item getByItemNumAndHeatNum(String itemNum, String heatSerialNum) {
//		String sql = "select item from Item item where item.itemNum = :itemNum and item.heatSerialNum = :heatSerialNum ";		
//		return (Item) this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("itemNum", itemNum).setParameter("heatSerialNum", heatSerialNum).uniqueResult();
//	}
	
//	@Override
//	public List<Item> getByItemNumAndHeatNum(String itemNum) {
//		String sql = "select item from Item item where item.itemNum = :itemNum";		
//		return (List<Item>) this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("itemNum", itemNum).list();
//	}
//	
//	
//	
//	
//	
//	/**
//	 *  C.Sparks - 5-20-2020
//	 *  	This routine is used to return all the ITEMS, as a DTO that also has the MTR and Approvals if they exist.
//	 *  	This is needed when qdocreq is not involved, like when uploading to a ITMEMOnly or on the Receive Page, for ItemOnly.
//	 */
//	@Override
//	public List<ItemDTO> getItemDTOList_Search (String itemNumber, int contractorId) {
//		
//		String sql = "Select   "				 
//		 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//		 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//		 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//		 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//		 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//		 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//		 	+ " item.Model_Num as modelNum,   "
//				 	
//		 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//		 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//		 	+ " imtr.CoC_Flag as cocFlag, "
//		 	
////			+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//			
//			+ " imtra.Reasons as reasons,  "
//			+ " imtra.Comment as comment,  "						
//		 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//		 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//					 			
//			+ " from Item item "	
//			
//			
//			//These will be null almost always unless youre looking up an item that was originally uploaded to a PO
////			+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////			+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//						
//			+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//			+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//						
//			+ " where item.Item_Num = :itemNumber and (imtra.Contractor_Id = :contractorId or imtra.Contractor_Id = 0)"
//			+ " order by item.Item_Id asc";
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber).setParameter("contractorId", contractorId);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}	
//	
//	@Override
//	public List<ItemDTO> getItemDTOList_SearchbyItemOnly (String itemNumber) {
//		
//		String sql = "Select   "				 
//		 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//		 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//		 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//		 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//		 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//		 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//		 	+ " item.Model_Num as modelNum,   "
//				 	
//		 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//		 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//		 	+ " imtr.CoC_Flag as cocFlag, "
//		 	
////			+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//			
//			+ " imtra.Reasons as reasons,  "
//			+ " imtra.Comment as comment,  "						
//		 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//		 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//					 			
//			+ " from Item item "	
//			
//			
//			//These will be null almost always unless youre looking up an item that was originally uploaded to a PO
////			+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////			+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//						
//			+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//			+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//						
//			+ " where item.Item_Num = :itemNumber"
//			+ " order by item.Item_Id asc";
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@Override
//	public List<ItemDTO> getItemDTOListbyContractorId (String contractorId) {
//		
//		String sql = "Select   "				 
//		 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//		 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//		 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//		 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//		 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//		 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//		 	+ " item.Model_Num as modelNum,   "
//				 	
//		 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//		 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//		 	+ " imtr.CoC_Flag as cocFlag, "
//		 	
////			+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//			
//			+ " imtra.Reasons as reasons,  "
//			+ " imtra.Comment as comment,  "						
//		 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//		 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//					 			
//			+ " from Item item "	
//			
//			
//			//These will be null almost always unless youre looking up an item that was originally uploaded to a PO
////			+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////			+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//						
//			+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//			+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//						
//			+ " where imtra.Contractor_Id = :contractorId "
//			+ " order by item.Item_Id asc";
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("contractorId", contractorId);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}	
//	
//	
//	
//	
//	
//	/**
//	 *  C.Sparks 5-20-2020
//	 */
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOList_contractor (String itemNumber, String filterCode, int contractorId) {
//				
//		String filterClause = "";
//		if (filterCode.equals(TraceConstants.MTR_APPROVED) ||  filterCode.equals(TraceConstants.MTR_VALIDATED)) {			
//			filterClause = " and imtra.Valid_MTR_Flag= 'Y' "; 			
//		}  else if (filterCode.equals(TraceConstants.MTR_UNVALIDATED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");			
//			filterClause = " and (imtra.Inspected_MTR_Flag = 'N'  or imtra.Inspected_MTR_Flag is null )   "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//		
//		} 	else if (filterCode.equals(TraceConstants.MTR_FAILED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'Y'  and validMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");				
//			filterClause = " and imtra.Inspected_MTR_Flag = 'Y' "
//						 + " and imtra.Valid_MTR_Flag= 'N' "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_NOMTR)) {
//			//sqlBuff.append(" and (qdrl2.fileName = '' or qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd <> 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//						 + " and item.Traceability_Ind <> 'Y' ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_YESMTR)) {
//			//sqlBuff.append(" AND (qdrl2.fileName = '' OR qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd = 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//					 + " and item.Traceability_Ind = 'Y' ";
//			
//		}
//				
//		
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
////				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
////				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Num = :itemNumber and (imtra.Contractor_Id = :contractorId or imtra.Contractor_Id = 0) "
//				
//				+ filterClause
//					
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber).setParameter("contractorId", contractorId);		
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOList_contractorForManage (String itemNumber, String filterCode, int contractorId) {
//				
//		String filterClause = "";
//		if (filterCode.equals(TraceConstants.MTR_APPROVED) ||  filterCode.equals(TraceConstants.MTR_VALIDATED)) {			
//			filterClause = " and imtra.Valid_MTR_Flag= 'Y' "; 			
//		}  else if (filterCode.equals(TraceConstants.MTR_UNVALIDATED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");			
//			filterClause = " and (imtra.Inspected_MTR_Flag = 'N'  or imtra.Inspected_MTR_Flag is null )   "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//		
//		} 	else if (filterCode.equals(TraceConstants.MTR_FAILED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'Y'  and validMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");				
//			filterClause = " and imtra.Inspected_MTR_Flag = 'Y' "
//						 + " and imtra.Valid_MTR_Flag= 'N' "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_NOMTR)) {
//			//sqlBuff.append(" and (qdrl2.fileName = '' or qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd <> 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//						 + " and item.Traceability_Ind <> 'Y' ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_YESMTR)) {
//			//sqlBuff.append(" AND (qdrl2.fileName = '' OR qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd = 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//					 + " and item.Traceability_Ind = 'Y' ";
//			
//		}
//				
//		
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
////				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
////				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_File_Paths imtrfp  on imtrfp.Item_MTR_Id = imtr.Item_MTR_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Num = :itemNumber and (imtra.Contractor_Id = :contractorId or imtra.Contractor_Id = 0) "
//				
//				+ filterClause
//					
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber).setParameter("contractorId", contractorId);		
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOList(String itemNumber, String filterCode) {
//				
//		String filterClause = "";
//		if (filterCode.equals(TraceConstants.MTR_APPROVED) ||  filterCode.equals(TraceConstants.MTR_VALIDATED)) {			
//			filterClause = " and imtra.Valid_MTR_Flag= 'Y' "; 			
//		}  else if (filterCode.equals(TraceConstants.MTR_UNVALIDATED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");			
//			filterClause = " and (imtra.Inspected_MTR_Flag = 'N'  or imtra.Inspected_MTR_Flag is null )   "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//		
//		} 	else if (filterCode.equals(TraceConstants.MTR_FAILED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'Y'  and validMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");				
//			filterClause = " and imtra.Inspected_MTR_Flag = 'Y' "
//						 + " and imtra.Valid_MTR_Flag= 'N' "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_NOMTR)) {
//			//sqlBuff.append(" and (qdrl2.fileName = '' or qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd <> 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//						 + " and item.Traceability_Ind <> 'Y' ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_YESMTR)) {
//			//sqlBuff.append(" AND (qdrl2.fileName = '' OR qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd = 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//					 + " and item.Traceability_Ind = 'Y' ";
//			
//		}
//				
//		
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
////				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
////				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Num = :itemNumber "
//				
//				+ filterClause
//					
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOList_manage(String itemNumber, String filterCode) {
//				
//		String filterClause = "";
//		if (filterCode.equals(TraceConstants.MTR_APPROVED) ||  filterCode.equals(TraceConstants.MTR_VALIDATED)) {			
//			filterClause = " and imtra.Valid_MTR_Flag= 'Y' "; 			
//		}  else if (filterCode.equals(TraceConstants.MTR_UNVALIDATED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");			
//			filterClause = " and (imtra.Inspected_MTR_Flag = 'N'  or imtra.Inspected_MTR_Flag is null )   "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//		
//		} 	else if (filterCode.equals(TraceConstants.MTR_FAILED)) {
//			//sqlBuff.append(" and qdrl2.inspectedMTRFlag = 'Y'  and validMTRFlag = 'N' ");
//			//sqlBuff.append(" and qdrl2.fileName <> '' and qdrl2.fileName <> 'placeholder' ");				
//			filterClause = " and imtra.Inspected_MTR_Flag = 'Y' "
//						 + " and imtra.Valid_MTR_Flag= 'N' "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_NOMTR)) {
//			//sqlBuff.append(" and (qdrl2.fileName = '' or qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd <> 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//						 + " and item.Traceability_Ind <> 'Y' ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_YESMTR)) {
//			//sqlBuff.append(" AND (qdrl2.fileName = '' OR qdrl2.fileName = 'placeholder') AND qdrl2.traceabilityInd = 'Y' ");			
//			filterClause = " and imtr.File_Name is null  "
//					 + " and item.Traceability_Ind = 'Y' ";
//			
//		}
//				
//		
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
////				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
////				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
////				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_File_Paths imtrfp  on imtrfp.Item_MTR_Id = imtr.Item_MTR_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Num = :itemNumber "
//				
//				+ filterClause
//					
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemNumber", itemNumber);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//    public List<ItemDTO> getItemDTOListbyContractor(String contractorId, String filterCode) {
//		String filterClause = "";
//		if (filterCode.equals(TraceConstants.MTR_APPROVED) ||  filterCode.equals(TraceConstants.MTR_VALIDATED)) {			
//			filterClause = " and imtra.Valid_MTR_Flag= 'Y' "; 			
//		}  else if (filterCode.equals(TraceConstants.MTR_UNVALIDATED)) {	
//			filterClause = " and (imtra.Inspected_MTR_Flag = 'N'  or imtra.Inspected_MTR_Flag is null )   "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//		
//		} 	else if (filterCode.equals(TraceConstants.MTR_FAILED)) {			
//			filterClause = " and imtra.Inspected_MTR_Flag = 'Y' "
//						 + " and imtra.Valid_MTR_Flag= 'N' "
//						 + " and (imtr.File_Name <> ''  and imtr.File_Name is not null ) ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_NOMTR)) {		
//			filterClause = " and imtr.File_Name is null  "
//						 + " and item.Traceability_Ind <> 'Y' ";
//			
//		} else if (filterCode.equals(TraceConstants.MTR_YESMTR)) {		
//			filterClause = " and imtr.File_Name is null  "
//					 + " and item.Traceability_Ind = 'Y' ";
//			
//		}
//				
//		
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where imtra.Contractor_Id = :contractorId "
//				
//				+ filterClause
//					
//				+ " order by item.Item_Num asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("contractorId", contractorId);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	/**
//	 *  C.Sparks 5-20-2020
//	 */
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOListbyHeatandContractor(String heatNumber, int contractorId) {			
//		
//		String sql = "Select Distinct  "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
//				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, qdrl2.PO_Line_Num as poLineNum, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
//				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
//				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "	
//				+ "  left outer join Item_MTR_File_Paths imtrfp  on imtrfp.Item_MTR_Id = imtr.Item_MTR_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where (imtra.Contractor_Id = :contractorId or imtra.Contractor_Id = 0)"
//				+ "and upper(item.Heat_Serial_Num) like upper(:heatNumber) "
//				+ " and (imtr.File_Path like '%' + CAST(qdr2.PO_Num as varchar(15)) + '%' "
//				+ " or imtr.File_Path like '%allItemUploads%') "
//				
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("heatNumber", "%"+heatNumber+"%").setParameter("contractorId", contractorId);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}	
//	
//
//	/**
//	 *  V.Padma 02/18/2021
//	 */
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	public List<ItemDTO> getApprovedItemDTOListbyProject(String projectNumber, boolean getAllItemsFlag) {			
//	
//		String sql = "Select Distinct  "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//				+ " item.lvHeatValidationFlag as lvHeatValidationFlag, "
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, qdrl2.PO_Line_Num as poLineNum, "
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//			 	
//			 	+ " from QDoc_Req2 qdr2 "	
//				+ "  left outer join QDoc_Req_Line2 qdrl2 on qdoc.QDoc_Req_Id_2 = qdrl2.QDoc_Req_Id_2 "
//				+ "  left outer join Item item  on item.Item_Id = qdrl2.Item_Id"			
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "	
//				+ "  join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  		
//				+ " where qdr2.IR_Project_Num = :projectNumber" ;
//				
//				if(!getAllItemsFlag) {
//					sql = sql + "and imtra.Valid_MTR_Flag = 'Y' " ;
//				}		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("projectNumber", projectNumber);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}	
//	
//
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOListbyHeat(String heatNumber) {			
//		
//		String sql = "Select Distinct  "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
//				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, qdrl2.PO_Line_Num as poLineNum, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
//				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
//				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_File_Paths imtrfp  on imtrfp.Item_MTR_Id = imtr.Item_MTR_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where upper(item.Heat_Serial_Num) like upper(:heatNumber) "
//				+ " and (imtr.File_Path like '%' + CAST(qdr2.PO_Num as varchar(15)) + '%' "
//				+ " or imtr.File_Path like '%allItemUploads%') "
//				
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("heatNumber", "%"+heatNumber+"%");	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	//public List<QDocReqLine2DTO> getQDocReqLineDTOList_2 (int qDocReqId_2, String filterCode) {
//    public List<ItemDTO> getItemDTOListbyItemId(int itemId) {			
//		
//		String sql = "Select Distinct  "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//			 	
//				+ " CAST(qdr2.PO_Num as varchar(15)) as poNum, qdrl2.QDoc_Req_Line_Id_2 as qDocReqLineId_2, qdrl2.PO_Line_Num as poLineNum, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//				
//				+ "  left outer join QDoc_Req_Line2 qdrl2 on item.Item_Id = qdrl2.Item_Id "
//				+ "  left outer join QDoc_Req2 qdr2 on qdrl2.QDoc_Req_Id_2 = qdr2.QDoc_Req_Id_2"
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Id = :itemId "
//					
//				+ " order by item.Item_Id asc";
//		
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("itemId", itemId);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	/**
//	 * 	 05-20-2020 - Same as above, but return only one specific row, with all data.
//	 */
//	@SuppressWarnings({ "deprecation" })
//	public ItemDTO getItemDTO (int itemId ) {
//				
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Id = :itemId "
//				+ " order by item.Item_Id asc";
//					
//			@SuppressWarnings("rawtypes")
//			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//				.setParameter("itemId", itemId);	
//				query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//			
//			return (ItemDTO) query.setMaxResults(1).uniqueResult();		
//			
//	}	
//	
//	@SuppressWarnings({ "deprecation" })
//	public ItemDTO getItemDTObyIdandFileName (int itemId, String fileName) {
//				
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtr.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "			
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Id = :itemId and imtr.File_Name = :fileName "
//				+ " order by item.Item_Id asc";
//					
//			@SuppressWarnings("rawtypes")
//			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//				.setParameter("itemId", itemId).setParameter("fileName", fileName);	
//				query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//			
//			return (ItemDTO) query.setMaxResults(1).uniqueResult();		
//			
//	}	
//	
//	@SuppressWarnings({ "deprecation" })
//	public ItemDTO getItemDTObyIdandFileNameandContractorandFilePath(int itemId, String fileName, String contractorId, String filePath) {
//				
//		String sql = "Select   "				 
//			 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//			 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//			 	+ " item.Item_Desc as itemDesc, item.Manufacturer as manufacturer,  "
//			 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
//			 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
//			 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
//			 	+ " item.Model_Num as modelNum, "
//					 	
//			 	+ " imtr.Item_MTR_Id as itemMTRId, imtr.File_Name as fileName, "			
//			 	+ " imtr.File_Type as fileType, imtrfp.File_Path as filePath, "
//			 	+ " imtr.CoC_Flag as cocFlag, "
//				
//				+ " imtra.Reasons as reasons,  "
//				+ " imtra.Comment as comment,  "						
//			 	+ " imtra.Item_MTR_Approval_Id as itemMTRApprovalId, imtra.Contractor_Id as contractorId, "	
//			 	+ " imtra.Inspected_MTR_Flag as inspectedMTRFlag, imtra.Valid_MTR_Flag as validMTRFlag  "	
//						 			
//				+ " from Item item "	
//							
//				+ "  left outer join Item_MTR imtr  on item.Item_Id = imtr.Item_Id "
//				+ "  left outer join Item_MTR_File_Paths imtrfp  on imtrfp.Item_MTR_Id = imtr.Item_MTR_Id "
//				+ "  left outer join Item_MTR_Approval imtra  on imtr.Item_MTR_Id = imtra.Item_MTR_Id"  // bring in null rows
//							
//				+ " where item.Item_Id = :itemId and imtr.File_Name = :fileName "
//				+ "and (imtra.Contractor_Id = :contractorId or imtra.Contractor_Id is null) and imtrfp.File_Path = :filePath"
//				+ " order by item.Item_Id asc";
//					
//			@SuppressWarnings("rawtypes")
//			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//				.setParameter("itemId", itemId).setParameter("fileName", fileName).setParameter("contractorId", contractorId).setParameter("filePath", filePath);	
//				query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//			
//			return (ItemDTO) query.setMaxResults(1).uniqueResult();		
//			
//	}
//	
//	@Override
//	public List<ItemDTO> getItemDTOListbyHeatValidationErrors(String projectNumber) {
//		
//		String sql = "Select"				 
//		 	+ " item.Item_Num as itemNum , item.Item_Id as item_ItemId, "
//		 	+ " item.Heat_Serial_Num as heatSerialNum, item.Heat_Serial_Ind as heatSerialInd,  "
//		 	+ " item.Item_Desc as itemDesc "
////		 	+ " item.Manufacturer as manufacturer,  "
////		 	+ " item.Mfg_Item_Desc as mfgItemDesc, item.Unit_Of_Measure as unitOfMeasure, "
////		 	+ " item.Traceability_Ind as traceabilityInd, item.Cutsheet_Flag as cutsheetFlag, "
////		 	+ " item.Pipe_Flag as pipeFlag, item.Order_Qty as orderQty, "
////		 	+ " item.Model_Num as modelNum   "
//	
//					 			
//			+ " from Item item "	
//
//			+ "  left outer join Trace_BOM_Item_Heat_Errors ihe on ihe.Item_Num = item.Item_Num and ihe.Heat_Num = item.Heat_Serial_Num "			
//			+ "  left outer join LV_Projects_BOM lvb on lvb.Item_Num = ihe.Item_Num"			
//			+ "  left outer join LV_Projects lvp on lvp.LV_Project_Id = lvb.LV_Project_Id " // bring in null rows
//						
//			+ " where lvp.Trace_Project_Num = :traceProjectNumber "
//			+ " and item.LV_HeatValidation_Flag = 'Y' "
//			+ " order by item.Item_Id asc";
//				
//		@SuppressWarnings("rawtypes")
//		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql)			
//			.setParameter("traceProjectNumber", projectNumber);	
//			query.setResultTransformer(new AliasToBeanResultTransformer(ItemDTO.class));
//		
//		return (List<ItemDTO>) query.list();		
//	}
//	
//	
//	
	
	
	
}
