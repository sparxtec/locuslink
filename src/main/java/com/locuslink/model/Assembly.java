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
    
    @Column(name = "jobDescription")
	private String jobDescription; 
    
    @Column(name = "location")
	private String location; 
    
    @Column(name = "station_number")
	private String stationNumber; 
    
    @Column(name = "trace_number")
	private String traceNumber; 
    
}