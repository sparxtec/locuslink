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
@Table(name="uid_product_attribute_list")
public class UidProductAttributeList extends Common {

	public UidProductAttributeList() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="uid_pal_pkId", unique=true, nullable = false)
    private int uidPalPkId;
  
    @Column(name = "industry_pkid", nullable = false)
	private int industryPkId; 
    
    @Column(name = "sub_industry_pkid", nullable = false)
	private int subIndustryPkId; 
    
    @Column(name="product_type_pkid", nullable = false)
    private int productTypePkId;

    @Column(name="product_sub_type_pkid", nullable = false)
    private int productSubTypePkId;
    
    @Column(name = "uid_gen_seq", nullable = false)
	private int uidGenSeq; 
	
    @Column(name = "uid_pal_name", nullable = false)
	private String uidPalName; 

    @Column(name = "uid_pal_attributes_json", nullable = false)
	private String uidPalAttributesJson; 

}