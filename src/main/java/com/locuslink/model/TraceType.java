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
@Table(name="traceability_type")
public class TraceType extends Common {

	public TraceType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="traceability_type_pkid", unique=true, nullable = false)
    private int traceTypePkId;
  
    @Column(name="traceability_type_code", nullable = false)
    private String traceTypeCode;

    @Column(name = "traceability_type_desc", nullable = false)
	private String traceTypeDesc; 
	
	


}