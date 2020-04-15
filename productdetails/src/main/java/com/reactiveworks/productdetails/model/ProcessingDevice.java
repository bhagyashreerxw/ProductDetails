package com.reactiveworks.productdetails.model;

import java.util.Map;

import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

public class ProcessingDevice extends Product {
	private String screenSize;
	private String os;
	private String batterySize;
	private String numCore;
	private String ram;
	private static final String SCREEN_SIZE = "ScreenSize";
	private static final String OS = "OS";
	private static final String BATTERY_SIZE = "BatterySize";
	private static final String NUM_CORE = "NumCore";
	private static final String RAM = "RAM";

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBatterySize() {
		return batterySize;
	}

	public void setBatterySize(String batterySize) {
		this.batterySize = batterySize;
	}

	public String getNumCore() {
		return numCore;
	}

	public void setNumCore(String numCore) {
		this.numCore = numCore;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	@Override
	public String toString() {
		return "ProcessingDevice [screenSize=" + screenSize + ", os=" + os + ", batterySize=" + batterySize
				+ ", numCore=" + numCore + ", ram=" + ram + "]";
	}

	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		super.buildProduct(productDetails);
		this.screenSize = productDetails.get(SCREEN_SIZE);
		this.os = productDetails.get(OS);
		this.batterySize = productDetails.get(BATTERY_SIZE);
		this.numCore = productDetails.get(NUM_CORE);
		this.ram = productDetails.get(RAM);
	}

}
