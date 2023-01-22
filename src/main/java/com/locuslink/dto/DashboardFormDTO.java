package com.locuslink.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardFormDTO {


	private String fileName;
	private String responseText ="";
	private String selectedDocType;
	
	
	
	
	// crap test from docupload dto
	
	private String purchaseOrderNumber;
	private String lookupWorkOrderNumber;
	private int poItemNum;
	private int poLineNum;
	private String poItemDesc;
	private String fileType;
	private String filePath;
	private String heatNumber;
	private String serialNumber;
	private String Mfg;
	private String mfgDesc;
	private String override;
	private String selectedContractorType;
	private String inputSearchNumber;
	private String poNum;
	private String itemNumber;
	
	
	
	// Upload Page 3
	private List <String[]> dataTableArray = new ArrayList<String[]>();
	
	
	
	
	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public String getSelectedDocType() {
		return selectedDocType;
	}
	public void setSelectedDocType(String selectedDocType) {
		this.selectedDocType = selectedDocType;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getLookupWorkOrderNumber() {
		return lookupWorkOrderNumber;
	}
	public void setLookupWorkOrderNumber(String lookupWorkOrderNumber) {
		this.lookupWorkOrderNumber = lookupWorkOrderNumber;
	}
	public int getPoItemNum() {
		return poItemNum;
	}
	public void setPoItemNum(int poItemNum) {
		this.poItemNum = poItemNum;
	}
	public int getPoLineNum() {
		return poLineNum;
	}
	public void setPoLineNum(int poLineNum) {
		this.poLineNum = poLineNum;
	}
	public String getPoItemDesc() {
		return poItemDesc;
	}
	public void setPoItemDesc(String poItemDesc) {
		this.poItemDesc = poItemDesc;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getHeatNumber() {
		return heatNumber;
	}
	public void setHeatNumber(String heatNumber) {
		this.heatNumber = heatNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMfg() {
		return Mfg;
	}
	public void setMfg(String mfg) {
		Mfg = mfg;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getOverride() {
		return override;
	}
	public void setOverride(String override) {
		this.override = override;
	}
	public String getSelectedContractorType() {
		return selectedContractorType;
	}
	public void setSelectedContractorType(String selectedContractorType) {
		this.selectedContractorType = selectedContractorType;
	}
	public String getInputSearchNumber() {
		return inputSearchNumber;
	}
	public void setInputSearchNumber(String inputSearchNumber) {
		this.inputSearchNumber = inputSearchNumber;
	}
	public String getPoNum() {
		return poNum;
	}
	public void setPoNum(String poNum) {
		this.poNum = poNum;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
		
	
}
