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
@Table(name="product_template")
public class ProductTemplate extends Common {

	public ProductTemplate() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="product_template_pkId", unique=true, nullable = false)
    private int productTemplatePkId;
  
    @Column(name="ucat_pkId", unique=true, nullable = false)
    private int ucatPkId;

    @Column(name = "product_template_desc", nullable = false)
	private String productTemplateDesc; 
	
    @Column(name = "unique_attributes_json", nullable = false)
	private String unique_attributes_json; 
    
    @Column(name = "additional_attributes_json", nullable = false)
	private String additional_attributes_json; 


}