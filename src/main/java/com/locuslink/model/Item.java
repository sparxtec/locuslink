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


@Entity
@Table(name="Item")
public class Item extends Common {

	public Item() {
	}

	private static final long serialVersionUID = 1L;

	@Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="Item_Id", unique=true, nullable = false)
    private String itemId;
  
    @Column(name="Item_Type", nullable = false)
    private String itemType;

    @Column(name = "Item_Num", nullable = false)
	private String itemNum; 
	
	@Column(name = "Heat_Serial_Num")
	private String heatSerialNum;
	
	@Column(name = "Heat_Serial_Ind")
	private String heatSerialInd;

	@Column(name = "Item_Desc")
	private String itemDesc;  
	
	@Column(name = "Manufacturer", nullable = false)
	private String manufacturer; 

	@Column(name = "Mfg_Item_Desc")
	private String mfgItemDesc; 
			
	@Column(name = "Unit_Of_Measure")
	private String unitOfMeasure; 

	@Column(name = "Pipe_Flag")
	private String pipeFlag;
	
	@Column(name = "Traceability_Ind")
	private String traceabilityInd;
	
	@Column(name = "Cutsheet_Flag")
	private String cutsheetFlag;
	
	@Column(name = "Order_Qty")
	private int orderQty;
	
	@Column(name = "Model_Num")
	private String modelNum;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getHeatSerialNum() {
		return heatSerialNum;
	}

	public void setHeatSerialNum(String heatSerialNum) {
		this.heatSerialNum = heatSerialNum;
	}

	public String getHeatSerialInd() {
		return heatSerialInd;
	}

	public void setHeatSerialInd(String heatSerialInd) {
		this.heatSerialInd = heatSerialInd;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMfgItemDesc() {
		return mfgItemDesc;
	}

	public void setMfgItemDesc(String mfgItemDesc) {
		this.mfgItemDesc = mfgItemDesc;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getPipeFlag() {
		return pipeFlag;
	}

	public void setPipeFlag(String pipeFlag) {
		this.pipeFlag = pipeFlag;
	}

	public String getTraceabilityInd() {
		return traceabilityInd;
	}

	public void setTraceabilityInd(String traceabilityInd) {
		this.traceabilityInd = traceabilityInd;
	}

	public String getCutsheetFlag() {
		return cutsheetFlag;
	}

	public void setCutsheetFlag(String cutsheetFlag) {
		this.cutsheetFlag = cutsheetFlag;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}