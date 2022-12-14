package com.locuslink.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
public class GenericMessageResponse extends AbstractMessage {

	public static final int SUCCESSFUL = 0;
	public static final int ERROR_UNKNOWN_MESSAGE = 10;
	public static final int ERROR_INCORRECT_MESSAGE_FORMAT = 20;
	public static final int ERROR_REQUIRED_FIELD_MISSING = 30;
	public static final int ERROR_INVALID_ARGUMENT = 40;
	public static final int ERROR_INVALID_ACTION = 50;
	public static final int ERROR_INTERNAL_SERVER_ERROR = 60;

	private final String name;

	private int error;
	
	private String errorMessage;

	public GenericMessageResponse(String version, String protocol, String name) {
		this.name = name;

		setVersion(version);
		setProtocol(protocol);
	}

	public int getError() {
		return error;
	}

	@JsonSerialize
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getName() {
		return name;
	}

	public void setError(int error) {
		this.error = error;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
