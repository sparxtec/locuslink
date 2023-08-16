package com.locuslink.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UndergroundCableAttr extends CommonAttr{


	//Universal Catalog Attributes
	private String conductor_size; 
	private String voltage_rating;
	private String cable_configuration;
	private String phase_conductor_material;
	private String conductor_stranding_Class;
	private String conductor_stranding_type;
	private String conductor_shield;
	private String insulation_material;
	private String insulation_shield;
	private String insulation_level;
	private String neutral_material;
	private String neutral_configuration;
	private String neutral_percent;
	private String jacket_material;
	private String moisture_bocked;
	private String installation_type;
	private String submersable;
	private String maximum_temperature;
	private String bil_rating ;

	private String lot_code;
	private String country_of_origin;
	private String date_of_manufacture;
	private String reel_ID;
	private String manufacturer_catalog_id;
	private String customer_part_number;
	private String customer_po_number;
	private String reel_volume;
	private String fault_current_rating;
	private String frequency;
	private String minimum_operating_temperature;
	private String nominal_inside_diameter;
	private String nominal_outside_diameter;
	private String nominal_insulation_thickness ;
	private String nominal_diameter_over_insulation;
	private String jacket_thickness;
	private String size_of_neutral ;
	private String percent_overlap;
	private String filling_material;
	private String pulling_tension ;
	private String sidewall_bearing_pressure;
	private String conductor_strand_count;
	private String weight ;
	private String bending_radius;

	
	// This should be in the Po Table, and come in via thaqt process, duplicating here for now
	private String CustomerPoNumber;
	
}