package com.reactiveworks.productdetails.model;

import java.util.Map;

import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

public class TV extends Product {
	private String displaySize;
	private String displayType;
	private String isSmartTV;
	private static final String DISPLAY_SIZE = "DisplaySize";
	private static final String DISPLAY_TYPE = "DisplayType";
	private static final String IS_SMART_TV = "isSmartTV";



	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public String getIsSmartTV() {
		return isSmartTV;
	}

	public void setIsSmartTV(String isSmartTV) {
		this.isSmartTV = isSmartTV;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}


	@Override
	public String toString() {
		return "TV [displaySize=" + displaySize + ", displayType=" + displayType + ", isSmartTV=" + isSmartTV + "]";
	}

	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		super.buildProduct(productDetails);
	
			this.displaySize = productDetails.get(DISPLAY_SIZE);
			this.displayType = productDetails.get(DISPLAY_TYPE);
			this.isSmartTV = productDetails.get(IS_SMART_TV);


	}
}