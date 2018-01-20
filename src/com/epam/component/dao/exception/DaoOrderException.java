package com.epam.component.dao.exception;

/**
 * Dao order exception
 * 
 * @author Yuriy Sirotenko
 */
public class DaoOrderException extends Exception {
	public DaoOrderException(String msg) {
		super(msg);
	}
	
	public DaoOrderException(String msg, Exception e) {
		super(msg, e);
	}
}
