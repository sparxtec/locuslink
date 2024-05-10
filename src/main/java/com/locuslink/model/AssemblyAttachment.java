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
@Table(name="assembly_attachment")
public class AssemblyAttachment extends Common {

	public AssemblyAttachment() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="aa_pkid", unique=true, nullable = false)
    private Integer aaPkid;
  
    @Column(name="assembly_pkid", nullable = false)
    private int assemblyPkid;

    @Column(name="doc_type_pkid", nullable = false)
    private int docTypePkid;
    
    @Column(name = "filename_fullpath", nullable = false)
	private String filenameFullpath; 
    
    @Column(name = "attributes_json")
	private String attributesJson; 
    
    
    
}