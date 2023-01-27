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
@Table(name="universal_catalog")
public class UniversalCatalog extends Common {

	public UniversalCatalog() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="ucat_pkId", unique=true, nullable = false)
    private int ucatPkId;
  
    @Column(name="universal_catalog_id", nullable = false)
    private String universaCatalogId;

    @Column(name = "asset_type_id", nullable = false)
	private int assetTypeId; 
	
    @Column(name = "asset_number", nullable = false)
	private String assetNumber; 
    
    @Column(name = "asset_name", nullable = false)
	private String assetName; 
    
    @Column(name = "asset_desc", nullable = false)
	private String assetDesc; 
    

}