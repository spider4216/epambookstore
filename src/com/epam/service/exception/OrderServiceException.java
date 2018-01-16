package com.epam.service.exception;

public class OrderServiceException extends Exception {
	public OrderServiceException(String msg) {
		super(msg);
	}
	
	public OrderServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
