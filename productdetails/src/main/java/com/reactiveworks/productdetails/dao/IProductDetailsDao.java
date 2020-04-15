package com.reactiveworks.productdetails.dao;

import java.util.List;

import com.reactiveworks.productdetails.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productdetails.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productdetails.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productdetails.model.Product;

/**
 * 
 */
public interface IProductDetailsDao {

	/**
	 * Gets the list of products from the database.
	 * 
	 * @return the list of products.
	 */
	public List<Product> getProducts()
			throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException;

	/**
	 * Updates the product in the database.
	 * 
	 * @param product product to be updated.
	 * @param price   the new price of the product.
	 */
	public void updateProduct(Product product, int price)
			throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

	/**
	 * Inserts products into the database.
	 * 
	 * @param products list of products to be inserted into the database.
	 */
	public void insertProducts(List<Product> products) throws OperationNotSupportedException;

	/**
	 * Deletes product from the database.
	 * 
	 * @param product product to be deleted from the database.
	 */
	public void deleteProduct(Product product) throws OperationNotSupportedException,DataBaseAccessException,DBOperationFailureException;

}
