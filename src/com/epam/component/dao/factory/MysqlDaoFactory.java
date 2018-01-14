package com.epam.component.dao.factory;

import java.sql.Connection;

import com.epam.component.dao.MysqlBasketDao;
import com.epam.component.dao.MysqlBookDao;
import com.epam.component.dao.MysqlCategoryDao;
import com.epam.component.dao.MysqlUserDao;
import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.exception.MysqlDaoException;

public class MysqlDaoFactory extends DaoFactory {
	
	private ConnectionPool connectionPool;
	
	public MysqlDaoFactory() throws MysqlDaoException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new MysqlDaoException("cannot create connection pool", e);
		}
	}

	public IBookDao getBookDao() throws DaoBookException {
		Connection con = null;
		try {
			con = connectionPool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoBookException("Problem with create connection", e);
		}
		
		return new MysqlBookDao(con);
	}
	
	public IUserDao getUserDao() throws DaoUserException {
		Connection con = null;
		try {
			con = connectionPool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoUserException("Problem with create connection", e);
		}
		
		return new MysqlUserDao(con);
	}
	
	public ICategoryDao getCategoryDao() throws DaoCategoryException {
		Connection con = null;
		try {
			con = connectionPool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoCategoryException("Problem with create connection", e);
		}
		
		return new MysqlCategoryDao(con);
	}
	
	public IBasketDao getBasketDao() throws DaoBasketException {
		Connection con = null;
		try {
			con = connectionPool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoBasketException("Problem with create connection", e);
		}
		
		return new MysqlBasketDao(con);
	}

}
