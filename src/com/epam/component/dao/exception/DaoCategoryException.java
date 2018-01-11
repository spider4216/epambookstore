package com.epam.component.dao.exception;

public class DaoCategoryException extends Exception {
	public DaoCategoryException(String msg) {
		super(msg);
	}
	
	public DaoCategoryException(String msg, Exception e) {
		super(msg, e);
	}
}
