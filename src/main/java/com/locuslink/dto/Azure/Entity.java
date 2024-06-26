package com.locuslink.dto.Azure;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Entity {

	private String category;
	private Float confidenceScore;
	private Integer length;
	private Integer offset;
	private String text;
}
