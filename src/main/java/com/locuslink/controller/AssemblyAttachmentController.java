package com.locuslink.controller;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.locuslink.common.SecurityContextManager;
import com.locuslink.dao.AssemblyAttachmentDao;
import com.locuslink.dao.AssemblyDao;
import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.dto.AssemblyDTO;
import com.locuslink.dto.DashboardFormDTO;

/**
 * This is a Spring MVC Controller.
 *
 * @author C.Sparks
 * @since 1.0.0 - June 15, 2024 - Initial version
 */
@Controller
@Service
public class AssemblyAttachmentController {

	private static final Logger logger = Logger.getLogger(AssemblyAttachmentController.class);


    @Value("${app.logout.url}")
    private String appLogoutUrl;

	// 6-2-2022 C.Sparks
    @Autowired
    private SecurityContextManager securityContextManager;
    
    @Autowired
    private AssemblyDao assemblyDao;
    
    @Autowired
    private AssemblyAttachmentDao assemblyAttachmentDao;
    

    
    
	@Autowired
	private AmazonS3Client awsS3Client;

	@Value("${aws.s3.bucketName}")
	private String awsS3BucketName;
							 
	@Value("${file.assembly.staging.fullpath}")
	private String assemblyStagingFullpath;
	
	@Value("${file.assembly.storage.fullpath}")
	private String assemblyStorageFullpath;
	

    

	
	//************************************************************************//
	//*******     ASSEMBLY -  A T T A C H M E N T   D A T A       ************//
	//************************************************************************//
    
	@PostMapping(value = "/initAssemblyAttachments")
	public String initAssemblyAttachments (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
		logger.debug("Starting initAssemblyAttachments()...");

	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);

