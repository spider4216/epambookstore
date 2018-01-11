package com.epam.component.dao.exception;

public class DaoUserException extends Exception {
	public DaoUserException(String msg, Exception reason) {
		super(msg, reason);
	}
	
	public DaoUserException(String msg) {
		super(msg);
	}
}
