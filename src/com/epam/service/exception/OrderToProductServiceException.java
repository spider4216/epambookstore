package com.epam.service.exception;

/**
 * Order To Product service exception
 * 
 * @author Yuriy Sirotenko
 */
public class OrderToProductServiceException extends Exception {
	public OrderToProductServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
