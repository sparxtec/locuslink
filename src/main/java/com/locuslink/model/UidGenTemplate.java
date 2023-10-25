//package com.locuslink.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name="uid_gen_template")
//public class UidGenTemplate extends Common {
//
//	public UidGenTemplate() {
//	}
//
//	private static final long serialVersionUID = 1L;
//
//	@Id 
//    @Column(name="uid_gen_template_pkid", unique=true, nullable = false)
//    private int uidGenTemplatePkId;
//  
//    @Column(name = "industry_pkid", nullable = false)
//	private int industryPkId; 
//    
//    @Column(name = "sub_industry_pkid", nullable = false)
//	private int subIndustryPkId; 
//    
//    @Column(name="product_type_pkid", nullable = false)
//    private int productTypePkId;
//
//    @Column(name="product_sub_type_pkid" , nullable = false)
//    private int productSubTypePkId;
//            
//    @Column(name = "uid_template_length", nullable = false)
//	private int uidGenTemplateLength; 
//    
//    @Column(name = "uid_gen_formatted_template", nullable = false)
//	private String uidGenFormattedTemplate; 
//	
//}