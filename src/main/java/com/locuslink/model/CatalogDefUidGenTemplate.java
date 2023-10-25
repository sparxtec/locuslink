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
@Table(name="catalog_def_uid_gen_tpl")
public class CatalogDefUidGenTemplate extends Common {

	public CatalogDefUidGenTemplate() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="cdugt_pkid")
    private Integer cdugtPkId;
  
    @Column(name = "industry_pkid")
	private Integer industryPkId; 
    
    @Column(name = "sub_industry_pkid")
	private Integer subIndustryPkId; 
    
    @Column(name="product_type_pkid")
    private Integer productTypePkId;

    @Column(name="product_sub_type_pkid" )
    private Integer productSubTypePkId;
            
    
    @Column(name = "uid_template_len")
	private int uidTemplateLen; 
    
    @Column(name = "uid_template")
	private String uidTemplate; 
	
}