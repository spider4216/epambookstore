package com.epam.component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.epam.component.dao.book.IBookDao;
import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.category.ICategoryDao;
import com.epam.component.dao.category.exception.DaoCategoryException;
import com.epam.component.dao.category.exception.MysqlCategoryDao;
import com.epam.component.dao.factory.exception.MysqlDaoException;
import com.epam.component.dao.user.IUserDao;
import com.epam.component.dao.user.MysqlUserDao;
import com.epam.component.dao.user.exception.DaoUserException;

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

}
