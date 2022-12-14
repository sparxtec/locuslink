
package com.locuslink.dto;

public class LoginFormDTO {

	private String username;
	private String password;
	private String id_token;

	private String headerInfo ="";
	private String SAMLResponse ="";;


	public String getHeaderInfo() {
		return headerInfo;
	}
	public void setHeaderInfo(String headerInfo) {
		this.headerInfo = headerInfo;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
	public String getSAMLResponse() {
		return SAMLResponse;
	}
	public void setSAMLResponse(String sAMLResponse) {
		SAMLResponse = sAMLResponse;
	}

}
