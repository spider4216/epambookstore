package com.epam.component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.epam.component.dao.MysqlBasketDao;
import com.epam.component.dao.MysqlBookDao;
import com.epam.component.dao.MysqlCategoryDao;
import com.epam.component.dao.MysqlUserDao;
import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.exception.MysqlDaoException;

public class MysqlDaoFactory extends DaoFactory {
	
	private static synchronized Connection createConnection() throws MysqlDaoException {
		ResourceBundle resource = ResourceBundle.getBundle("com.epam.config.db");
		String url = resource.getString("url");
		String driver = resource.getString("driver");
		String user = resource.getString("user");
		String pass = resource.getString("password");
		
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new MysqlDaoException("Cannot create DB connection", e);
		}

		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			throw new MysqlDaoException("Problem with connection", e);
		}
	}

	public IBookDao getBookDao() throws DaoBookException {
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoBookException("Problem with create connection", e);
		}
		
		return new MysqlBookDao(con);
	}
	
	public IUserDao getUserDao() throws DaoUserException {
		// TODO DRY
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoUserException("Problem with create connection", e);
		}
		
		return new MysqlUserDao(con);
	}
	
	public ICategoryDao getCategoryDao() throws DaoCategoryException {
		// TODO DRY
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoCategoryException("Problem with create connection", e);
		}
		
		return new MysqlCategoryDao(con);
	}
	
	public IBasketDao getBasketDao() throws DaoBasketException {
		// TODO DRY
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoBasketException("Problem with create connection", e);
		}
		
		return new MysqlBasketDao(con);
	}

}
