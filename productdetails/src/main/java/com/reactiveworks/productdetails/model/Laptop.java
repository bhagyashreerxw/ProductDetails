package com.reactiveworks.productdetails.model;

import java.util.Map;

import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

/**
 * This class Represents Laptop object.
 */
public class Laptop extends ProcessingDevice {

	private String graphicCard;
	private static final String GRAPHIC_CARD = "GraphicsCard";

	public String getGraphicCard() {
		return graphicCard;
	}

	public void setGraphicCard(String graphicCard) {
		this.graphicCard = graphicCard;
	}

	@Override
	public String toString() {
		return "Laptop [graphicCard=" + graphicCard + "]";
	}

	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		super.buildProduct(productDetails);
		this.graphicCard = productDetails.get(GRAPHIC_CARD);
	}
}
