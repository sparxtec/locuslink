package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;

/**
 * 
 * This class holds all the attributes for a product, both unique and additional.
 * 
 * The attribute type will be prefixed, so by convention we know where to put the
 * attribute in the data base
 *
 */
@Data
public class ProductWire extends Product {
	
	//Unique Attributes - prefix with ua
	private String uaWireSize;
	
	private String ua2;
	
	private String ua3;
	
	private String ua4;
	
	private String ua5;
	
	
	// Additional Attributes - prefix with aa
	private String aaVoltage;
	
	private String aa21;
	
	private String aa22;
	
	private String aa23;
		
	
}
