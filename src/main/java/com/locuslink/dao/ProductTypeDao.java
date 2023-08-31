
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.model.ProductAttachment;
import com.locuslink.model.ProductType;


public interface ProductTypeDao  {

    public static final String BEAN_NAME = "ProductTypeDao";
    
	
	public ProductType getById (int id);
	
	//public ProductAttachmentDTO getDtoById (int id);
		
	public List<ProductType>  getAll();
	
	
	public void save(ProductType productType);
	
	public void saveOrUpdate(ProductType productType);
	
	public void delete(ProductType productType);
	
}
