package com.locuslink.dto;

import lombok.Data;

@Data
public class UniqueAssetDTO {

	public UniqueAssetDTO() {
	};
		
	public UniqueAssetDTO( 
			int uniqueAssetPkId, 
			String uniqueAssetId, 						  
			String universalCatalogId, 
			int ucatPkId, 						   
			String manufacturerName, 
			
			String productTypeCode, 			              
			String traceTypeCode, 
			String traceCode,
			
		    // cat def template
		    Integer cdugtPkId, 
		    Integer industryPkId, 
		    Integer subIndustryPkId, 
		    Integer productTypePkId, 
		    Integer productSubTypePkId, 
          
           // 4 keys desc
           String industryDesc,
           String subIndustryDesc,
           String productTypeDesc,
           String productSubTypeDesc,
	           
		   String customerName 

		){
		

		this.uniqueAssetPkId = uniqueAssetPkId; 
		this.uniqueAssetId = uniqueAssetId; 
		this.universalCatalogId = universalCatalogId;				
		this.ucatPkId = ucatPkId;	
		
		this.manufacturerName = manufacturerName;	
		this.productTypeCode = productTypeCode;					
		this.traceTypeCode = traceTypeCode;		
		this.traceCode = traceCode;		
		
		this.cdugtPkId = cdugtPkId;	
		this.industryPkId = industryPkId;	
		this.subIndustryPkId = subIndustryPkId;	
		this.productTypePkId = productTypePkId;	
		this.productSubTypePkId = productSubTypePkId;
				
		
        // 4 keys desc
		this.industryDesc =industryDesc;
		this.subIndustryDesc =subIndustryDesc;
		this.productTypeDesc = productTypeDesc;
		this.productSubTypeDesc = productSubTypeDesc;	
        
        
		this.customerName = customerName;	
				
	}

	// Unique Asset
	private int uniqueAssetPkId;
	private String uniqueAssetId;
	private String universalCatalogId; 
	private int ucatPkId;
	
	private int traceabilityTypePkId;
	private int customerPkId;
	private String traceCode;

	// MFG
	private String manufacturerName;
			
	// Trace Type
	private String traceTypeCode;
	private String traceTypeDesc;
	
	// Catalog Definition Template
    Integer cdugtPkId;
    Integer industryPkId;
    Integer subIndustryPkId; 
    Integer productTypePkId; 
    Integer productSubTypePkId; 
    
    // 4 keys desc
    String industryDesc;
    String subIndustryDesc;
    String productTypeDesc;
    String productSubTypeDesc;
    
	// Customer
	private String customerTypeCode;
	private String customerName;
		
	// Product Type	
    private String productTypeCode;

	// Product Attributes	
	private String attributesJson; 


}
