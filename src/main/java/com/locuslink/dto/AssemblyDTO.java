package com.locuslink.dto;

import java.util.List;

import lombok.Data;

@Data
public class AssemblyDTO {

	public AssemblyDTO() {		
	};
		
	public AssemblyDTO( 
		   //  assembly
		   Integer assemblyPkid, 
		   String jobNumber, 
		   String jobDescription, 
		   String location, 
		   String stationNumber, 
		   String traceNumber, 
		   
		   // AssemblyReqDoc
		   Integer ardPkid, 
		   Integer aard_docTypePkid, 
           String docDescription,
           
           // AssemblyAttachment
           Integer aaPkid, 
		   Integer aa_docTypePkid, 
           String filenameFullpath,
           String attributesJson,

           //AssemblyAIStatus
           Integer aaisPkid, 
           String statusType, 
           String status, 
           String statusDescription
           
           ){
			            			
		this.assemblyPkid = assemblyPkid;		
		this.jobNumber = jobNumber;	
		this.jobDescription = jobDescription;			
		this.location = location;	
		this.stationNumber = stationNumber;	
		this.traceNumber = traceNumber;	
				
		this.ardPkid = ardPkid;	
		this.aard_docTypePkid = aard_docTypePkid;	
		this.docDescription = docDescription;
		
		this.aaPkid = aaPkid;			
		this.aa_docTypePkid = aa_docTypePkid;	
		this.filenameFullpath = filenameFullpath;	
		this.attributesJson = attributesJson;	
			
		this.aaisPkid = aaisPkid;
		this.statusType = statusType;
		this.status = status;
		this.statusDescription = statusDescription;		
	}


    //  assembly
    Integer assemblyPkid; 
    String jobNumber;
    String jobDescription; 
    String location;
    String stationNumber; 
    String traceNumber;
	   
	// AssemblyReqDoc
	Integer ardPkid;
	Integer aard_docTypePkid; 
    String docDescription;
    
    // AssemblyAttachment
    Integer aaPkid;
	Integer aa_docTypePkid; 
    String filenameFullpath;
    String attributesJson;

    //AssemblyAIStatus
    Integer aaisPkid;
    String statusType; 
    String status;
    String statusDescription;
	
}
