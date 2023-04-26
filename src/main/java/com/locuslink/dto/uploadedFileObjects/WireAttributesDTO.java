package com.locuslink.dto.uploadedFileObjects;

import lombok.Data;


@Data
public class WireAttributesDTO extends ProductDTO {
	
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
