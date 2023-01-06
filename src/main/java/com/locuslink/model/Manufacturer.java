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
@Table(name="manufacturer")
public class Manufacturer extends Common {

	public Manufacturer() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="manufacturer_id", unique=true, nullable = false)
    private int manufacturerId;
  
    @Column(name="name", nullable = false)
    private String name;

   

}