package com.locuslink.dto;

import lombok.Data;

@Data
public class DashboardFormDTO {

	
	// Assembly Data
	private String assemblyPkid;
	
	
	
	// Attachment Processing
	private String productAttachPkId;
	private boolean isPdf;
	
	
	// Barcode Processing
	private String printBarcode1;
	private String uniqueAssetPkId;
	
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
	
	// 04-25-2023
	// The code in upload PAge 3, loops on all the fields in the uploaded file, string the json together in here, passed to back end.
	// this json goes to the DB in product_attachment in its entirety
	//  KEY fileds from the json are used to create the entries in Unique_asset table
	private String jsonUploadedProductObjectList;
	
	
	// Login, then needed on the header for the PRofile name displayed
	//private String firstName;
	//private String lastNameBusName;
	
	
	// Profile - UI Selection Role Table	
    String selectedUserRoleCode;
	String selectedFirstName;	
	String selectedLastNameBusName;	
	String selectedEmailId;	
	String selectedPhoneNumber;	
	
}
