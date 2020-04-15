package com.reactiveworks.productdetails.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.reactiveworks.productdetails.dao.IProductDetailsDao;
import com.reactiveworks.productdetails.dao.ProductDetailsDaoFactory;
import com.reactiveworks.productdetails.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productdetails.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productdetails.model.Product;
import com.reactiveworks.productdetails.service.IProductService;

/**
 * This class provides product services.
 */
public class ProductService implements IProductService {
	private static final Logger LOGGER_OBJ = Logger.getLogger(ProductService.class);
	List<Product> poductsList;

	public ProductService() {

		try {
			IProductDetailsDao productDetailsDaoObj = ProductDetailsDaoFactory.getInstance();
			poductsList = productDetailsDaoObj.getProducts();

		} catch (DataBaseAccessException | DBOperationFailureException | InvalidDBRecordFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates the list of products of the given type.
	 * 
	 * @param productType the type of the products to be fetched from the database.
	 * @return the list of products of the given type.
	 */
	@Override
	public List<Product> getProductForType(String productType) {
		LOGGER_OBJ.debug("execution of getProductForType() started");
		List<Product> products = poductsList.stream()
				.filter(product -> product.getProductType().equalsIgnoreCase(productType)).collect(Collectors.toList());
		LOGGER_OBJ.debug("execution of getProductForType() completed");
		return products;
	}

}
