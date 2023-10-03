
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.UserLocuslink;
import com.locuslink.model.UserMembership;


public interface UserMembershipDao  {

    public static final String BEAN_NAME = "UserMembershipDao";
    
	
	public UserMembership getById (int id);
		
	public List<UserMembership>  getAll();
	
		
	public void save(UserMembership userMembership);
	
	public void saveOrUpdate(UserMembership userMembership);
	
	public void delete(UserMembership userMembership);		
}
