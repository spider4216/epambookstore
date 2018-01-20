package com.epam.component.dao.exception;

/**
 * Dao order to product (many to many) exception
 * 
 * @author Yuriy Sirotenko
 */
public class DaoOrderToProductException extends Exception {
	public DaoOrderToProductException(String msg) {
		super(msg);
	}
	
	public DaoOrderToProductException(String msg, Exception e) {
		super(msg, e);
	}
}
