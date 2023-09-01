
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.SubIndustry;


public interface SubIndustryDao  {

    public static final String BEAN_NAME = "SubIndustryDao";
    
	
	public SubIndustry getById (int id);
		
	public List<SubIndustry>  getAll();
	
	
	public void save(SubIndustry subIndustry);
	
	public void saveOrUpdate(SubIndustry subIndustry);
	
	public void delete(SubIndustry subIndustry);
	
}
