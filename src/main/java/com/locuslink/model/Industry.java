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
@Table(name="industry")
public class Industry extends Common {

	public Industry() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="industry_pkid", unique=true, nullable = false)
    private int industryPkId;
  
    @Column(name="uid", nullable = false)
    private String uid;
    
    @Column(name="industry_code", nullable = false)
    private String industryCode;

    @Column(name = "industry_desc", nullable = false)
	private String industryDesc; 
	
	


}