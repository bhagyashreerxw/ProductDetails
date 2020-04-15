package com.reactiveworks.productdetails.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.reactiveworks.productdetails.model.Product;
import com.reactiveworks.productdetails.service.implementation.ProductService;

/**
 * Test class for ProductService.
 */
public class ProductServiceTest {
	private static final Logger LOGGER_OBJ = Logger.getLogger(ProductServiceTest.class);

	/**
	 * Tests the working of getProductForType() in positive Scenario.
	 */
	@Test
	public void getProductForTypeServiceVerification() {
		LOGGER_OBJ.debug("execution of getProductForTypeServiceVerification() started.");
		ProductService prodServiceObj = new ProductService();
		List<Product> mobiles = prodServiceObj.getProductForType("Mobile");
		int actualProductListSize = mobiles.size();
		int expectedProductListSize = 3;
		assertTrue(actualProductListSize == expectedProductListSize);
		LOGGER_OBJ.debug("execution of getProductForTypeServiceVerification() completed.");
	}

	/**
	 * Tests the working of getProductForType() in negative Scenario.
	 */
	@Test
	public void getProductForTypeServiceFailTest() {
		LOGGER_OBJ.debug("execution of getProductForTypeServiceFailTest() started.");
		ProductService prodServiceObj = new ProductService();
		List<Product> mobiles = prodServiceObj.getProductForType("Mobile");
		int actualProductListSize = mobiles.size();
		int expectedProductListSize = 2;
		assertTrue(actualProductListSize != expectedProductListSize);
		LOGGER_OBJ.debug("execution of getProductForTypeServiceFailTest() completed.");
	}

}
