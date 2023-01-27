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
@Table(name="asset_template")
public class AssetTemplate extends Common {

	public AssetTemplate() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="asset_template_id", unique=true, nullable = false)
    private int asset_template_id;
  
    @Column(name="asset_type_id", nullable = false)
    private int asset_type_id;

    @Column(name = "asset_template_desc", nullable = false)
	private String asset_template_desc; 
	
    @Column(name = "unique_attributes_json", nullable = false)
	private String unique_attributes_json; 
    
    @Column(name = "additional_attributes_json", nullable = false)
	private String additional_attributes_json; 


}