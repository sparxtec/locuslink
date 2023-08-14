package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommonAttr {
  

	private String Industry;
	private String Sub_Industry;
	private String Asset_Type;
	private String Asset_Subtype;
	private String Version;
	
	private String catalog_id;
	private String unique_asset_id	;
	private String manufacturer;

}