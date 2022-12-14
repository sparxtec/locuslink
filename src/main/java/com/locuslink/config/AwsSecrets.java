package com.locuslink.config;

import org.springframework.stereotype.Component;

@Component
public class AwsSecrets  {

	String dbUserName;
	String dbPassword;

	public AwsSecrets() {
	}

	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}


}