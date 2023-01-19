package com.locuslink.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {
	
    public static final String BEAN_NAME = "fileStorageService";
    

    	
    	
    
	public void store(MultipartFile file, String jobFilePath );
	
//	public void storeAtGivenTotalPath(MultipartFile file, String jobFilePath, String filename);
//	
//	public Resource loadFile(String filename);
//	
//	public void deleteAll();
//	
//	public void init();
	
	
    // 4-9-2019
    //public boolean movePDFfromNFStoLocalDir(String mtrNFSPath, String pdfViewerPath) ;
	
	
//	public List<DocUploadFormDTO> getFileList();
//	
//	public List <ItemMTRDTO> getMRCControlFileList_2();
//
//	void processFileType1(Path workPath, List <DocUploadFormDTO> listDocUploadFormDTO);
//
//	void processFileType2(Path workPath, List<DocUploadFormDTO> listDocUploadFormDTO);
//	
//	public void deleteFile(String filename);
//	
//	public void deletePDFFromInprogressFolder(String filename);
//
//	public void  moveToFinish(String jobFilePath, String initialFileName, String finishedFileName);
//	
//	public void copyBarcode(String jobFilePath, String initialFileName, String finishedFileName, String pipeFlag);
//
//	public void moveMRCToFinish(String jobFilePath, String initialFileName, String finishedFileName);
//	
//	public String getFinishedFilePath(String jobFilePath);
//	
//	public void processCreateBarcodePDF(String heatBarcodeforPDF, String newBarcodeZPL, String filePath, String fileName, boolean twoBarcodes, int qDocReqLineId, String isItem) throws IOException;
//
//	public void processCreateTraceIDReturnPDF(String newBarcodeZPL, String filePath, String fileName) throws IOException;
//
//	public void writeMRCErrorFile(String response, String errorFile) throws IOException;
//	
//	public void sftpFileUploadMRC(String errorFile);
//
//	public void moveControlFileToError(String jobFilePath, String fileName);
//	
//	//S. Toppi 4/10/2020
//
//	public void moveTDWControlFileToError(String jobFilePath, String fileName);
//
//
////S.Toppi 3/24/2020
//	public void writeTDWErrorFile(String response, String errorFile) throws IOException;
//
//	public void moveTDWToFinish(String jobFilePath, String initialFileName, String finishedFileName);
//
//	public List<ItemMTRDTO> getTDWControlFileList_2();
//	
//	public void sftpFileUploadTDW(String errorFile);







	
	
}
