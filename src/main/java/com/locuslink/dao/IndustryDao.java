
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.Industry;


public interface IndustryDao  {

    public static final String BEAN_NAME = "IndustryDao";
    
	
	public Industry getById (int id);
		
	public List<Industry>  getAll();
	
	
	public void save(Industry industry);
	
	public void saveOrUpdate(Industry industry);
	
	public void delete(Industry industry);
	
}
