
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.UidDTO;
import com.locuslink.model.Industry;


public interface IndustryDao  {

    public static final String BEAN_NAME = "IndustryDao";
    
	
	public Industry getById (int id);
		
	public List<Industry>  getAll();
	
	public List<UidDTO>  getUidDTO(int iPkId, int siPkId, int vPkId, int ptPkId, int pstPkId);
	
	
	
	public void save(Industry industry);
	
	public void saveOrUpdate(Industry industry);
	
	public void delete(Industry industry);
	
}
