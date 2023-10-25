package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="universal_cat_selected_attributes")
public class UniversalCatalogSelectedAttributes extends Common {

	public UniversalCatalogSelectedAttributes() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="ucat_selected_attributes__pkid")
    private Integer ucatSelectedAttributesPkId;
  
    @Column(name = "ucat_pkid")
	private Integer ucatPkId; 

    @Column(name="cdugt_pkid")
    private Integer cdugtPkId;
    
    @Column(name="selected_attribute_value")
    private String selectedAttributeValue;
    
    
    

}