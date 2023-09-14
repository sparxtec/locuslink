package com.locuslink.dto;

import lombok.Data;

@Data
public class UidProductAttributeListDTO {

	public UidProductAttributeListDTO() {
		
	};
		
	public UidProductAttributeListDTO( int industryPkId, String industryUid, String industryCode, String industryDesc,
			 int subIndustryPkId, String subIndustryUid, String subIndustryCode, String subIndustryDesc,
			 int productTypePkId, String productTypeUid, String productTypeCode, String productTypeDesc,			 
			 Integer productSubTypePkId, String productSubTypeUid, String productSubTypeCode, String productSubTypeDesc,			 
			 Integer uidPalPkId, int uidGenSeq, String uidPalName, String uidPalAttributesJson	) {	
		
		this.industryPkId = industryPkId; this.industryUid = industryUid; 
		this.industryCode = industryCode;	this.industryDesc = industryDesc;		
		
		this.subIndustryPkId = subIndustryPkId; this.subIndustryUid = subIndustryUid; 
		this.subIndustryCode = subIndustryCode;	this.subIndustryDesc = subIndustryDesc;
					
		this.productTypePkId = productTypePkId; this.productTypeUid = productTypeUid; 
		this.productTypeCode = productTypeCode;	this.productTypeDesc = productTypeDesc;
		
		this.productSubTypePkId = productSubTypePkId; this.productSubTypeUid = productSubTypeUid; 
		this.productSubTypeCode = productSubTypeCode;	this.productSubTypeDesc = productSubTypeDesc;
			
		this.uidPalPkId = uidPalPkId; this.uidGenSeq = uidGenSeq; 
		this.uidPalName = uidPalName;	this.uidPalAttributesJson = uidPalAttributesJson;
	}

	// Industry
    private Integer industryPkId;   
    private String industryUid;    
    private String industryCode;
	private String industryDesc; 
	
	// Sub Industry
    private Integer subIndustryPkId;
    private String subIndustryUid;
    private String subIndustryCode;
	private String subIndustryDesc; 
    
	// Asset - Product Type
    private Integer productTypePkId;
    private String productTypeUid;
    private String productTypeCode;
	private String productTypeDesc; 
    
	// Sub Asset - Product Sub Type
    private Integer productSubTypePkId;
    private String productSubTypeUid;
    private String productSubTypeCode;
	private String productSubTypeDesc;
	
	// Sub Asset - Product Sub Type
    private Integer uidPalPkId;
    private int uidGenSeq;
    private String uidPalName;
	private String uidPalAttributesJson;
	

}
