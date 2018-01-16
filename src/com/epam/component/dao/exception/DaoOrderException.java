package com.epam.component.dao.exception;

public class DaoOrderException extends Exception {
	public DaoOrderException(String msg) {
		super(msg);
	}
	
	public DaoOrderException(String msg, Exception e) {
		super(msg, e);
	}
}
