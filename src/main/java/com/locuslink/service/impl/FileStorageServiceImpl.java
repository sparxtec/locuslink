package com.locuslink.service.impl;

import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.locuslink.service.FileStorageService;


//@Configuration
@Service(FileStorageService.BEAN_NAME)
public class FileStorageServiceImpl implements FileStorageService{
	
	private static final Logger logger = Logger.getLogger(FileStorageServiceImpl.class);
	
	@Autowired
	private Environment env;
	
//	@Autowired
//	@Qualifier(QDocReqLineDao2.BEAN_NAME)
//	private QDocReqLineDao2 qDocReqLineDao2;
//	
//	@Autowired
//	@Qualifier(ItemMTRDao.BEAN_NAME)
//	private ItemMTRDao itemMTRDao;
	
	
	private Path rootLocation;
	

//	public FileStorageServiceImpl() {
//	}
 

	
	
	@Override
    public void store(MultipartFile file, String jobFilePath) {
		
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        
//        try {
//            if (file.isEmpty()) {
//                throw new StorageException("Failed to store empty file " + filename);
//            }
//            if (filename.contains("..")) {
//                throw new StorageException(
//                     "Cannot store file with relative path outside current directory "  + filename);
//            }
//                      	
//      		// C.Sparks 12-7-2018 
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//        	String inProgressPath = env.getProperty("spring.upload.nfs.mtr.storage.inprogress");
//        	try {
//        		logger.debug (" Creating JOB directory ->: " + fileDirectoryPath + inProgressPath + jobFilePath) ; 
//        		this.rootLocation= Files.createDirectories(Paths.get(fileDirectoryPath + inProgressPath + jobFilePath) );        		
//			} catch (Exception e) {
//				logger.debug (" Error creating JOB directory ->: " + fileDirectoryPath) ;       
//				e.printStackTrace();
//			}            	
//             
//        	logger.debug (" Saving file ->: " + filename);          
//            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),  StandardCopyOption.REPLACE_EXISTING);
//                    
//        } catch (IOException e) {
//            throw new StorageException("Failed to store file " + filename, e);
//        }
    }
	
	
//	public void storeAtGivenTotalPath(MultipartFile file, String jobFilePath, String filename) {
//		
//        try {
//        	
//            if (file.isEmpty()) {
//                throw new StorageException("Failed to store empty file " + filename);
//            }
//            
//            if (filename.contains("..")) {
//                throw new StorageException("Cannot store file with relative path outside current directory "  + filename);
//            }
//                      	
//        	try {
//        		logger.debug (" JOB directory ->: " + jobFilePath); 
//        		this.rootLocation = Paths.get(jobFilePath);
//			} catch (Exception e) {
//				logger.debug (" Error creating PATH ->: " + jobFilePath);       
//				e.printStackTrace();
//			}           	
//             
//        	logger.debug (" Saving file ->: " + filename);          
//            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//                    
//        } catch (IOException e) {
//            throw new StorageException("Failed to store file " + filename, e);
//        }
//    }
	
//	@Override
//    public Resource loadFile(String filename) {
//        try {
//            Path file = rootLocation.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if(resource.exists() || resource.isReadable()) {
//                return resource;
//            }else{
//            	throw new RuntimeException("FAIL!");
//            }
//        } catch (MalformedURLException e) {
//        	throw new RuntimeException("Error! -> message = " + e.getMessage());
//        }
//    }

//	@Override
//  public boolean movePDFfromNFStoLocalDir(String mtrNFSPath, String pdfViewerPath ) {
//		
//      File pdfFileOnNFS = new File (mtrNFSPath);
//      File pdfFileViewerDir = new File (pdfViewerPath);
//		
//      try {
//          if (pdfFileOnNFS.exists()) {
//          	// this should not happen
//          	logger.error("Error ->: could not find MTR file on the NFS drive, " + mtrNFSPath) ;
//              return false;
//          }
//        	            
//      	logger.debug (" Moving PDF file to pdfFileViewerDir");          
//      	FileUtils.copyFileToDirectory( pdfFileOnNFS,  pdfFileViewerDir );        	
//                  
//      } catch (IOException e) {
//      	logger.error("Error ->: failed to copy file from : " + pdfFileOnNFS + "  to:" + pdfFileViewerDir) ;
//          return false;
//      }
//      
//      logger.debug(" Success ->: copied file from : " + pdfFileOnNFS + "  to:" + pdfFileViewerDir) ;
//      return true;
//  }
//	
//	
//	@Override
//    public String getFinishedFilePath(String jobFilePath) {
//		
//		
//		String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//		String finishedFilePath = finishedPath + jobFilePath;
//		
//		
//		return finishedFilePath.trim();
//    }
//	
//	
//	
//	@Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(rootLocation.toFile());
//    }
//
//	
//	@Override
//    public void deleteFile(String filename) {
//
//		try {
//			
//			FileSystemUtils.deleteRecursively(this.loadFile(filename).getFile());
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//        
//    }
//	
//	@Override
//    public void deletePDFFromInprogressFolder(String filename) {
//		
//		try {
//
//			File fileToBeDeletedWithFullPath = this.loadFile(filename).getFile();
//			
//			logger.debug("   fileToBeDeletedWithFullPath.toPath() = " + fileToBeDeletedWithFullPath.toPath());
//			Files.deleteIfExists(fileToBeDeletedWithFullPath.toPath()); 
//			
//		} catch (IOException e) {
//			logger.debug("   deletePDFFromInprogressFolder() : exception ");
//			e.printStackTrace();
//		}
//		
//        
//    }
//	
//	
//	@Override
//    public void init() {
//        try {
//            Files.createDirectory(rootLocation);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize storage!");
//        }
//    }
//	
//	
//	@Override
//	public List <DocUploadFormDTO> getFileList () {
//        try {
//
//        	 DocUploadFormDTO docUploadFormDTO = new DocUploadFormDTO();
//        	 
//        	 List <DocUploadFormDTO> listDocUploadFormDTO = new ArrayList<DocUploadFormDTO> ();
//        	
//             Stream <Path> filenameList =  Files.list(this.rootLocation).filter(Files::isRegularFile);
//
//             for (Path workPath:  (Iterable<Path>) () -> filenameList.iterator() ) {
//            	 
//            	 logger.debug("   getFileName ->: " + workPath.getFileName().toString());
//
//            	 docUploadFormDTO = new DocUploadFormDTO();            	             	 
//            	 docUploadFormDTO.setFileName(workPath.getFileName().toString());            	 
//            	 docUploadFormDTO.setFilePath(workPath.toFile().getPath());
//            	 
//            	 String splitFileName = workPath.getFileName().toString();
//            	 
//            	 if(splitFileName.toLowerCase().contains("heat;")) {
//            		 processFileType1(workPath, listDocUploadFormDTO);            		             		 
//            	 } else if (splitFileName.toLowerCase().contains("_")) {
//            		 processFileType2(workPath, listDocUploadFormDTO);            		 
//            	 } else {
//            		 logger.debug("Invalid File - but we can continue for now testing.");
//            		 
//            		 docUploadFormDTO.setHeatNumber("");
//            		 docUploadFormDTO.setSerialNumber("");
//            		 docUploadFormDTO.setMfg("");
//            		 docUploadFormDTO.setMfgDesc("");
//            		 docUploadFormDTO.setPoItemNum(666);
//            		 docUploadFormDTO.setPoLineNum(123456); //nullable
//            		 docUploadFormDTO.setPoItemDesc("tbd");
//            		 
//            		 listDocUploadFormDTO.add(docUploadFormDTO);
//            		 
//            	 }
//            			 
//            	 
//             }
//             
//            return listDocUploadFormDTO;
//        }
//        catch (IOException e) {
//        	throw new RuntimeException("\"Failed to read stored file");
//        }
//	}
//	
//	@Override
//	public List <ItemMTRDTO> getMRCControlFileList_2() {
//		
//    	List<ItemMTRDTO> listItemMTRDTO = new ArrayList<ItemMTRDTO> ();
//    	 
//        try {
//        	
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String mrcFileDrop = env.getProperty("spring.upload.nfs.mtr.filedrop");
//			
//			ItemMTRDTO itemMTRDTO = new ItemMTRDTO();
//        	            	
//             Stream <Path> filenameList =  Files.list(Paths.get(fileDirectoryPath + mrcFileDrop)).filter(Files::isRegularFile);
//
//             for (Path workPath:  (Iterable<Path>) () -> filenameList.iterator() ) {
//            	 
//            	 logger.debug("   getFileName ->: " + workPath.getFileName().toString());
//            	 if(workPath.getFileName().toString().contains("control_")) {
//
//
//            		 itemMTRDTO = new ItemMTRDTO();            	             	 
//            		 itemMTRDTO.setFileName(workPath.getFileName().toString());            	 
//            		 itemMTRDTO.setFilePath(workPath.toFile().getPath());
//        		 
//            		 listItemMTRDTO.add(itemMTRDTO);
//
//            		 
//            	 } else {
//            		 logger.debug("MTR File");
//            	 }
//            	 
//             }
//             
//            return listItemMTRDTO;
//            
//        } catch (IOException e) {
//        	// C.Sparks 6-3-2020 This is not an error, so stop throwing one        	
//        	//throw new RuntimeException("\"Failed to read stored file");
//        	logger.error("Warning. Tried to read MTR file, and nothing was found. Peace out.");
//        	return listItemMTRDTO;
//        }
//	}
//	
//	//S.Toppi 4/7/2020
//	@Override
//	public List<ItemMTRDTO> getTDWControlFileList_2() {
//        try {
//        	
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String tdwFileDrop = env.getProperty("spring.upload.nfs.TDW.filedrop");
//			
//			ItemMTRDTO itemMTRDTO = new ItemMTRDTO();
//        	 
//        	 List<ItemMTRDTO> listItemMTRDTO = new ArrayList<ItemMTRDTO> ();
//        	
//             Stream <Path> filenameList =  Files.list(Paths.get(fileDirectoryPath + tdwFileDrop)).filter(Files::isRegularFile);
//
//             for (Path workPath:  (Iterable<Path>) () -> filenameList.iterator() ) {
//            	 
//            	 logger.debug("   getFileName ->: " + workPath.getFileName().toString());
//            	 if(workPath.getFileName().toString().contains("control_")) {
//
//
//            		 itemMTRDTO = new ItemMTRDTO();            	             	 
//            		 itemMTRDTO.setFileName(workPath.getFileName().toString());            	 
//            		 itemMTRDTO.setFilePath(workPath.toFile().getPath());
//        		 
//            		 listItemMTRDTO.add(itemMTRDTO);
//
//            		 
//            	 } else {
//            		 logger.debug("MTR File");
//            	 }
//            	 
//             }
//             
//            return listItemMTRDTO;
//        }
//        catch (IOException e) {
//        	//throw new RuntimeException("\"Failed to read stored file");
//        	 logger.error("ERROR.");
//        	 logger.error("ERROR.Failed to read stored file");
//        	 logger.error("ERROR.");
//        	 return null;
//        }
//	}
//	
//	@Override
//	public void processFileType1(Path workPath, List <DocUploadFormDTO> listDocUploadFormDTO) {
//	try {
//		DocUploadFormDTO docUploadFormDTO = new DocUploadFormDTO();
//			   	 
//	   	docUploadFormDTO.setFileName(workPath.getFileName().toString());
//			
//	   	String[] splitFileName = workPath.getFileName().toString().split(";");
//	   	String[] heatNumber = splitFileName[1].split("-");
//	   	String[] Mfg = splitFileName[2].split("-");
//	   	String MfgDesc = splitFileName[3].substring(0, splitFileName[3].lastIndexOf('.'));
//	   	String fileType = splitFileName[3].substring(splitFileName[3].lastIndexOf('.')+1);
//		logger.debug("   JK FileType1 ->: " + fileType ); 
//	   	logger.debug("   Heat Number ->: " + heatNumber[0] );
//		logger.debug("   Manufacturer ->: " + Mfg[0] );
//		logger.debug("   Manufacturer Desc ->: " + MfgDesc );
//		 
//		docUploadFormDTO.setHeatNumber(heatNumber[0]);
//		docUploadFormDTO.setSerialNumber(heatNumber[0]);
//		docUploadFormDTO.setMfg(Mfg[0]);
//		docUploadFormDTO.setMfgDesc(MfgDesc);
//		docUploadFormDTO.setFileType(fileType);
//		docUploadFormDTO.setFilePath(workPath.toFile().getPath());
//	
//		listDocUploadFormDTO.add(docUploadFormDTO);
//	}
//	catch (Exception e) {
//		DocUploadFormDTO docUploadFormDTO = new DocUploadFormDTO();
//	   	 
//	   	docUploadFormDTO.setFileName(workPath.getFileName().toString());
//	   	
//		 docUploadFormDTO.setHeatNumber("");
//		 docUploadFormDTO.setSerialNumber("");
//		 docUploadFormDTO.setMfg("");
//		 docUploadFormDTO.setMfgDesc("");
//		 docUploadFormDTO.setPoItemNum(666);
//		 docUploadFormDTO.setPoLineNum(123456); //nullable
//		 docUploadFormDTO.setPoItemDesc("tbd");
//		 
//		 listDocUploadFormDTO.add(docUploadFormDTO);
//		
//	}
//}
//	
//	
//	@Override
//	public void processFileType2(Path workPath, List <DocUploadFormDTO> listDocUploadFormDTO) {
//	try {
//		
//	
//   	 DocUploadFormDTO docUploadFormDTO = new DocUploadFormDTO();
//   	 
//   	 docUploadFormDTO.setFileName(workPath.getFileName().toString());
//		
//	 String[] splitFileName = workPath.getFileName().toString().split("_");
//	 String[] heatNumber = splitFileName[0].split("-");
//	 String[] Mfg = splitFileName[1].split("-");
//	 String MfgDesc = splitFileName[2].substring(0, splitFileName[2].lastIndexOf('.'));
//	 String fileType = splitFileName[2].substring(splitFileName[2].lastIndexOf('.')+1);
//	 logger.debug("   Heat Number ->: " + heatNumber[0] );
//	 logger.debug("   Manufacturer ->: " + Mfg[0] );
//	 logger.debug("   Manufacturer Desc ->: " + MfgDesc );
//	 logger.debug("   JK FileType2 ->: " + fileType );
//	 docUploadFormDTO.setHeatNumber(heatNumber[0]);
//	 docUploadFormDTO.setSerialNumber(heatNumber[0]);
//	 docUploadFormDTO.setMfg(Mfg[0]);
//	 docUploadFormDTO.setMfgDesc(MfgDesc);
//	 docUploadFormDTO.setFileType(fileType);
//	 docUploadFormDTO.setFilePath(workPath.toFile().getPath());
//	 
//	 
//	 docUploadFormDTO.setPoItemNum(docUploadFormDTO.getPoItemNum());
//
//	 	 
//	 listDocUploadFormDTO.add(docUploadFormDTO);
//	 
//	}	catch (Exception e) {
//		
//		
//		DocUploadFormDTO docUploadFormDTO = new DocUploadFormDTO();
//	   	 
//	   	docUploadFormDTO.setFileName(workPath.getFileName().toString());
//	   	
//		 docUploadFormDTO.setHeatNumber("");
//		 docUploadFormDTO.setSerialNumber("");
//		 docUploadFormDTO.setMfg("");
//		 docUploadFormDTO.setMfgDesc("");
//		 docUploadFormDTO.setPoItemNum(666);
//		 docUploadFormDTO.setPoLineNum(123456); //nullable
//		 docUploadFormDTO.setPoItemDesc("tbd");
//		 
//		 listDocUploadFormDTO.add(docUploadFormDTO);
//		
//	}
//}
//
//	@Override
//	public void moveToFinish(String jobFilePath, String initialFileName, String finishedFileName) {
//		try {
//
//			Path initialFilePath = rootLocation.resolve(initialFileName);
//
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + jobFilePath) ); 
//    		Files.move(initialFilePath, finishedFilePath.resolve(finishedFileName), StandardCopyOption.REPLACE_EXISTING);
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//               
//	}
//	
//	@Override
//	public void copyBarcode(String jobFilePath, String initialFileName, String finishedFileName, String pipeFlag) {
//		try {
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//			
//			this.rootLocation = Paths.get(fileDirectoryPath + finishedPath +"/allItemUploads/BarcodePDF");
//			Path initialFilePath = rootLocation.resolve(initialFileName);
//			String[] initialFileNameHeatBarcode = initialFileName.split(".pdf");
//			String[] finishedFileNameHeatBarcode = finishedFileName.split(".pdf");
//			Path initialFilePathHeatBarcode = rootLocation.resolve(initialFileNameHeatBarcode[0] + "_HEAT.pdf");
//
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + jobFilePath) ); 
//    		Files.copy(initialFilePath, finishedFilePath.resolve(finishedFileName), StandardCopyOption.REPLACE_EXISTING);
//    		if(pipeFlag.equalsIgnoreCase("Y")) {
//    			Files.copy(initialFilePathHeatBarcode, finishedFilePath.resolve(finishedFileNameHeatBarcode[0] + "_HEAT.pdf"), StandardCopyOption.REPLACE_EXISTING);
//    		} else {
//    			logger.debug("does not need heat barcode pdf");
//    		}
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//               
//	}
//	
//	@Override
//	public void moveMRCToFinish(String jobFilePath, String initialFileName, String finishedFileName) {
//		try {
//			
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String mrcFileDrop = env.getProperty("spring.upload.nfs.mtr.filedrop");
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//        	
//        	Path initialFilePath = Paths.get(fileDirectoryPath + mrcFileDrop).resolve(initialFileName);
//        			
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + jobFilePath) ); 
//    		Files.move(initialFilePath, finishedFilePath.resolve(finishedFileName), StandardCopyOption.REPLACE_EXISTING);
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//               
//	}
//	
//	//S. Toppi added 4/9/2020
//	@Override
//	public void moveTDWToFinish(String jobFilePath, String initialFileName, String finishedFileName) {
//		try {
//			
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String tdwFileDrop = env.getProperty("spring.upload.nfs.TDW.filedrop");
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//        	
//        	Path initialFilePath = Paths.get(fileDirectoryPath + tdwFileDrop).resolve(initialFileName);
//        			
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + jobFilePath) ); 
//    		Files.move(initialFilePath, finishedFilePath.resolve(finishedFileName), StandardCopyOption.REPLACE_EXISTING);
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//		
//	}
//	
//	
//	@Override
//	public void moveControlFileToError(String jobFilePath, String fileName) {
//		try {
//			
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String mrcFileDrop = env.getProperty("spring.upload.nfs.mtr.filedrop");
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//        	
//        	Path initialFilePath = Paths.get(fileDirectoryPath + mrcFileDrop).resolve(fileName);
//        			
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + "/MRCErrorFiles/errorControlFiles/" + jobFilePath) ); 
//    		Files.move(initialFilePath, finishedFilePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//               
//	}
//	
//	@Override 
//	public void moveTDWControlFileToError(String jobFilePath, String fileName) {
//		try {
//			
//        	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//			String tdwFileDrop = env.getProperty("spring.upload.nfs.TDW.filedrop");
//        	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//        	
//        	Path initialFilePath = Paths.get(fileDirectoryPath + tdwFileDrop).resolve(fileName);
//        			
//        	Path finishedFilePath= Files.createDirectories(Paths.get(fileDirectoryPath + finishedPath + "/TDWErrorFiles/errorControlFiles/" + jobFilePath) ); 
//    		Files.move(initialFilePath, finishedFilePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//        	
//        } catch (IOException e) {
//            throw new StorageException("Failed to move file ------>", e);
//        }
//               
//	}
//	
//	@Override
//	public void processCreateBarcodePDF(String heatBarcodeforPDF, String newBarcodeZPL, String filePath, String fileName, boolean twoBarcodes, int qDocReqLineId, String isItem) throws IOException {
//		
//		String mtrDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//		
//    	//Random Rand = new Random();
//    	String newFilename = "";
//    	
//    	
//    	//QDocReqLine2 qDocReqLine2 = null;
//    	//ItemMTR itemMTR = null;
//    	File heatZplBarcodeToPDF = null;
//    	
//    	HttpClient client = HttpClientBuilder.create().build();
//		File zplBarcodeToPDF = new File(mtrDirectoryPath + filePath + "/BarcodePDF/" + fileName);
////		
////        boolean fileExists = zplBarcodeToPDF.exists();
////        
////        while(fileExists) {
////        	//generate random values from 0-9999
////        	File copyFile = new File(mtrDirectoryPath + filePath + "/" + fileName);
////        	
////        	int upperbound=10000;
////        	int randomNumber = Rand.nextInt(upperbound); 
////        	
////        	newFilename = FilenameUtils.getBaseName(fileName) + "_" + randomNumber + ".pdf";
////        	zplBarcodeToPDF = new File(mtrDirectoryPath + filePath + "/BarcodePDF/" + newFilename);
////        	if(zplBarcodeToPDF.exists()) {
////        		logger.debug("Random Number Generated filename exists! ---- try a new random number.");
////        	} else {
////        		File copyFiles = new File(mtrDirectoryPath + filePath + "/" + newFilename);
////        		Files.copy(copyFile.toPath(), copyFiles.toPath(), StandardCopyOption.REPLACE_EXISTING);
////        		if(isItem.equalsIgnoreCase("Y")) {
////        			// TS 5-26-2020
////        			//QDocReqLineId is actually Item Id if it comes in here
////        			itemMTR = itemMTRDao.getByItemId(qDocReqLineId);
////        		} else {
////        			qDocReqLine2 = qDocReqLineDao2.getById_2(qDocReqLineId);
////        			itemMTR = itemMTRDao.getByItemId(qDocReqLine2.getItemId());
////        		}
////        		
////        		itemMTR.setFileName(newFilename);
////        		itemMTRDao.updateItemMTR(itemMTR);
////        		fileExists = false;
////        	}
////        }
//		
//		//If there are two barcodes that need to be created (trace id barcodes also need a heat barcode, then it will come into this function to create that heat barcode. 
//		if(twoBarcodes) {
//			if(newFilename == "") {
//				heatZplBarcodeToPDF = new File(mtrDirectoryPath + filePath + "/BarcodePDF/" + FilenameUtils.getBaseName(fileName) +"_HEAT.pdf");
//			} else {
//				heatZplBarcodeToPDF = new File(mtrDirectoryPath + filePath + "/BarcodePDF/" + FilenameUtils.getBaseName(newFilename) +"_HEAT.pdf");
//			}
//			
//	        HttpGet heatRequest = new HttpGet("http://api.labelary.com/v1/printers/12dpmm/labels/4x2/0/" + URLEncoder.encode(heatBarcodeforPDF, "UTF-8").replace("+", "%20"));
//	        heatRequest.addHeader("accept", "application/pdf");
//	        
//	        try {
//	        	
//	            HttpResponse response = client.execute(heatRequest);
//	            
//	            int statusCode = response.getStatusLine().getStatusCode();
//	            logger.debug(statusCode);
//	            
//	            
//	            HttpEntity entity = response.getEntity();
//	            if (entity != null) {
//	                try {
//	                	InputStream zplStream = entity.getContent();
//
//	                	FileUtils.copyInputStreamToFile(zplStream, heatZplBarcodeToPDF);
//	                	
//	                } catch(Exception e) {
//	                	logger.debug("Error Copying Stream to File ---> " + e );
//	                }
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//		}
//		
//		//Make this barcode no matter what. It could be a heat or Trace barcode.
// 
//        HttpGet request = new HttpGet("http://api.labelary.com/v1/printers/12dpmm/labels/4x2/0/" + URLEncoder.encode(newBarcodeZPL, "UTF-8").replace("+", "%20"));
//        request.addHeader("accept", "application/pdf");
//        
//        try {
//        	
//            HttpResponse response = client.execute(request);
//            
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.debug(statusCode);
//            
//            
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                try {
//                	InputStream zplStream = entity.getContent();
//
//                	FileUtils.copyInputStreamToFile(zplStream, zplBarcodeToPDF);
//                	
//                } catch(Exception e) {
//                	logger.debug("Error Copying Stream to File ---> " + e );
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	
//
//		
//		
//	}
//	
//	@Override
//	public void processCreateTraceIDReturnPDF(String newBarcodeZPL, String filePath, String fileName) throws IOException {
//		
//		String mtrDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//    	
//    	HttpClient client = HttpClientBuilder.create().build();
//		File zplBarcodeToPDF = new File(mtrDirectoryPath + filePath + "/BarcodePDF/" + fileName);
// 
//        HttpGet request = new HttpGet("http://api.labelary.com/v1/printers/12dpmm/labels/4x2/0/" + URLEncoder.encode(newBarcodeZPL, "UTF-8").replace("+", "%20"));
//        request.addHeader("accept", "application/pdf");
//        
//        try {
//        	
//            HttpResponse response = client.execute(request);
//            
//            int statusCode = response.getStatusLine().getStatusCode();
//            logger.debug(statusCode);
//            
//            
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                try {
//                	InputStream zplStream = entity.getContent();
//
//                	FileUtils.copyInputStreamToFile(zplStream, zplBarcodeToPDF);
//                	
//                } catch(Exception e) {
//                	logger.debug("Error Copying Stream to File ---> " + e );
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	
//
//		
//		
//	}
//	
//	@Override
//	public void writeMRCErrorFile( String response, String errorFile) throws IOException {
//    	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//    	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//    	
//    
//    	
//    	Files.write(Paths.get(fileDirectoryPath + finishedPath + "/MRCErrorFiles/" + errorFile), response.getBytes());
//    	
//    	//write the file straight to the sFTP
//    	
//	}
//	
//	@Override
//	public void sftpFileUploadMRC(String errorFile) {
//    	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//    	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//    	String SFTPWORKINGDIR = env.getProperty("sftp.mtr.errorFolder");
//    	String sftpRemoteErrorDirectory = env.getProperty("sftp.mtr.remotedirectory");
//    	
//		String SFTPHOST = env.getProperty("sftp.host");
//		int SFTPPORT = 22;
//		String SFTPUSER = env.getProperty("sftp.mtr.mrc.username");
//		String SFTPPASS = env.getProperty("sftp.mtr.mrc.password");
//		  
//
//		  Session session = null;
//		  Channel channel = null;
//		  ChannelSftp channelSftp = null;
//		  //System.out.println("preparing the host information for sftp.");
//		  try {
//		   JSch jsch = new JSch();
//		   session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//		   session.setPassword(SFTPPASS);
//		   java.util.Properties config = new java.util.Properties();
//		   config.put("StrictHostKeyChecking", "no");
//		   session.setConfig(config);
//		   session.connect();
//		   //System.out.println("Host connected.");
//		   channel = session.openChannel("sftp");
//		   channel.connect();
//		   //System.out.println("sftp channel opened and connected.");
//		   channelSftp = (ChannelSftp) channel;
//		   //channelSftp.cd(SFTPWORKINGDIR);
//		   //File f = new File(fileDirectoryPath + finishedPath + "/MRCErrorFiles/" + errorFile);
//	       channelSftp.put(fileDirectoryPath + finishedPath + "/MRCErrorFiles/" + errorFile, sftpRemoteErrorDirectory + SFTPWORKINGDIR + "/" +  errorFile);
//		  } catch (Exception ex) {
//		   //System.out.println("Exception found while tranfer the response.");
//		  } finally {
//
//		   channelSftp.exit();
//		   //System.out.println("sftp Channel exited.");
//		   channel.disconnect();
//		   //System.out.println("sftp Channel disconnected.");
//		   session.disconnect();
//		   //System.out.println("Host Session disconnected.");
//		  }
//		 }
//
//
//	
//	@Override
//	public void writeTDWErrorFile(String response, String errorFile) throws IOException {
//	    String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//	    String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//	    	
//	    
//	    	
//	    	Files.write(Paths.get(fileDirectoryPath + finishedPath + "/TDWErrorFiles/" + errorFile), response.getBytes());
//	    	
//	    	//write the file straight to the sFTP
//	    	
//		}
//	
//	@Override
//	public void sftpFileUploadTDW(String errorFile) {
//    	String fileDirectoryPath = env.getProperty("spring.upload.nfs.fileDirectoryPath"); 
//    	String finishedPath = env.getProperty("spring.upload.nfs.mtr.storage.finished");
//    	String SFTPWORKINGDIR = env.getProperty("sftp.mtr.errorFolder");
//    	String sftpRemoteErrorDirectory = env.getProperty("sftp.tdw.remotedirectory");
//    	
//		String SFTPHOST = env.getProperty("sftp.host");
//		int SFTPPORT = 22;
//		String SFTPUSER = env.getProperty("sftp.mtr.png.tdw.username");
//		String SFTPPASS = env.getProperty("sftp.mtr.png.tdw.password");
//		  
//
//		  Session session = null;
//		  Channel channel = null;
//		  ChannelSftp channelSftp = null;
//		  //System.out.println("preparing the host information for sftp.");
//		  try {
//		   JSch jsch = new JSch();
//		   session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
//		   session.setPassword(SFTPPASS);
//		   java.util.Properties config = new java.util.Properties();
//		   config.put("StrictHostKeyChecking", "no");
//		   session.setConfig(config);
//		   session.connect();
//		   //System.out.println("Host connected.");
//		   channel = session.openChannel("sftp");
//		   channel.connect();
//		   //System.out.println("sftp channel opened and connected.");
//		   channelSftp = (ChannelSftp) channel;
//		   //channelSftp.cd(SFTPWORKINGDIR);
//		   //File f = new File(fileDirectoryPath + finishedPath + "/MRCErrorFiles/" + errorFile);
//	       channelSftp.put(fileDirectoryPath + finishedPath + "/TDWErrorFiles/" + errorFile, sftpRemoteErrorDirectory + SFTPWORKINGDIR + "/" +  errorFile);
//		  } catch (Exception ex) {
//		   //System.out.println("Exception found while tranfer the response.");
//		  } finally {
//
//		   channelSftp.exit();
//		   //System.out.println("sftp Channel exited.");
//		   channel.disconnect();
//		   //System.out.println("sftp Channel disconnected.");
//		   session.disconnect();
//		   //System.out.println("Host Session disconnected.");
//		  }
//			 
//		
//	}
	

}