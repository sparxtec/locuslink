package com.locuslink.dto;

import lombok.Data;

@Data
public class AssemblyAttachmentDTO {

	public AssemblyAttachmentDTO() {
		
	};
		
	public AssemblyAttachmentDTO(  
			int aaPkid, 
			int assemblyPkid,
			int docTypePkid,
			String filenameFullpath,
			String attributesJson,
			String docTypeCode,
			String docTypeDesc,
			String assemblyReqDocDesc			
		 ){
		this.aaPkid = aaPkid;	
		this.assemblyPkid = assemblyPkid;	
		this.docTypePkid = docTypePkid;	
		this.filenameFullpath = filenameFullpath;
		this.attributesJson = attributesJson;
		this.docTypeCode = docTypeCode;
		this.docTypeDesc = docTypeDesc;
		this.assemblyReqDocDesc = assemblyReqDocDesc;
	}

	// Assembly Attachment
	private int aaPkid;
	private int assemblyPkid;
	private int docTypePkid;
	private String filenameFullpath;
	private String attributesJson; 
	
	// Doc Type
	private String docTypeCode;	
	private String docTypeDesc;	
	 
	// Assembly Docs {required and not}
	private String assemblyReqDocDesc;

}
