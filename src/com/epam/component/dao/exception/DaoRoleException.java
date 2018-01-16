package com.epam.component.dao.exception;

public class DaoRoleException extends Exception {
	public DaoRoleException(String msg) {
		super(msg);
	}
	
	
	public DaoRoleException(String msg, Exception e) {
		super(msg, e);
	}
}
