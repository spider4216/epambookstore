package com.epam.component.dao.exception;

public class ConnectionPoolException extends Exception {
	public ConnectionPoolException(String msg) {
		super(msg);
	}
	
	public ConnectionPoolException(String msg, Exception e) {
		super(msg, e);
	}
}
