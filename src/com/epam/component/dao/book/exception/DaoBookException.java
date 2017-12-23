package com.epam.component.dao.book.exception;

public class DaoBookException extends Exception {
	public DaoBookException(String msg, Exception reason) {
		super(msg, reason);
	}
}
