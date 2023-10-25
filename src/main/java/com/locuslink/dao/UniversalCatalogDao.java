
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.UniversalCatalogDTO;
import com.locuslink.model.UniversalCatalog;


public interface UniversalCatalogDao  {

    public static final String BEAN_NAME = "UniversalCatalogDao";
    
	
	public UniversalCatalog getById (int id);
	
	public List<UniversalCatalog>  getAll();
	
	
	public UniversalCatalogDTO getDtoById(int pkid);
	
	public  List<UniversalCatalogDTO>  getAllDTO();
	
	
	public void saveOrUpdate(UniversalCatalog universalCatalog);
	
	public void delete(UniversalCatalog universalCatalog);
	
}
