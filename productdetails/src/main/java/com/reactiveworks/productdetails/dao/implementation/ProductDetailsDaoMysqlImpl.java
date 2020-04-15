package com.reactiveworks.productdetails.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.reactiveworks.productdetails.dao.IProductDetailsDao;
import com.reactiveworks.productdetails.db.DBUtil;
import com.reactiveworks.productdetails.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productdetails.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productdetails.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productdetails.model.Laptop;
import com.reactiveworks.productdetails.model.MobileDevice;
import com.reactiveworks.productdetails.model.TV;
import com.reactiveworks.productdetails.model.WashingMachine;
import com.reactiveworks.productdetails.model.Product;
import java.sql.Date;

/**
 * Mysql implementation of ProductServiceDao.
 */
public class ProductDetailsDaoMysqlImpl implements IProductDetailsDao {
	private static final Logger LOGGER_OBJ = Logger.getLogger(ProductDetailsDaoMysqlImpl.class);
	private static final String INSERT_QUERY = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_QUERY = "UPDATE product SET price=? WHERE productId=?;";
	private static final String DELETE_QUERY = "DELETE FROM product WHERE productId=?;";
	private static final String SELECT_QUERY = "SELECT * FROM product ;";
	private static final String PRODUCT_TYPE = "ProductType";
	private static final String LAPTOP = "Laptop";
	private static final String TV = "TV";
	private static final String MOBILE = "Mobile";
	private static final String WASHING_MACHINE = "WashinMachine";

