package com.reactiveworks.productdetails.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.reactiveworks.productdetails.dao.IProductDetailsDao;
import com.reactiveworks.productdetails.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productdetails.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productdetails.model.Laptop;
import com.reactiveworks.productdetails.model.MobileDevice;
import com.reactiveworks.productdetails.model.Product;
import com.reactiveworks.productdetails.model.TV;
import com.reactiveworks.productdetails.model.WashingMachine;

/**
 * csv implementation of ProductDetails Dao.
 */
public class ProductDetailsDaoCSVImpl implements IProductDetailsDao {
	private static final Logger LOGGER_OBJ = Logger.getLogger(ProductDetailsDaoCSVImpl.class);
	private static final String FILE_NAME = "ProductDetail.csv";
	private static final String PRODUCT_TYPE = "ProductType";
	private static final String LAPTOP = "laptop";
	private static final String TV = "tv";
	private static final String WASHING_MACHINE = "WashinMachine";

	/**
	 * Gets the list of products from the database.
	 * 
	 * @return the list of products.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	@Override
	public List<Product> getProducts() throws DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getProducts() started ");
		File file = new File(getClass().getClassLoader().getResource(FILE_NAME).getFile());
		List<Product> productsList;
		try (Stream<String> line = Files.lines(Paths.get(file.toURI()))) {
			String[] headerArr = Files.lines(Paths.get(file.toURI())).findFirst().get().split(",");
			productsList = line.skip(1).map(str -> {
				Map<String, String> map = parseCSVLine(str, headerArr);
				Product product;
				String prodType = map.get(PRODUCT_TYPE);

				if (prodType.equalsIgnoreCase(LAPTOP)) {
					product = new Laptop();
					try {
						product.buildProduct(map);
					} catch (InvalidDBRecordFormatException invDBRecordExp) {
						LOGGER_OBJ.error(invDBRecordExp.getMessage());
						invDBRecordExp.printStackTrace();
					}
				} else if (prodType.equalsIgnoreCase(TV)) {
					product = new TV();
					try {
						product.buildProduct(map);
					} catch (InvalidDBRecordFormatException invDBRecordExp) {
						LOGGER_OBJ.error(invDBRecordExp.getMessage());
						invDBRecordExp.printStackTrace();
					}
				} else if (prodType.equalsIgnoreCase(WASHING_MACHINE)) {
					product = new WashingMachine();
					try {
						product.buildProduct(map);
					} catch (InvalidDBRecordFormatException invDBRecordExp) {
						LOGGER_OBJ.error(invDBRecordExp.getMessage());
						invDBRecordExp.printStackTrace();
					}
				} else {
					product = new MobileDevice();
					try {
						product.buildProduct(map);
					} catch (InvalidDBRecordFormatException invDBRecordExp) {
						LOGGER_OBJ.error(invDBRecordExp.getMessage());
						invDBRecordExp.printStackTrace();
					}
				}
				return (Product) product;
			}).collect(Collectors.toList());

		} catch (IOException ioExp) {
			LOGGER_OBJ.error("unable to access the database");
			throw new DataBaseAccessException("unable to access the database "+FILE_NAME , ioExp);

		}
		LOGGER_OBJ.debug("execution of getProducts() completed ");
		return productsList;
	}

	/**
	 * converts one line in csv file to the product object.
	 * 
	 * @param str the string which has to be parsed.
	 * @return the object of the product.
	 * @throws InvalidDBRecordFormatException when the operation is not supported by
	 *                                        the database.
	 */
	private Map<String, String> parseCSVLine(String str, String[] headerArr) {
		LOGGER_OBJ.debug("execution of parseCSVLine() started ");
		Map<String, String> productDetails = new LinkedHashMap<String, String>();
		String[] rowArray = str.split(",");
		for (int index = 0; index < headerArr.length; index++) {
			productDetails.put(headerArr[index], rowArray[index]);
		}
		LOGGER_OBJ.debug("execution of parseCSVLine() completed ");
		return productDetails;
	}

	/**
	 * Updates the product in the database.
	 * 
	 * @param product product to be updated.
	 * @param price   the new price of the product.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void updateProduct(Product product, int price) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("update operation is not supported by the database");
	}

	/**
	 * Inserts products into the database.
	 * 
	 * @param products list of products to be inserted into the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void insertProducts(List<Product> products) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("update operation is not supported by the database");

	}

	/**
	 * Deletes product from the database.
	 * 
	 * @param product product to be deleted from the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void deleteProduct(Product product) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("update operation is not supported by the database");

	}

}