	   	return "fragments/assembly-attachment-viewer-modal";
	}
	
	
	
	
	/**
	 * 2-21-2024  This is called from the asset detail tab, when a document was clicked on for viewing.
	 * 
	 */
	@PostMapping(value = "/getAttachmentForAssembly")
	public String getAttachmentForAssembly (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
	
		logger.debug("Starting getAttachmentForAssembly()...");


		AssemblyDTO assemblyDTO = assemblyDao.getDtoById(Integer.valueOf(dashboardFormDTO.getAssemblyPkid()));
		
		
		AssemblyAttachmentDTO assemblyAttachmentDTO = assemblyAttachmentDao.getDtoById(Integer.valueOf(dashboardFormDTO.getAssemblyAttachPkId()));
		

		if (assemblyDTO == null) {
			logger.debug("  Error:  Assembly  Lookup failed...");
		}	
		
		if (assemblyAttachmentDTO == null) {
			logger.debug("  Error:  Assembly Attachment Lookup failed...");
		}
				
		// 06-11-2024  the filename is prefixed with pkId to avoid collisions
		String prefixAssemblytPkId = dashboardFormDTO.getAssemblyPkid()+ "_";	
		String fullpathFileName_keyName = assemblyStorageFullpath +  prefixAssemblytPkId + assemblyAttachmentDTO.getFilenameFullpath();	
		
		
        GetObjectRequest request = new GetObjectRequest(
        	awsS3BucketName, 
        	fullpathFileName_keyName ) ;
        	//assemblyAttachment.getFilenameFullpath() );
                
        S3Object s3Object = awsS3Client.getObject(request);
        S3ObjectInputStream s3is = s3Object.getObjectContent();
          
		ByteArrayResource byteArrayResource = null;
	
		
		
		// TODO 6-22-2023
		String encodedPDFBarcdeString = "";
		JSONObject xlsJson = null;
		if (s3Object.getKey().contains("xls")) {
			
			//	uniqueAssetDTO.setPdf(false);				
			//	byteArrayResource = new ByteArrayResource( convertExcelToPDF( s3is ).toByteArray());			
			
		} else if (s3Object.getKey().contains("pdf"))  {
			//uniqueAssetDTO.setPdf(true);
			
			try {
				byteArrayResource = new ByteArrayResource( s3is.readAllBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
		
		
		encodedPDFBarcdeString = Base64.getEncoder().encodeToString(byteArrayResource.getByteArray());	
				
	   	model.addAttribute("encodedPDFBarcdeString", encodedPDFBarcdeString);		   	
	   	model.addAttribute("assemblyAttachPkId", assemblyAttachmentDTO.getAaPkid());		
	   	model.addAttribute("assemblyDTO", assemblyDTO);
	   	model.addAttribute("assemblyAttachmentDTO", assemblyAttachmentDTO);

	   	
	   	return "fragments/assembly-attachment-viewer-modal";
	   	
	}

	
	
	
	
    
//	@PostMapping(value = "/deleteAssetAttachment")
//	public String deleteAssetAttachment (@ModelAttribute(name = "dashboardFormDTO") DashboardFormDTO dashboardFormDTO,	Model model, HttpSession session) {
//		
//		logger.debug("Starting deleteAssetAttachment()...");
//
//		
//		// TODO Delete the clicked on ROW from AWS and the Database
//		ProductAttachment productAttachment = productAttachmentDao.getById(Integer.valueOf(dashboardFormDTO.getProductAttachPkId()));
//		if (productAttachment == null) {
//			logger.debug("  Error:  Product Attachment Lookup failed...");
//		}
//		
//		// AWS Remove the attachment from the Storge Area			
//        awsS3Client.deleteObject(new DeleteObjectRequest(awsS3BucketName, productAttachment.getFilenameFullpath()));
//        logger.debug("     remove the AWS STORAGE file successfully.");	 
//        
//        // Database
//        productAttachmentDao.delete(productAttachment);
//		
//        
//	   	model.addAttribute("dashboardFormDTO", dashboardFormDTO);
//
//		//return "fragments/modal_attachment_list";
//	   	return "fragments/asset-attachment-viewer-modal";
//	   	
//	}
	
	
	
	
	
	
	
	
//	private ByteArrayOutputStream convertExcelToPDF( S3ObjectInputStream s3is ) {	
//	
//		int nbrColsInExcelSheet = 3;
//		
//	    Document document = new Document();
//	    PdfPTable my_table = new PdfPTable(nbrColsInExcelSheet);
//	    PdfPCell table_cell;
//
//	    Row row = null;		 		           
//	    boolean notFinished = true;  
//	   	   
//	    
//	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//	    
//	    try {	    	
//			XSSFWorkbook workbook = new XSSFWorkbook(s3is);					
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			Iterator<Row> rowIterator = sheet.iterator();
//					    
//		    //PdfWriter.getInstance(document, new FileOutputStream("Excel2PDF_Output.pdf"));
//			
//			PdfWriter.getInstance(document, byteArrayOutputStream);
//			
//		    document.open();
//	    	
//	    	while (rowIterator.hasNext() && notFinished) {
//				
//				row = rowIterator.next();												
//				int len = nbrColsInExcelSheet;
//
//				
////				// 6-27-2023  TODO Remove blank rows
////				if ((row.getCell(0) != null &&  row.getCell(0).toString().length() > 0) ||
////					(row.getCell(1) != null &&  row.getCell(1).toString().length() > 0) ||
////					(row.getCell(2) != null &&  row.getCell(2).toString().length() > 0)) {
////						break;				
////				}
//				
//				
//				// Loop thru the cells on a row
//				if ( row.getCell(0) != null ) {						
//					String rowCellValue = "";
//					
//					CellStyle cellStyle = null;
//					//XSSFCellStyle xssfCellStyle = null;	
//					
//					for ( int i = 0; len > i ; i++) {		
//						
//						// 6-22-2023
//						if (row.getCell(i) != null ) {
//							rowCellValue = row.getCell(i).toString();							
//							cellStyle = row.getCell(i).getCellStyle();							
//							//xssfCellStyle = (XSSFCellStyle) row.getCell(i).getCellStyle();																				
//						}	else {
//							rowCellValue = "";
//						}												
//						System.out.print(rowCellValue);									
//						if(len-1 == i) 	{
//						} else {
//							System.out.print(",");																																									
//						}	
//								
//						//logger.debug(" cell Fore Color ->:" + cellStyle.getFillForegroundColor() );
//							
//						//cellStyle.getFillBackgroundColor()
//						table_cell=new PdfPCell(new Phrase(  rowCellValue ));
//						
////						// trying hack to set some colors
////						if ( cellStyle.getFillForegroundColor() == 0) {
////							table_cell.setBackgroundColor( Color.lightGray );
////						}
//																		
//                        my_table.addCell(table_cell);                       
//						
//					}	
//					System.out.println();
//		
//				}
//			}
//		        	
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		
//		}
//	    
//	    
//	    
//	    //Finally add the table to PDF document
//	    try {
//	    	document.add(my_table);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}                       
//	    document.close();  
//	    
//	    
//	return byteArrayOutputStream;
//}
	
	

	
}