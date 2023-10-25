package com.locuslink.dto;

import java.util.List;

import lombok.Data;

@Data
public class UniversalCatalogDTO {

	public UniversalCatalogDTO() {		
	};
		
	public UniversalCatalogDTO( 
		   //  ucat
		   Integer ucatPkId, 
		   String universalCatalogId, 
		   String availFlag, 
		   
		   // cat def template
		   Integer cdugtPkId, 
		   Integer industryPkId, 
		   Integer subIndustryPkId, 
		   Integer productTypePkId, 
		   Integer productSubTypePkId, 
		   Integer uidTemplateLen,
           String uidTemplate,
           
           // 4 keys desc
           String industryDesc,
           String subIndustryDesc,
           String productTypeDesc,
           String productSubTypeDesc
           

           
//           // cat def attr list
//           Integer cdalPkId,
//           Integer uidAttrSeq,
//           String uidAttrListName,
//           String uidAttributesJson 
           ){
			            			
		this.ucatPkId = ucatPkId;		
		this.universalCatalogId = universalCatalogId;	
		this.availFlag = availFlag;	
		
		this.cdugtPkId = cdugtPkId;	
		this.industryPkId = industryPkId;	
		this.subIndustryPkId = subIndustryPkId;	
		this.productTypePkId = productTypePkId;	
		this.productSubTypePkId = productSubTypePkId;	
		this.uidTemplateLen = uidTemplateLen;	
		this.uidTemplate = uidTemplate;	
		
		this.industryDesc = industryDesc;	
		this.subIndustryDesc = subIndustryDesc;	
		this.productTypeDesc = productTypeDesc;	
		this.productSubTypeDesc = productSubTypeDesc;
		
//		this.cdalPkId = cdalPkId;	
//		this.uidAttrSeq = uidAttrSeq;	
//		this.uidAttrListName = uidAttrListName;	
//		this.uidAttributesJson = uidAttributesJson;	
	}


	// Universal Catalog
	Integer ucatPkId; 
    String universalCatalogId;
    String availFlag;
	   
	// Catalog Definition Template
    Integer cdugtPkId;
    Integer industryPkId;
    Integer subIndustryPkId; 
    Integer productTypePkId; 
    Integer productSubTypePkId; 
    Integer uidTemplateLen;
    String uidTemplate;
    
    // 4 keys desc
    String industryDesc;
    String subIndustryDesc;
    String productTypeDesc;
    String productSubTypeDesc;
    
	List<CatalogAttributeListDTO> attributeList;
	
//    // Catalog Definition  attr list
//    Integer cdalPkId;
//    Integer uidAttrSeq;
//    String uidAttrListName;
//    String uidAttributesJson;

}
