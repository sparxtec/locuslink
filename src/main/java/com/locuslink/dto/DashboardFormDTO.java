package com.locuslink.dto;

import java.util.ArrayList;
import java.util.List;

import com.locuslink.dto.uploadedFileObjects.WireObject;

import lombok.Data;

@Data
public class DashboardFormDTO {

	private String fileName;
	private String responseText ="";
	private String selectedDocType;
				
	// from docupload dto	
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
	//private List <String[]> dataTableArray = new ArrayList<String[]>();
		
	private List <WireObject> dataTableArray = new ArrayList<WireObject>();
	
	
	
}
