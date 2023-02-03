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
@Table(name="document_type")
public class DocumentType extends Common {

	public DocumentType() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @Column(name="doc_type_pkId", unique=true, nullable = false)
    private int docTypePkId;
  
    @Column(name="doc_type_code", nullable = false)
    private String docTypeCode;

    @Column(name = "doc_type_desc", nullable = false)
	private String docTypeDesc; 
	
	


}