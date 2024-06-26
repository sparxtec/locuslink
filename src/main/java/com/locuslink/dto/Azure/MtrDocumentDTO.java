package com.locuslink.dto.Azure;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class MtrDocumentDTO extends DocumentDTO {
	
	private List<String> facilityNameForPipeManufacturer;
	private List<String> facilityAddressForPipeManufacturer;
	private List<String> facilityNameForPlateOrCoilManufacturer;
	private List<String> facilityAddressForPlateOrCoilManufacturer;
	private List<String> heatNumber;
	private List<String> pipeNumber;
	private List<String> dateOfCertification;
	private List<String> chemistryTestType;
	private List<String> description;
	private List<String> tensileYieldStrength;
	private List<String> ultimateTensileStrength;
	private List<String> tensileTestUnits;
	private List<String> charpyVNotchAverageShearPercent;
	private List<String> charpyVNotchTestType;
	private List<String> dropWeightTearTestType;
	private List<String> tensileTestType;
	private List<String> hardnessTestType;
	private List<String> hardnessTestUnits;
	private List<String> maximumRecordedHardness;
	private List<String> hydrostaticPressureTestValue;
	private List<String> hydrostaticPressureTestUnits;
	private List<String> hydrostaticPressureTestDuration;
	private List<String> carbonPercent;
	private List<String> manganesePercent;
	private List<String> copperPercent;
	private List<String> guidedBendTestResult;
	private List<String> nonDestructiveTestingStatement;
	private List<String> customerPurchaseOrder;
	
	public MtrDocumentDTO(String id, List<Entity> entities, List<String> warnings) {
		super(id, entities, warnings);
		
		this.carbonPercent = new ArrayList<String>();
		this.charpyVNotchAverageShearPercent = new ArrayList<String>();
		this.charpyVNotchTestType = new ArrayList<String>();
		this.chemistryTestType = new ArrayList<String>();
		this.copperPercent = new ArrayList<String>();
		this.customerPurchaseOrder = new ArrayList<String>();
		this.dateOfCertification = new ArrayList<String>();
		this.description = new ArrayList<String>();
		this.dropWeightTearTestType = new ArrayList<String>();
		this.facilityAddressForPipeManufacturer = new ArrayList<String>();
		this.facilityAddressForPlateOrCoilManufacturer = new ArrayList<String>();
		this.facilityNameForPipeManufacturer = new ArrayList<String>();
		this.facilityNameForPlateOrCoilManufacturer = new ArrayList<String>();
		this.guidedBendTestResult = new ArrayList<String>();
		this.hardnessTestType = new ArrayList<String>();
		this.hardnessTestUnits = new ArrayList<String>();
		this.heatNumber = new ArrayList<String>();
		this.hydrostaticPressureTestDuration = new ArrayList<String>();
		this.hydrostaticPressureTestUnits = new ArrayList<String>();
		this.hydrostaticPressureTestValue = new ArrayList<String>();
		this.manganesePercent = new ArrayList<String>();
		this.maximumRecordedHardness = new ArrayList<String>();
		this.nonDestructiveTestingStatement = new ArrayList<String>();
		this.pipeNumber = new ArrayList<String>();
		this.tensileTestType = new ArrayList<String>();
		this.tensileTestUnits = new ArrayList<String>();
		this.tensileYieldStrength = new ArrayList<String>();
		this.ultimateTensileStrength = new ArrayList<String>();
		
		assignAttributes(this.getEntities());
	}
	
	private void assignAttributes(List<Entity> entities) {
		for (Entity entity : entities) {
			
			switch (entity.getCategory().toUpperCase()) {
			case "PIPE_NUMBER" -> this.pipeNumber.add(entity.getText());
			case "HEAT_NUMBER" -> this.heatNumber.add(entity.getText());
			case "DATE_OF_CERTIFICATION" -> this.dateOfCertification.add(entity.getText());
			case "FACILITY_NAME_FOR_PIPE_MANUFACTURER" -> this.facilityNameForPipeManufacturer.add(entity.getText());
			case "FACILITY_ADDRESS_FOR_PIPE_MANUFACTURER" -> this.facilityAddressForPipeManufacturer.add(entity.getText());
			case "FACILITY_NAME_FOR_PLATEORCOIL_MANUFACTURER" -> this.facilityNameForPlateOrCoilManufacturer.add(entity.getText());
			case "FACILITY_ADDRESS_FOR_PLATEORCOIL_MANUFACTURER" -> this.facilityAddressForPlateOrCoilManufacturer.add(entity.getText());
			case "PROCESS_GUIDED_BEND_TEST_PASSORFAIL" -> this.guidedBendTestResult.add(entity.getText());
			case "CVN_AVERAGE_SHEAR_AREA_PERCENT" -> this.charpyVNotchAverageShearPercent.add(entity.getText());
			case "CERTIFIED_HYDROSTATIC_TEST_PRESSURE_VALUE" -> this.hydrostaticPressureTestValue.add(entity.getText());
			case "CERTIFIED_HYDROSTATIC_TEST_PRESSURE_VALUE_UNITS" -> this.hydrostaticPressureTestUnits.add(entity.getText());
			case "CERTIFIED_HYDROSTATIC_TEST_PRESSURE_DURATION_SEC" -> this.hydrostaticPressureTestDuration.add(entity.getText());
			case "NDT_STATEMENT" -> this.nonDestructiveTestingStatement.add(entity.getText());
			case "CARBON_C" -> this.carbonPercent.add(entity.getText());
			case "MANGANESE_MN" -> this.manganesePercent.add(entity.getText());
			case "COPPER_CU" -> this.copperPercent.add(entity.getText());
			case "CUSTOMER_PURCHASE_ORDER" -> this.customerPurchaseOrder.add(entity.getText());
			case "TENSILE_TEST_TYPE" -> this.tensileTestType.add(entity.getText());
			case "CVN_TEST_TYPE" -> this.charpyVNotchTestType.add(entity.getText());
			case "DWTT_TEST_TYPE" -> this.dropWeightTearTestType.add(entity.getText());
			case "HARDNESS_TEST_TYPE" -> this.hardnessTestType.add(entity.getText());
			case "MAX_HARDNESS_RECORDED_BODY" -> this.maximumRecordedHardness.add(entity.getText());
			case "HARDNESS_TEST_UNITS" -> this.hardnessTestUnits.add(entity.getText());
			case "CHEMISTRY_TEST_TYPE" -> this.chemistryTestType.add(entity.getText());
			case "DESCRIPTION" -> this.description.add(entity.getText());
			case "TENSILE_TEST_YIELD_STRENGTH" -> this.tensileYieldStrength.add(entity.getText());
			case "ULTIMATE_TENSILE_STRENGTH" -> this.ultimateTensileStrength.add(entity.getText());
			case "TENSILE_STRENGTH_UNITS" -> this.tensileTestUnits.add(entity.getText());
			default -> throw new IllegalArgumentException("Unexpected Entity type: " + entity.getCategory().toUpperCase());
			}
		}
	}

}
