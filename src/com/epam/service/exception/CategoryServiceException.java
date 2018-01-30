package com.epam.service.exception;

/**
 * Category Service Exception
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryServiceException extends Exception {
	public CategoryServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
