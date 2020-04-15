package com.reactiveworks.productdetails.service;

import java.util.List;

import com.reactiveworks.productdetails.model.Product;

/**
 * Interface for providing product service.
 */
public interface IProductService {
	
	/**
	 * creates the list of products of the given type.
	 * @param productType the type of the products to be fetched from the database.
	 * @return the list of products of the given type.
	 */
	public List<Product> getProductForType(String productType);
}
