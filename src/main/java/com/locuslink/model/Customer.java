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
@Table(name="customer")
public class Customer extends Common {

	public Customer() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="customer_pkid", unique=true, nullable = false)
    private int customerPkId;
  
    @Column(name="customer_type_code", nullable = false)
    private String customerTypeCode;

    @Column(name="customer_name", nullable = false)
    private String customerName;
    
    @Column(name="active_flag", nullable = false)
    private String activeFlag;
 

}