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
@Table(name="asset_type")
public class AssetType extends Common {

	public AssetType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="asset_type_id", unique=true, nullable = false)
    private int assetTypeId;
  
    @Column(name="type_code", nullable = false)
    private String type_code;

    @Column(name = "type_desc", nullable = false)
	private String type_desc; 
	
	


}