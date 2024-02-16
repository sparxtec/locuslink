package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="product_attribute")
public class ProductAttribute extends Common {

	public ProductAttribute() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_attr_pkid", unique=true, nullable = false)
    private Integer productAttrPkId;
  
    @Column(name="ucat_pkid", unique=true, nullable = false)
    private Integer ucatPkId;

    
    @Column(name = "attributes_json", nullable = false)
	private String attributesJson; 
    
//    @Column(name = "unique_attributes_json", nullable = false)
//	private String uniqueAttributesJson; 
//    @Column(name = "additional_attributes_json", nullable = false)
//	private String additionalAttributesJson; 


}