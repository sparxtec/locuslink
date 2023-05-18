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
@Table(name="product_attachment")
public class ProductAttachment extends Common {

	public ProductAttachment() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="product_attach_pkId", unique=true, nullable = false)
    private int productAttachPkId;
  
    @Column(name="unique_asset_pkid", unique=true, nullable = false)
    private int uniqueAssetPkId;

    @Column(name = "doc_type_pkId", nullable = false)
	private int docTypePkId; 
	 
    @Column(name = "filename_fullpath", nullable = false)
	private String filenameFullpath; 

    @Column(name = "attributes_json", nullable = false)
	private String attributesJson; 
    
    
}