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
    @Column(name="product_sub_type_pkId")
    private Integer productSubTypePkId;
  
    @Column(name = "product_type_pkid")
	private Integer productTypePkId; 
    
    @Column(name="uid")
    private String uid;
    
    @Column(name="sub_type_code")
    private String productSubTypeCode;

    @Column(name = "sub_type_desc")
	private String productSubTypeDesc; 
	
	


}