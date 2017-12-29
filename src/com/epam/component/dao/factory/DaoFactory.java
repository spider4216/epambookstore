package com.epam.component.dao.factory;

import com.epam.component.dao.book.IBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.user.IUserDao;
import com.epam.component.dao.user.exception.DaoUserException;

public abstract class DaoFactory {
	public static final int MYSQL = 1;
	
	public abstract IBookDao getBookDao() throws DaoBookException;
	
	public abstract IUserDao getUserDao() throws DaoUserException;
	
	public static DaoFactory getDaoFactory(Integer whichFactory) {
		switch (whichFactory) {
			case MYSQL :
				return new MysqlDaoFactory();
			default :
				return null;
		}
	}
}
