package com.locuslink.dto.Textract;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class AwsTextractBlockDTO {		
	private String blockType;
	private String confidence;
	private String text;
	private String textType;
	private Integer rowIndex;
    private Integer columnIndex;
    private Integer rowSpan;
    private Integer columnSpan;   
    private String id;   
    private String selectionStatus;
    private Integer page;
        
    // Complex Objects 
    private Geometry geometry;    
    private Query query;
    private List<Relationship> relationships;
    private List<String> entityTypes;
    
    // Convenience Method
	public final boolean hasRelationships() {
	  return relationships != null;
	}
		
}

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
class Geometry {
	private BoundingBox boundingBox;
	private List<Point> polygon;
}

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
class BoundingBox {
  private String width;
  private String height;
  private String left;
  private String top;
 }

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
class Point {
  private String x;
  private String y;
}

@Data
@Builder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
class Query {
  private String text;
  private String alias;
  private List<String> pages;
}