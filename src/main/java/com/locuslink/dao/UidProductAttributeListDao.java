
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.UidProductAttributeListDTO;
import com.locuslink.model.UidProductAttributeList;


public interface UidProductAttributeListDao  {

    public static final String BEAN_NAME = "UidProductAttributeListDao";
    
	
	public UidProductAttributeList getById (int id);
	
	public UidProductAttributeListDTO getDtoById (int id);
	
	public List<UidProductAttributeList>  getAll();
	
	public List<UidProductAttributeListDTO>  getAllDTO ();
	
	
	public void save(UidProductAttributeList uidProductAttributeList);
	
	public void saveOrUpdate(UidProductAttributeList uidProductAttributeList);
	
	public void delete(UidProductAttributeList uidProductAttributeList);
	
}
