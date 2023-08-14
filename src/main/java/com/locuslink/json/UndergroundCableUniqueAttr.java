package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UndergroundCableUniqueAttr {
	
	// TODO 8-13-2024 not sure we need 2 attribute lists, regular and unique.
	// maybe list product_attributes would be enough
	
	private String ConductorSize;	
	private String VoltageRating;
	private String ConductorMaterial;
	private String ConductorConstruction;
	private String CableConfiguration;
	private String InstallationType;
	private String InsulationType;
	private String JacketMaterial;
	private String BILRating;
	private String MetallicShieldingType;
	private String TemperatureRating;

}