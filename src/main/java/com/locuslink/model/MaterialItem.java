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
@Table(name="Material_Item")
public class MaterialItem extends Common {

	public MaterialItem() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="mat_item_id", unique=true, nullable = false)
    private int matItemId;
  
	
    @Column(name = "mat_item_type_Id", nullable = false)
	private int matItemTypeId; 
    
    @Column(name = "mat_item_num", nullable = false)
  	private String matItemNum; 
    
    @Column(name="manufacturer_id", nullable = false)
    private int manufacturerId;
      
    @Column(name = "model_num", nullable = false)
	private String modelNum; 
  
    @Column(name = "item_desc", nullable = false)
	private String itemDesc; 
	

}