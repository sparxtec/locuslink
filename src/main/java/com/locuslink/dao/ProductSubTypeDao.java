
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.ProductSubType;


public interface ProductSubTypeDao  {

    public static final String BEAN_NAME = "ProductSubTypeDao";
    
	
	public ProductSubType getById (int id);
			
	public List<ProductSubType>  getAll();
	
	public List<ProductSubType>  getByProductType(int productTypePkId);
	
	
	public void save(ProductSubType productSubType);
	
	public void saveOrUpdate(ProductSubType productSubType);
	
	public void delete(ProductSubType productSubType);
	
}
