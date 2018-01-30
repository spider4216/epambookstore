package com.epam.service.exception;

/**
 * Book Service Exception
 * 
 * @author Yuriy Sirotenko
 */
public class BookServiceException extends Exception {
	public BookServiceException(String msg, Exception reason) {
		super(msg, reason);
	}
}
