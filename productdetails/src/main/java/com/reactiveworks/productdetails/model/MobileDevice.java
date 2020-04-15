package com.reactiveworks.productdetails.model;

import java.util.Map;

import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

public class MobileDevice extends ProcessingDevice {
	private String simType;
	private static final String SIM_TYPE = "SIMtYPE";

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	@Override
	public String toString() {
		return "MobileDevice [simType=" + simType + "]";
	}

	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		//LOGGER_OBJ.debug(productDetails.get(SIM_TYPE));
		super.buildProduct(productDetails);
		this.simType = productDetails.get(SIM_TYPE);
	}
}
