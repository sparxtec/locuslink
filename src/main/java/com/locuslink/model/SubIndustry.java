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
    @Column(name="sub_industry_pkid")
    private Integer subIndustryPkId;
  
    @Column(name = "industry_pkid")
	private Integer industryPkId; 
    
    @Column(name="uid")
    private String uid;
    
    @Column(name="sub_industry_code")
    private String subIndustryCode;

    @Column(name = "sub_industry_desc")
	private String subIndustryDesc; 
	
	


}