package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;


@Data
public class SteelPipeAttributes extends Product {
	
	// 04-25-2023 - Used by upload page 3, save to DB
	private String jsonSteelPipeAttributeList;
	
	
	// Future, if we need the json attributes from the product_attribute Table
	// Traceable Unique Attributes - prefix with ua
	private String ua1;	
	private String ua2;	
	private String ua3;	
	private String ua4;	
	private String ua5;
	
	
	// Additional Attributes - prefix with aa
	private String aa01;	
	private String aa02;
	private String aa03;	
	private String aa04;
	private String aa05;	
	private String aa06;
	private String aa07;	
	private String aa08;
	private String aa09;	
	private String aa10;
	
	

	
	
}
