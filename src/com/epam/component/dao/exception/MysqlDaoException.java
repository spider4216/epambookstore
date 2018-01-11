package com.epam.component.dao.exception;

public class MysqlDaoException extends Exception {
	public MysqlDaoException(String msg, Exception reason) {
		super(msg, reason);
	}
}
