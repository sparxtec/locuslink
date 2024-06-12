package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="assembly")
public class Assembly extends Common {

	public Assembly() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="assembly_pkid", unique=true, nullable = false)
    private Integer assemblyPkid;
  
    @Column(name = "job_number")
	private String jobNumber; 
    
    @Column(name = "job_description")
	private String jobDescription; 
    
    @Column(name = "location")
	private String location; 
    
    @Column(name = "station_number")
	private String stationNumber; 
    
    @Column(name = "trace_number")
	private String traceNumber; 
    
    
    @Column(name = "station_name")
	private String stationName; 
    
    @Column(name = "design_spec_number")
	private String designSpecNumber; 
    
    @Column(name = "drawing_number")
	private String drawingNumber; 
    
    @Column(name = "fabricator_company_name")
	private String fabricatorCompanyName; 
    
    @Column(name = "customer_spec_number")
	private String customerSpecNumber; 
    
    
    
    
    
    
    
    
    
}