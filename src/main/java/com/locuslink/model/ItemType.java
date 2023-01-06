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
@Table(name="item_type")
public class ItemType extends Common {

	public ItemType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="item_type_id", unique=true, nullable = false)
    private int itemTypeId;
  
    @Column(name="item_type_code", nullable = false)
    private String item_type_code;

    @Column(name = "type_desc", nullable = false)
	private String type_desc; 
	
	


}