package com.locuslink.dto;

public class DashboardFormDTO {


	private String fileName;
	private String responseText ="";
	private String selectedDocType;
	
	
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
		
	
}
