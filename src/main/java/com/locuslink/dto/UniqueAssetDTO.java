package com.locuslink.dto;

import lombok.Data;

@Data
public class UniqueAssetDTO {

	public UniqueAssetDTO() {
		
	};
		
	public UniqueAssetDTO( int uniqueAssetPkId, String uniqueAssetId, String universalCatalogId, 			
			               String manufacturerName, String productTypeCode, String productName, 
			               String productNumber, String traceTypeCode, String traceCode,
			               String customerName, String productDesc, String productTypeDesc,
			               String uniqueAttributesJson	) {
		
		this.uniqueAssetPkId = uniqueAssetPkId; 
		this.uniqueAssetId = uniqueAssetId; 
		this.universalCatalogId = universalCatalogId;		
		this.manufacturerName = manufacturerName;		
		this.productTypeCode = productTypeCode;		
		this.productName = productName;
		this.productNumber = productNumber;		
		this.traceTypeCode = traceTypeCode;		
		this.traceCode = traceCode;		
		
		this.customerName = customerName;
		this.productDesc = productDesc;		
		this.productTypeDesc = productTypeDesc;		
		this.uniqueAttributesJson = uniqueAttributesJson;			
	}


	// Unique Asset
	private int uniqueAssetPkId;
	private String uniqueAssetId;
	private int ucatPkId;

	private int traceabilityTypePkId;
	private int customerPkId;
	private String traceCode;

	// MFG
	private String manufacturerName;
			
	// Trace Type
	private String traceTypeCode;
	private String traceTypeDesc;
	
	// Customer
	private String customerTypeCode;
	private String customerName;
	
	
	
	// Universal Catalog
	private String universalCatalogId;
	private String productNumber;
	private String productName;
	private String productDesc;
		
	// Product Type	
    private String productTypeCode;
	private String productTypeDesc; 
	
	// Product Attributes	
	private String uniqueAttributesJson; 


}
