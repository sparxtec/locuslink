
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.AssemblyDTO;
import com.locuslink.model.Assembly;


public interface AssemblyDao  {

    public static final String BEAN_NAME = "AssemblyDao";
    
	
	public Assembly getById (int id);
	
	public List<Assembly>  getAll();
	
	
	public List<AssemblyDTO>  getAllDTO ();
	
	public AssemblyDTO getDtoById (int id);
	
	
	public void save(Assembly assembly);
	
	public void saveOrUpdate(Assembly assembly);
	
	public void delete(Assembly assembly);
	
}
