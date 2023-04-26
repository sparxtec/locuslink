package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;

@Data
public class ProductDTO {
	
	// UI Needs This upload page 3
	private String uploadedFilename;
	
	private String productCatalogId;
	
	private String productNumber;
	
	private String productName;
	
	private String productDesc;
	
	private String activeStatus;
	
	private String productTypeCode;
	
	// TODO For Demo This needs to be in Steel DTO, temp gere for now due to time constraint
	
	private String heatNumber;
	
}
