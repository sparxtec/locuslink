package com.locuslink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class AssemblyAzureMtrDto {
	public String category;
	public String confidenceScore;
	public String length;
	public String offset;
	public String text;	
}
