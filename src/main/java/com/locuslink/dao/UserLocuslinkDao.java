
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.UserLocuslink;


public interface UserLocuslinkDao  {

    public static final String BEAN_NAME = "UserLocuslinkDao";
    
	
	public UserLocuslink getById (int UserLocuslinkId);
	
	public UserLocuslink getUserByLanId(String lanId);
	
	public List<UserLocuslink>  getAll();
	
	
	
	public void saveOrUpdate(UserLocuslink newProfile);
	
	public void delete(UserLocuslink UserLocuslink);
	
	
	//public List<UserLocuslink>  getAllActiveUsers();	
	
	//public UserLocuslink getActiveUsersByFirstandLastName(String firstName, String lastName);
}
