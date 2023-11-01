package com.locuslink.dto;

import lombok.Data;

@Data
public class RequiredDocumentsDTO {

	public RequiredDocumentsDTO() {
		
	};
		
	public RequiredDocumentsDTO(  
			String docName, 
			String docTypeCode,
			String docTypeDesc			
			                ) {
		this.docName = docName;	
		this.docTypeCode = docTypeCode;	
		this.docTypeDesc = docTypeDesc;	

	}



	
	
	// Product Attachments
	private String docName;
	private String docTypeCode;
	private String docTypeDesc;



}
