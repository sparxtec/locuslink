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
@Table(name="catalog_def_attr_list")
public class CatalogDefAttributeList extends Common {

	public CatalogDefAttributeList() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="cdal_pkid")
    private Integer cdalPkId;
  
    @Column(name = "cdugt_pkid")
	private Integer cdugtPkId; 
        
    @Column(name = "uid_attr_seq")
	private int uidAttrSeq; 
	
    @Column(name = "uid_attr_list_name")
	private String uidAttrListName; 
    
    @Column(name = "uid_attributes_json")
	private String uidAttributesJson; 

}