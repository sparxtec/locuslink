
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.ProductAttribute;


public interface ProductAttributeDao  {

    public static final String BEAN_NAME = "ProductAttributeDao";
    
	
	public ProductAttribute getById (int id);
	
	public List<ProductAttribute>  getByUniversalCatalogId (int Id);
	
	
	public void save(ProductAttribute productAttribute);
	
	public void saveOrUpdate(ProductAttribute productAttribute);
	
	public void delete(ProductAttribute productAttribute);
	
}
