package com.locuslink.common;

@SuppressWarnings("serial")
public class GenericMessageRequest extends AbstractMessage {

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
