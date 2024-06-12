
package com.locuslink.dto;

import lombok.Data;

@Data
public class AssemblyFormDTO {

    private int assemblyPkid; 
    private String jobNumber;
    private String jobDescription; 
    private String location;
    private String stationNumber; 
    private String traceNumber;
	   
    private String stationName;
    private String designSpecNumber;
    private String drawingNumber; 
    private String fabricatorCompanyName;
    private String customerSpecNumber;




}
