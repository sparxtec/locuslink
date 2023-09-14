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
@Table(name="product_sub_type")
public class ProductSubType extends Common {

	public ProductSubType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="product_sub_type_pkId", unique=true, nullable = false)
    private int productSubTypePkId;
  
    @Column(name = "product_type_pkid", nullable = false)
	private int productTypePkId; 
    
    @Column(name="uid", nullable = false)
    private String uid;
    
    @Column(name="sub_type_code", nullable = false)
    private String productSubTypeCode;

    @Column(name = "sub_type_desc", nullable = false)
	private String productSubTypeDesc; 
	
	


}