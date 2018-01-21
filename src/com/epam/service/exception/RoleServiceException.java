package com.epam.service.exception;

/**
 * Role Service Exception
 * 
 * @author Yuriy Sirotenko
 */
public class RoleServiceException extends Exception {
	public RoleServiceException(String msg) {
		super(msg);
	}
	
	public RoleServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
