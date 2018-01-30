package com.epam.component.dao.exception;

/**
 * Dao role exception
 * 
 * @author Yuriy Sirotenko
 */
public class DaoRoleException extends Exception {
	public DaoRoleException(String msg, Exception e) {
		super(msg, e);
	}
}
