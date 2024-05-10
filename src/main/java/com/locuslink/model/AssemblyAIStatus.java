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
@Table(name="assembly_ai_status")
public class AssemblyAIStatus extends Common {

	public AssemblyAIStatus() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="aais_pkid", unique=true, nullable = false)
    private Integer aaisPkid;
  
    @Column(name="aa_pkid", nullable = false)
    private int aaPkid;

    @Column(name = "statusType", nullable = false)
	private String statusType; 
    
    @Column(name = "status", nullable = false)
	private String status; 
    
    @Column(name = "statusDescription", nullable = false)
	private String statusDescription; 
    
}