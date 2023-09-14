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
@Table(name="product_type")
public class ProductType extends Common {

	public ProductType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="product_type_pkId", unique=true, nullable = false)
    private int productTypePkId;
  
    @Column(name = "sub_industry_pkid", nullable = false)
	private int subIndustryPkId; 
    
    @Column(name="uid", nullable = false)
    private String uid;
    
    @Column(name="type_code", nullable = false)
    private String productTypeCode;

    @Column(name = "type_desc", nullable = false)
	private String productTypeDesc; 
	
	


}