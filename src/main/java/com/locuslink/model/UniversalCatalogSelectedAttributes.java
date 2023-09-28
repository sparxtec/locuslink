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
    @Column(name="ucat_selected_attributes__pkid", unique=true, nullable = false)
    private int ucatSelectedAttributesPkId;
  
    @Column(name = "ucat_pkid", nullable = false)
	private int ucatPkId; 
    
    @Column(name="uid_pal_pkId", unique=true, nullable = false)
    private int uidPalPkId;
    

}