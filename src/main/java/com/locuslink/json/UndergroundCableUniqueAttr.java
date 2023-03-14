package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UndergroundCableUniqueAttr {
	
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