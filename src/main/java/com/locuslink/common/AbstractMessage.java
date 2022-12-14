package com.locuslink.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@SuppressWarnings("serial")
public abstract class AbstractMessage implements Serializable {
	
	private String version;
	private String protocol;

	private Map<String, Object> data;

	@JsonSerialize
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	public Map<String, Object> getData() {
		return data;
	}

	protected Object getField(String name) {
		if (data == null) {
			return null;
		}
		return data.get(name);
	}

	public Boolean getFieldAsBoolean(String name) {
		return (Boolean) getField(name);
	}

	public Double getFieldAsDouble(String name) {
		return (Double) getField(name);
	}
	
	public Integer getFieldAsInt(String name) {
		return Integer.valueOf(getField(name).toString());
	}
	
	@SuppressWarnings("rawtypes")
	public Map getFieldAsMap(String name) {
		return (Map) getField(name);
	}

	public String getFieldAsString(String name) {
		return (String) getField(name);
	}

	public Object getFieldAsObject(String name) {
		return (Object) getField(name);
	}
	
	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setField(String name, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(name, value);
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
