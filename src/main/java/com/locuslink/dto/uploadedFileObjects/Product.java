package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;

@Data
public class Product {
	
	// UI Needs This
	private String uploadedFilename;
	
	// DB Needs This
	private String productType;
	
	private String productNumber;
	
	private String productName;
	
	private String productDesc;
	
	private String activeStatus;
	
	
}
