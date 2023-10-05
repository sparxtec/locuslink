
package com.locuslink.dao;

import java.util.List;

import com.locuslink.dto.UserDTO;
import com.locuslink.model.UserLocuslink;


public interface UserLocuslinkDao  {

    public static final String BEAN_NAME = "UserLocuslinkDao";
    
	
	public UserLocuslink getById (int id);
	
	public UserLocuslink getUserByLanId(String lanId);
	
	public List<UserLocuslink>  getAll();
	
	public List<UserDTO>  getAllDTO ();
	
	
	
	public void save(UserLocuslink userLocuslink);
	
	public void saveOrUpdate(UserLocuslink userLocuslink);
	
	public void delete(UserLocuslink userLocuslink);
	
	
	//public List<UserLocuslink>  getAllActiveUsers();	
	
	//public UserLocuslink getActiveUsersByFirstandLastName(String firstName, String lastName);
}
