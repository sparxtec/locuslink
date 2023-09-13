package com.locuslink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UidDTO {


    private Integer industryPkId;   
    private String industryUid;    
    private String industryCode;
	private String industryDesc; 
	
	
    private Integer subIndustryPkId;
    private String subIndustryUid;
    private String subIndustryCode;
	private String subIndustryDesc; 
    
    private Integer versionPkId;
    private String versionUid;
    private String versionCode;
    private String versionDesc;

    private Integer productTypePkId;
    private String productTypeUid;
    private String productTypeCode;
	private String productTypeDesc; 
    
    private Integer productSubTypePkId;
    private String productSubTypeUid;
    private String productSubTypeCode;
	private String productSubTypeDesc;  
    
    
}