	/**
	 * Gets the list of products from the database.
	 * 
	 * @return the list of products.
	 * @throws DBOperationFailureException    when operation on database fails.
	 * @throws InvalidDBRecordFormatException when format of database record is
	 *                                        invalid.
	 */
	@Override
	public List<Product> getProducts()
			throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException {

		LOGGER_OBJ.debug("execution of getProducts() started");
		List<Product> productsList = new ArrayList<Product>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(SELECT_QUERY);
			res = statement.executeQuery(SELECT_QUERY);
			ResultSetMetaData metaData = res.getMetaData();
			while (res.next()) {
				Product productObj;
				Map<String, String> productDetails = getProductDetails(metaData, res);
				if (productDetails.get(PRODUCT_TYPE).equalsIgnoreCase(LAPTOP)) {
					productObj = new Laptop();
					productObj.buildProduct(productDetails);
					//LOGGER_OBJ.debug(productObj);
					productsList.add(productObj);
				} else if (productDetails.get(PRODUCT_TYPE).equalsIgnoreCase(TV)) {
					productObj = new TV();
					productObj.buildProduct(productDetails);
					productsList.add(productObj);
				} else if (productDetails.get(PRODUCT_TYPE).equalsIgnoreCase(WASHING_MACHINE)) {
					productObj = new WashingMachine();
					productObj.buildProduct(productDetails);
					productsList.add(productObj);
				} else {
					productObj = new MobileDevice();
					productObj.buildProduct(productDetails);

					productsList.add(productObj);
				}

			}
		} catch (SQLException sqlExp) {

			LOGGER_OBJ.error("unable to access  database");
			throw new DataBaseAccessException("unable to access database" , sqlExp);
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}
		LOGGER_OBJ.debug(productsList);
		return productsList;
	}

	/**
	 * Updates the product in the database.
	 * 
	 * @param product product to be updated.
	 * @param price   the new price of the product.
	 * @throws DataBaseAccessException     when unable to access the database.
	 * @throws DBOperationFailureException when operation on database fails.
	 */
	@Override
	public void updateProduct(Product product, int price) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of updateProduct() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setInt(1, price);

			statement.setString(2, product.getProductId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access  database");
			throw new DataBaseAccessException("unable to update  product with id "+product.getProductId() , exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of updateProduct() completed");
	}

	/**
	 * Inserts products into the database.
	 * 
	 * @param products list of products to be inserted into the database.
	 */
	@Override
	public void insertProducts(List<Product> products) {
		LOGGER_OBJ.debug("execution of insertProducts() started.");
		products.stream().forEach(product -> {
			try {
				insertProduct(product);
			} catch (DataBaseAccessException dbAccessExp) {
				LOGGER_OBJ.error(dbAccessExp.getMessage());
				dbAccessExp.printStackTrace();
			} catch (DBOperationFailureException operationFailExp) {
				LOGGER_OBJ.error(operationFailExp.getMessage());
				operationFailExp.printStackTrace();
			}
		});
		LOGGER_OBJ.debug("execution of insertProducts() completed.");
	}

	/**
	 * Deletes product from the database.
	 * 
	 * @param product product to be deleted from the database.
	 * @throws DataBaseAccessException     when unable to access the database.
	 * @throws DBOperationFailureException when operation on the database fails.
	 */
	@Override
	public void deleteProduct(Product product) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of deleteProduct() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setString(1, product.getProductId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access the database");
			throw new DataBaseAccessException("unable to delete the product with id " +product.getProductId(), exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of deleteProduct() completed");

	}

	/**
	 * 
	 * @param metaData metadata of the database.
	 * @param res      database result.
	 * @return the details of the products from the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	private Map<String, String> getProductDetails(ResultSetMetaData metaData, ResultSet res)
			throws DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getProductDetails() started");
		Map<String, String> productDetails = new LinkedHashMap<String, String>();

		try {

			productDetails.put(metaData.getColumnName(1), res.getString(1));

			productDetails.put(metaData.getColumnName(2), res.getString(2));
			productDetails.put(metaData.getColumnName(3), res.getString(3));
			String price = Integer.toString(res.getInt(4));
			productDetails.put(metaData.getColumnName(4), price);
			String warranty = Integer.toString(res.getInt(5));
			productDetails.put(metaData.getColumnName(5), warranty);
			Date date = res.getDate(6);
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
			String dt = DATE_FORMAT.format(date);
			productDetails.put(metaData.getColumnName(6), dt);
			productDetails.put(metaData.getColumnName(7), res.getString(7));
			productDetails.put(metaData.getColumnName(8), res.getString(8));
			productDetails.put(metaData.getColumnName(9), res.getString(9));
			productDetails.put(metaData.getColumnName(10), res.getString(10));
			productDetails.put(metaData.getColumnName(11), res.getString(11));
			productDetails.put(metaData.getColumnName(12), res.getString(12));
			productDetails.put(metaData.getColumnName(13), res.getString(13));
			productDetails.put(metaData.getColumnName(14), res.getString(14));
			productDetails.put(metaData.getColumnName(15), res.getString(15));
			productDetails.put(metaData.getColumnName(16), res.getString(16));
			productDetails.put(metaData.getColumnName(17), res.getString(17));
			productDetails.put(metaData.getColumnName(18), res.getString(18));
			productDetails.put(metaData.getColumnName(19), res.getString(19));
		} catch (SQLException sqlExp) {
			LOGGER_OBJ.error("unable to access product database");
			throw new DataBaseAccessException("unable to access product database" + sqlExp);
		}
		LOGGER_OBJ.debug("execution of getProductDetails() completed");
		return productDetails;
	}

	/**
	 * Inserts product into the database.
	 * 
	 * @param product the product to be inserted into the database.
	 * @throws DataBaseAccessException     when unable to access the database.
	 * @throws DBOperationFailureException when operation on the database fails.
	 */
	private void insertProduct(Product product) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of insertProduct() started");

		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, product.getProductId());
			statement.setString(2, product.getProductName());
			statement.setString(3, product.getProductType());
			statement.setDouble(4, product.getPrice());
			statement.setInt(5, product.getWarranty());
			Date date = new Date(product.getManufecturedDt().getTime());
			statement.setDate(6, date);
			if (product.getProductType().equalsIgnoreCase(LAPTOP)) {
				statement.setString(19, ((Laptop) product).getGraphicCard());
				statement.setString(7, ((Laptop) product).getScreenSize());
			} else {
				statement.setString(19, "");
				statement.setString(7, "");
			}

			if (product.getProductType().equalsIgnoreCase(TV)) {
				statement.setString(13, ((TV) product).getDisplaySize());
				statement.setString(14, ((TV) product).getDisplayType());
				statement.setString(15, ((TV) product).getIsSmartTV());

			} else {
				statement.setString(13, "");
				statement.setString(14, "");
				statement.setString(15, "");
			}

			if (product.getProductType().equalsIgnoreCase(WASHING_MACHINE)) {
				statement.setString(16, ((WashingMachine) product).getLoadCapacity());
				statement.setString(17, ((WashingMachine) product).getIsAutomatic());
				statement.setString(18, ((WashingMachine) product).getDoorType());

			} else {
				statement.setString(16, "");
				statement.setString(17, "");
				statement.setString(18, "");
			}

			if (product.getProductType().equalsIgnoreCase(MOBILE)) {
				statement.setString(9, ((MobileDevice) product).getBatterySize());
				statement.setString(10, ((MobileDevice) product).getNumCore());
				statement.setString(8, ((MobileDevice) product).getOs());
				statement.setString(12, ((MobileDevice) product).getSimType());
				statement.setString(11, ((MobileDevice) product).getRam());

			} else {
				statement.setString(9, "");
				statement.setString(10, "");
				statement.setString(8, "");
				statement.setString(12, "");
				statement.setString(11, "");
			}

			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access database");
			throw new DataBaseAccessException("unable to insert product with id"+product.getProductId()+" into the database" + exp);
		} finally {

			DBUtil.cleanupdbresources(resultSet, statement, connection);

		}
		LOGGER_OBJ.debug("execution of insertProduct() completed");
	}

}
