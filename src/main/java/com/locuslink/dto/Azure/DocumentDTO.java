package com.locuslink.dto.Azure;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class DocumentDTO {

	private String id;
	
	// Complex objects
	private List<Entity> entities;
	private List<String> warnings;
}
