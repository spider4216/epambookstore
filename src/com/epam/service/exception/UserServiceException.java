package com.epam.service.exception;

/**
 * User Service Exception
 * 
 * @author Yuriy Sirotenko
 */
public class UserServiceException extends Exception {
	public UserServiceException(String msg, Exception reason) {
		super(msg, reason);
	}

	public UserServiceException(String msg) {
		super(msg);
	}
}
