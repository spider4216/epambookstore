package com.epam.component.validation.exception;

/**
 * Validation exception
 * 
 * @author Yuriy Sirotenko
 */
public class ValidationException extends Exception {
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(String msg, Exception e) {
		super(msg, e);
	}
}
