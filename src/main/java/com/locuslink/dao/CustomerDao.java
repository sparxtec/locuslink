
package com.locuslink.dao;

import java.util.List;

import com.locuslink.model.Customer;


public interface CustomerDao  {

    public static final String BEAN_NAME = "CustomerDao";
    
	
	public Customer getById (int id);
	
	public List<Customer>  getAll();
	
	
	public void saveOrUpdate(Customer customer);
	
	public void delete(Customer customer);
	
}
