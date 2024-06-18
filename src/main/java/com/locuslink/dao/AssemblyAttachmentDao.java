
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.AssemblyAttachmentDTO;
import com.locuslink.model.AssemblyAttachment;


public interface AssemblyAttachmentDao  {

    public static final String BEAN_NAME = "AssemblyAttachmentDao";
    
	
	public AssemblyAttachment getById (int id);
		
	public List<AssemblyAttachment>  getAllById(int id);
	
	
	// 6-10-2024
	public AssemblyAttachmentDTO getDtoById (int id);
		
	public 	List<AssemblyAttachmentDTO> getAllDTObyAssemblyId(int assemblyPkid);
	
	public 	List<AssemblyAttachmentDTO> getAllDTOMtrByAssemblyId(int assemblyPkid);
	
	
	
	public void save(AssemblyAttachment assemblyAttachment);
	
	public void saveOrUpdate(AssemblyAttachment assemblyAttachment);
	
	public void delete(AssemblyAttachment assemblyAttachment);


	
}
