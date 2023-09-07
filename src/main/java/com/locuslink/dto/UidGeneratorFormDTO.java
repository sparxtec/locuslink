package com.locuslink.dto;

import lombok.Data;

@Data
public class UidGeneratorFormDTO {

	private String uidGenString;
	
	private String selectedIndustryPkId;
	private String selectedIndustryUid;

	private String selectedSubIndustryPkId;
	private String selectedSubIndustryUid;
	
	private String selectedVersionPkId;	
	private String selectedVersionUid;	

	private String selectedProductTypePkId;
	private String selectedProductTypeUid;
	
	private String selectedProductSubTypePkId;
	private String selectedProductSubTypeUid;

}
