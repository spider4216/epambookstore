package com.epam.service.exception;

public class OrderToProductServiceException extends Exception {
	public OrderToProductServiceException(String msg) {
		super(msg);
	}
	
	public OrderToProductServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
