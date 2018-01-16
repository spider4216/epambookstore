package com.epam.component.dao.exception;

public class DaoOrderToProductException extends Exception {
	public DaoOrderToProductException(String msg) {
		super(msg);
	}
	
	public DaoOrderToProductException(String msg, Exception e) {
		super(msg, e);
	}
}
