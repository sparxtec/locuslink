package com.locuslink.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class Common implements Serializable  {

   private static final long serialVersionUID = 1L;

   @Column(name = "ADD_BY", nullable=false)
   private String addBy; 
   
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "ADD_TS", nullable=false)
   @CreationTimestamp
   private Date addTs; 
   
   @Column(name = "UPDATE_BY", nullable=false)
   private String updateBy; 
   
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "UPDATE_TS", nullable=false)
   @UpdateTimestamp
   private Date updateTs;

   
   
   
	public String getAddBy() {
		return addBy;
	}
	
	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}
	
	public Date getAddTs() {
		return addTs;
	}
	
	public void setAddTs(Date addTs) {
		this.addTs = addTs;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public Date getUpdateTs() {
		return updateTs;
	}
	
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
   
   
   
   
   
}