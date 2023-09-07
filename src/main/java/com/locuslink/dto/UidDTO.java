package com.locuslink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UidDTO {


    private int industryPkId;   
    private String industryUid;    
    private String industryCode;
	private String industryDesc; 
	
	
    private int subIndustryPkId;
    private String subIndustryUid;
    private String subIndustryCode;
	private String subIndustryDesc; 
    
    private int versionPkId;
    private String versionUid;
    private String versionCode;
    private String versionDesc;

    private int productTypePkId;
    private String productTypeUid;
    private String productTypeCode;
	private String productTypeDesc; 
    
    private int productSubTypePkId;
    private String productSubTypeUid;
    private String productSubTypeCode;
	private String productSubTypeDesc;  
    
    
}
