package com.epam.component.dao.exception;

/**
 * General dao exception
 * 
 * @author Yuriy Sirotenko
 */
public class DaoException extends Exception {
	public DaoException(String msg) {
		super(msg);
	}
	
	public DaoException(String msg, Exception reason) {
		super(msg, reason);
	}
}
