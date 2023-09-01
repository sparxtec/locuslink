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
@Table(name="sub_industry")
public class SubIndustry extends Common {

	public SubIndustry() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="sub_industry_pkid", unique=true, nullable = false)
    private int subIndustryPkId;
  
    @Column(name = "industry_pkid", nullable = false)
	private int industryPkId; 
    
    @Column(name="uid", nullable = false)
    private String uid;
    
    @Column(name="sub_industry_code", nullable = false)
    private String subIndustryCode;

    @Column(name = "sub_industry_desc", nullable = false)
	private String subIndustryDesc; 
	
	


}