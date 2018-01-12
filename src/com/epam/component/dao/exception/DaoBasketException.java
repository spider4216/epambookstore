package com.epam.component.dao.exception;

public class DaoBasketException extends Exception {
	public DaoBasketException(String msg) {
		super(msg);
	}
	
	
	public DaoBasketException(String msg, Exception e) {
		super(msg, e);
	}
}