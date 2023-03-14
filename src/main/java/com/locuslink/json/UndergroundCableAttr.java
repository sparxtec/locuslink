package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UndergroundCableAttr {
	
	private String CountryofOrigin;	
	private String DateofManufacture;
	private String ReelId;
	private String Diameter;
	private String ConductorStrandCount;
	private String Weight;
	private String BendingRadius;
	private String ManufacturerCatalogId;
	private String CustomerCatalogId;
	
	// This should be in the Po Table, and come in via thaqt process, duplicating here for now
	private String CustomerPoNumber;
	
}