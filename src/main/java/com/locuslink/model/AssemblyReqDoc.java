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
@Table(name="assembly_req_docs")
public class AssemblyReqDoc extends Common {

	public AssemblyReqDoc() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="ard_pkid", unique=true, nullable = false)
    private Integer ardPkid;
  	  
    @Column(name="assembly_pkid", nullable = false)
    private int assemblyPkid;
    
    @Column(name="doc_type_pkid", nullable = false)
    private int docTypePkid;

    @Column(name = "doc_description")
	private String docDescription; 
    
    
    
}