package com.epam.service.exception;

/**
 * Basket Service Exception
 * 
 * @author Yuriy Sirotenko
 */
public class BasketServiceException extends Exception {
	public BasketServiceException(String msg) {
		super(msg);
	}
	
	public BasketServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
