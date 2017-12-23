package com.epam.component.dao.factory;

import com.epam.component.dao.book.IBookDAO;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.user.IUserDao;
import com.epam.component.dao.user.exception.DaoUserException;

public abstract class DAOFactory {
	public static final int MYSQL = 1;
	
	public abstract IBookDAO getBookDAO() throws DaoBookException;
	
	public abstract IUserDao getUserDAO() throws DaoUserException;
	
	public static DAOFactory getDAOFactory(Integer whichFactory) {
		switch (whichFactory) {
			case MYSQL :
				return new MYSQLDAOFactory();
			default :
				return null;
		}
	}
}
