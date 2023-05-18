
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.ProductAttachmentDTO;
import com.locuslink.model.ProductAttachment;


public interface ProductAttachmentDao  {

    public static final String BEAN_NAME = "ProductAttachmenttDao";
    
	
	public ProductAttachment getById (int id);
	
	public ProductAttachmentDTO getDtoById (int id);
		
	public List<ProductAttachmentDTO>  getDtoByUniqueAssetId (int Id);
	
	
	public void save(ProductAttachment productAttachment);
	
	public void saveOrUpdate(ProductAttachment productAttachment);
	
	public void delete(ProductAttachment productAttachment);
	
}
