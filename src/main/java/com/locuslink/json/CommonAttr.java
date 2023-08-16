package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommonAttr {
  

	private String industry;
	private String sub_industry;
	private String asset_type;
	private String asset_subtype;
	private String version;
	
	private String catalog_id;
	private String unique_asset_id	;
	private String manufacturer;

}