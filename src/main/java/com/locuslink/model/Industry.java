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
    @Column(name="industry_pkid")
    private Integer industryPkId;
  
    @Column(name="uid")
    private String uid;
    
    @Column(name="industry_code")
    private String industryCode;

    @Column(name = "industry_desc")
	private String industryDesc; 
	
	


}