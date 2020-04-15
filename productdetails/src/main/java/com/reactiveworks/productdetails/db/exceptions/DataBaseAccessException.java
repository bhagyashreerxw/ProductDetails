package com.reactiveworks.productdetails.db.exceptions;

/**
 * Represents the exceptions related to database access.
 */
public class DataBaseAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataBaseAccessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataBaseAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DataBaseAccessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataBaseAccessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataBaseAccessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
