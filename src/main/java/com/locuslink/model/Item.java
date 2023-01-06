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
@Table(name="Item")
public class Item extends Common {

	public Item() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="Item_Id", unique=true, nullable = false)
    private int itemId;
  
    @Column(name="manufacturer_id", nullable = false)
    private int manufacturerId;

    @Column(name = "item_attr_id", nullable = false)
	private int itemAttrId; 
	
    @Column(name = "item_type_Id", nullable = false)
	private int itemTypeId; 

    @Column(name = "item_num", nullable = false)
	private String itemNum; 
           
    @Column(name = "model_num", nullable = false)
	private String modelNum; 
  
    @Column(name = "item_desc", nullable = false)
	private String itemDesc; 
	
    

}