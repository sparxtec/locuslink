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
@Table(name="material_item_type")
public class MaterialItemType extends Common {

	public MaterialItemType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="mat_item_type_id", unique=true, nullable = false)
    private int matItemTypeId;
  
    @Column(name="type_code", nullable = false)
    private String type_code;

    @Column(name = "type_desc", nullable = false)
	private String type_desc; 
	
	


}