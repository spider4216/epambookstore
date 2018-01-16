package com.epam.service.exception;

public class RoleServiceException extends Exception {
	public RoleServiceException(String msg) {
		super(msg);
	}
	
	public RoleServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
