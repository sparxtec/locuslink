package com.locuslink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class Item {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String itemId;
  
    @Column(nullable = false)
    private String itemType;

    
    
	public String getItemId() {
		return itemId;
	}	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}	
	public String getItemType() {
		return itemType;
	}	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}