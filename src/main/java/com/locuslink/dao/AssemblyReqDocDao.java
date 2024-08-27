
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.AssemblyReqDoc;


public interface AssemblyReqDocDao  {

    public static final String BEAN_NAME = "AssemblyReqDocDao";
    
	
	public AssemblyReqDoc getById (int id);
		
	//public List<AssemblyReqDoc>  getAllById(int id);
	
	public List<AssemblyReqDoc>  getAll();
	

	
	
	public void save(AssemblyReqDoc assemblyReqDoc);
	
	public void saveOrUpdate(AssemblyReqDoc assemblyReqDoc);
	
	public void delete(AssemblyReqDoc asssemblyReqDoc);
	
}
