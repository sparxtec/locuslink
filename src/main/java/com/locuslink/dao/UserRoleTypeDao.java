
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.ProductType;
import com.locuslink.model.UserRoleType;


public interface UserRoleTypeDao  {

    public static final String BEAN_NAME = "UserRoleTypeDao";
    
	
	public UserRoleType getById (int id);
		
	public List<UserRoleType>  getAll();
	
		
	public void save(UserRoleType userRoleType);
	
	public void saveOrUpdate(UserRoleType userRoleType);
	
	public void delete(UserRoleType userRoleType);		
}
