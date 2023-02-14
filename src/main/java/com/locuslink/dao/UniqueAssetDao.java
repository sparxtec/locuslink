
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.UniqueAssetDTO;
import com.locuslink.model.UniqueAsset;


public interface UniqueAssetDao  {

    public static final String BEAN_NAME = "UniqueAssetDao";
    
	
	public UniqueAsset getById (int id);
	
	public List<UniqueAsset>  getAll();
	
	public List<UniqueAssetDTO>  getAllDTO ();
	
	
	
	
	public void saveOrUpdate(UniqueAsset uniqueAsset);
	
	public void delete(UniqueAsset uniqueAsset);
	
}
