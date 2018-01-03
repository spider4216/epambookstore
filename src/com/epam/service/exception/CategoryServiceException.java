package com.epam.service.exception;

public class CategoryServiceException extends Exception {
	public CategoryServiceException(String msg) {
		super(msg);
	}
	
	public CategoryServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
