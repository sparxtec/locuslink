package com.locuslink.dto;

import lombok.Data;

@Data
public class UniqueAssetDTO {

	public UniqueAssetDTO() {
	};
		
	public UniqueAssetDTO( 
			//ua
			Integer uniqueAssetPkId, 
			String uniqueAssetId,
			String traceCode,
			
			              
			String traceTypeCode, 
			String manufacturerName, 
			String customerName ,
			   
			//ucat
			Integer ucatPkId, 	
			String universalCatalogId, 
			String availFlag,
			
			String attributesJson,
			
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
           String productSubTypeDesc,           
           String productTypeCode
           	           
		){
		

		this.uniqueAssetPkId = uniqueAssetPkId; 
		this.uniqueAssetId = uniqueAssetId; 
		this.traceCode = traceCode;	
		
		this.traceTypeCode = traceTypeCode;		
		this.manufacturerName = manufacturerName;	
		this.customerName = customerName;	
		
		this.ucatPkId = ucatPkId;
		this.universalCatalogId = universalCatalogId;				
		this.availFlag = availFlag;
		

		this.attributesJson = attributesJson;					

	
		
		this.cdugtPkId = cdugtPkId;	
		this.industryPkId = industryPkId;	
		this.subIndustryPkId = subIndustryPkId;	
		this.productTypePkId = productTypePkId;	
		this.productSubTypePkId = productSubTypePkId;
		this.uidTemplateLen = uidTemplateLen;	
		this.uidTemplate = uidTemplate;			
		
        // 4 keys desc
		this.industryDesc =industryDesc;
		this.subIndustryDesc =subIndustryDesc;
		this.productTypeDesc = productTypeDesc;
		this.productSubTypeDesc = productSubTypeDesc;	
		this.productTypeCode = productTypeCode;	
        		
	}

	// Unique Asset
	private int uniqueAssetPkId;
	private String uniqueAssetId;
	private String universalCatalogId; 
	private int ucatPkId;
	
	private int traceabilityTypePkId;
	private int customerPkId;
	private String traceCode;
	
	private String availFlag;
	

	// MFG
	private String manufacturerName;
			
	// Trace Type
	private String traceTypeCode;
	private String traceTypeDesc;
	
	// Catalog Definition Template
	private Integer cdugtPkId;
	private Integer industryPkId;
	private Integer subIndustryPkId; 
	private Integer productTypePkId; 
	private Integer productSubTypePkId; 
	private Integer uidTemplateLen;
	private String uidTemplate;
    
    // 4 keys desc
	private String industryDesc;
	private String subIndustryDesc;
	private String productTypeDesc;
	private String productSubTypeDesc;
    
	// Customer
	private String customerTypeCode;
	private String customerName;
		
	// Product Type	
    private String productTypeCode;

	// Product Attributes	
	private String attributesJson; 


}
