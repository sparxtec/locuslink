package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;

@Data
public class Product {
	
	// UI Needs This upload page 3
	private String uploadedFilename;
	
	private String productNumber;
	
	private String productName;
	
	private String productDesc;
	
	private String activeStatus;
	
	
	// DB Needs This - ui page 3 submit
	private String productTypeCode;
	
}
