package com.locuslink.dto;

import java.util.List;

import javax.persistence.Column;

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
		   
		   String stationName,
		   String designSpecNumber, 
		   String drawingNumber, 
		   String fabricatorCompanyName, 
		   String customerSpecNumber
           
           ){
			            			
		this.assemblyPkid = assemblyPkid;		
		this.jobNumber = jobNumber;	
		this.jobDescription = jobDescription;			
		this.location = location;	
		this.stationNumber = stationNumber;	
		this.traceNumber = traceNumber;	
				
		this.stationName = stationName;	
		this.designSpecNumber = designSpecNumber;	
		this.drawingNumber = drawingNumber;
		this.fabricatorCompanyName = fabricatorCompanyName;	
		this.customerSpecNumber = customerSpecNumber;			
		
	}


    //  assembly
    Integer assemblyPkid; 
    String jobNumber;
    String jobDescription; 
    String location;
    String stationNumber; 
    String traceNumber;
	   
    String stationName;
    String designSpecNumber;
    String drawingNumber; 
    String fabricatorCompanyName;
    String customerSpecNumber;
	
}
