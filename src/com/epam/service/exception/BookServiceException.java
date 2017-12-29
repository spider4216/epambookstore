package com.epam.service.exception;

public class BookServiceException extends Exception {
	public BookServiceException(String msg, Exception reason) {
		super(msg, reason);
	}

	public BookServiceException(String msg) {
		super(msg);
	}
}
