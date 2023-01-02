
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.Item;

public interface ItemDao  {
    
    public static final String BEAN_NAME = "itemDao";
    
   
	public Item getById (int itemId);
	
    public List<Item> getAllItems();

//	public Item getByItemNumAndHeatNum(String itemNum, String heatSerial);
	
	
	// PERSISTENCE 
	public void saveItem(Item item);
	
    public void updateItem(Item item) ;
    
    public void deleteItem(Item item);
    

	

//	public List<Item> getByItemNumAndHeatNum(String itemNum);
	
	
//	// C.Sparks 5-20-2020
//	public List<ItemDTO> getItemDTOList_Search (String searchValue, int contractorId) ;
//	
//	public List<ItemDTO> getItemDTOList_SearchbyItemOnly (String itemNumber);
//	
//	public List<ItemDTO> getItemDTOListbyContractorId (String contractorId);
//	
//    public List<ItemDTO> getItemDTOListbyContractor(String contractorId, String filterCode);
//	
//	public List<ItemDTO> getItemDTOList_contractor (String itemNumber, String filter, int contractorId) ;
//	
//	public List<ItemDTO> getItemDTOList_contractorForManage (String itemNumber, String filterCode, int contractorId);
//	
//	public List<ItemDTO> getItemDTOList(String itemNumber, String filter);
//	
//	public List<ItemDTO> getItemDTOList_manage(String itemNumber, String filterCode);
//
//	public ItemDTO getItemDTO (int itemId) ;
//	
//	public ItemDTO getItemDTObyIdandFileName (int itemId, String fileName);
//	
//	public ItemDTO getItemDTObyIdandFileNameandContractorandFilePath(int itemId, String fileName, String contractorId, String filePath);
//	
//	public List<ItemDTO> getItemDTOListbyHeatandContractor(String heatNumber, int contractorId);
//	
//	//V.Padma 02/18/2021
//	public List<ItemDTO> getApprovedItemDTOListbyProject(String projectNumber, boolean getAllItemsFlag);
//	
//	public List<ItemDTO> getItemDTOListbyHeat (String heatNumber);
//	
//	public List<ItemDTO> getItemDTOListbyItemId(int itemId);
//
//	public List<ItemDTO> getItemDTOListbyHeatValidationErrors(String projectNumber);
    
}
