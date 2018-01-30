package com.epam.component.dao.exception;

/**
 * Connection pool exception
 * 
 * @author Yuriy Sirotenko
 */
public class ConnectionPoolException extends Exception {
	public ConnectionPoolException(String msg, Exception e) {
		super(msg, e);
	}
}
