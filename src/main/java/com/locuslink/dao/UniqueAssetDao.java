
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.UniqueAsset;


public interface UniqueAssetDao  {

    public static final String BEAN_NAME = "UniqueAssetDao";
    
	
	public UniqueAsset getById (int id);
	
	public List<UniqueAsset>  getAll();
	
	
	public void saveOrUpdate(UniqueAsset uniqueAsset);
	
	public void delete(UniqueAsset uniqueAsset);
	
}
