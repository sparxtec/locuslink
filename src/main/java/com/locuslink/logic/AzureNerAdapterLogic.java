package com.locuslink.logic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locuslink.common.SecurityContextManager;


@Service
public class AzureNerAdapterLogic {
		
	private static final Logger logger = Logger.getLogger(AzureNerAdapterLogic.class);
	
	@Autowired
    private SecurityContextManager securityContextManager;
	

	/**
	 *   C.Sparks 4-19-2024
	 *   	We will define the logic methods needed
	 */
	public boolean process_1(  ) {
		
		logger.debug("Starting process_1() " );


		return true;
	}

	
	
}
