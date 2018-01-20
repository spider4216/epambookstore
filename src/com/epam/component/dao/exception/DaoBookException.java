package com.epam.component.dao.exception;

/**
 * Dao book exception
 * 
 * @author Yuriy Sirotenko
 */
public class DaoBookException extends Exception {
	public DaoBookException(String msg, Exception reason) {
		super(msg, reason);
	}
}
