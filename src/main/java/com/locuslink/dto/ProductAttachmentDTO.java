package com.locuslink.dto;

import lombok.Data;

@Data
public class ProductAttachmentDTO {

	public ProductAttachmentDTO() {
		
	};
		
	public ProductAttachmentDTO( int productAttachPkId, String filenameFullpath, String docTypeCode, String docTypeDesc,			
			                     int uniqueAssetPkId, String uniqueAssetId,	 String traceCode,	String manufacturerName,
			                     String productTypeCode, String traceTypeCode,  String productTypeDesc ) {
				
		this.productAttachPkId = productAttachPkId;	
		this.filenameFullpath = filenameFullpath;	
		this.docTypeCode = docTypeCode;	
		this.docTypeDesc = docTypeDesc;	
		
		this.uniqueAssetPkId = uniqueAssetPkId; 
		this.uniqueAssetId = uniqueAssetId; 
		this.traceCode = traceCode;				
		this.manufacturerName = manufacturerName;
		
		this.productTypeCode = productTypeCode;			
		this.traceTypeCode = traceTypeCode;					
		this.productTypeDesc = productTypeDesc;				
	}


	// Unique Asset
	private int uniqueAssetPkId;
	private String uniqueAssetId;
	private int traceabilityTypePkId;
	private String traceCode;

	// MFG
	private String manufacturerName;
			
	// Trace Type
	private String traceTypeCode;
	private String traceTypeDesc;
			
	// Universal Catalog
	private String universalCatalogId;
	private String productNumber;
	private String productName;
	private String productDesc;
		
	// Product Type	
    private String productTypeCode;
	private String productTypeDesc; 
	
	
	// Product Attachments
	private int productAttachPkId;
	private String filenameFullpath;
	private String docTypeCode;
	private String docTypeDesc;



}
