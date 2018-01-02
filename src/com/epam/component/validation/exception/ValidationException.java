package com.epam.component.validation.exception;

public class ValidationException extends Exception {
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(String msg, Exception e) {
		super(msg, e);
	}
}
