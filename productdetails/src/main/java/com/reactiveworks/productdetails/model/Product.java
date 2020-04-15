package com.reactiveworks.productdetails.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;

public class Product {
	private String productId;
	private String productName;
	private String productType;
	private int price;
	private int warranty;
	private Date manufecturedDt;
	private static final String PRODUCT_ID = "ProductId";
	private static final String PRODUCT_NAME = "ProductName";
	private static final String PRODUCT_TYPE = "ProductType";
	private static final String PRICE = "Price";
	private static final String WARRENTY = "Warranty";
	private static final String MANUFACTURED_DT = "ManufecturedDt";
	private static final Logger LOGGER_OBJ = Logger.getLogger("Product.class");

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public Date getManufecturedDt() {
		return manufecturedDt;
	}

	public void setManufecturedDt(Date manufecturedDt) {
		this.manufecturedDt = manufecturedDt;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productType=" + productType
				+ ", price=" + price + ", warranty=" + warranty + ", manufecturedDt=" + manufecturedDt + "]";
	}

	public void buildProduct(Map<String, String> productDetails) throws InvalidDBRecordFormatException {
		this.productId = productDetails.get(PRODUCT_ID);
		this.productName = productDetails.get(PRODUCT_NAME);
		this.productType = productDetails.get(PRODUCT_TYPE);
		try {
			int price = Integer.parseInt(productDetails.get(PRICE));
			this.price = price;
			int warrenty = Integer.parseInt(productDetails.get(WARRENTY));
			this.warranty = warrenty;
			//String date=productDetails.get(MANUFACTURED_DT);
			//LOGGER_OBJ.debug(productDetails.get(MANUFACTURED_DT));
			LOGGER_OBJ.debug(productDetails.get(MANUFACTURED_DT));
			Date date = new SimpleDateFormat("dd/mm/yyyy").parse(productDetails.get(MANUFACTURED_DT));
			this.manufecturedDt = date;
		} catch (NumberFormatException numFormatExp) {
			LOGGER_OBJ.debug("format of product field is invalid");
			throw new InvalidDBRecordFormatException("format of product field is invalid" + numFormatExp);

		} catch (ParseException parseExp) {
			LOGGER_OBJ.debug("format of date is invalid");
			throw new InvalidDBRecordFormatException("format of date is invalid" + parseExp);
		}

	}
}
