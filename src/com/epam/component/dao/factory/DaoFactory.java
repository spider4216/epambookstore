package com.epam.component.dao.factory;

import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoUserException;

public abstract class DaoFactory {
	public static final int MYSQL = 1;
	
	public abstract IBookDao getBookDao() throws DaoBookException;
	
	public abstract IUserDao getUserDao() throws DaoUserException;
	
	public abstract ICategoryDao getCategoryDao() throws DaoCategoryException;

	public abstract IBasketDao getBasketDao() throws DaoBasketException;
	
	public static DaoFactory getDaoFactory(Integer whichFactory) {
		switch (whichFactory) {
			case MYSQL :
				return new MysqlDaoFactory();
			default :
				return null;
		}
	}
}
