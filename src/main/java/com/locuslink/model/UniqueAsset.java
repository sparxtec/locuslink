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
@Table(name="unique_asset")
public class UniqueAsset extends Common {

	public UniqueAsset() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="unique_asset_pkid", unique=true, nullable = false)
    private int uniqueAssetPkId;
  
    @Column(name="unique_asset_id", nullable = false)
    private String uniqueAssetId;

    @Column(name = "ucat_pkid", nullable = false)
	private int ucatPkId; 

    @Column(name = "manufacturer_pkid", nullable = false)
	private int manufacturerPkId;     
    
    @Column(name = "traceability_type_pkid", nullable = false)
	private int traceTypePkId; 
    
    @Column(name = "customer_pkid", nullable = false)
	private int customerPkId; 
    
    @Column(name = "trace_code", nullable = false)
	private String traceCode; 
    

}