package com.reactiveworks.productdetails.model;

import java.util.Map;


import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

/**
 *This class represents WashingMachine.
 */
public class WashingMachine extends Product {
	private String loadCapacity;
	private String isAutomatic;
	private String doorType;
	private static final String LOAD_CAPACITY = "loadCapacity";
	private static final String IS_AUTOMATIC = "isAutomatic";
	private static final String DOOR_TYPE = "doorType";

	public String getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(String loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public String getIsAutomatic() {
		return isAutomatic;
	}

	public void setIsAutomatic(String isAutomatic) {
		this.isAutomatic = isAutomatic;
	}

	public String getDoorType() {
		return doorType;
	}

	public void setDoorType(String doorType) {
		this.doorType = doorType;
	}

	@Override
	public String toString() {
		return "WashingMachine [loadCapacity=" + loadCapacity + ", isAutomatic=" + isAutomatic + ", doorType="
				+ doorType + "]";
	}

	/**
	 * Builds WashingMachine object.
	 */
	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		super.buildProduct(productDetails);
			this.loadCapacity = productDetails.get(LOAD_CAPACITY);
			this.isAutomatic = productDetails.get(IS_AUTOMATIC);
			this.doorType = productDetails.get(DOOR_TYPE);

	}

}