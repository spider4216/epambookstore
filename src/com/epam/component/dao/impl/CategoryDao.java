package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * Mysql categories dao
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryDao implements ICategoryDao {

	private final static String SQL_FIND_ALL = "SELECT * FROM categories";

	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM categories WHERE id = ?";
	
	private Lang lang = null;
	
	public CategoryDao() throws DaoCategoryException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoCategoryException("cannot get lang", e);
		}
	}

	/**
	 * Find all categories
	 */
	public ResultSet findAll() throws DaoCategoryException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			Statement pr = connection.createStatement();
			
			return pr.executeQuery(SQL_FIND_ALL);
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
	
	/**
	 * Find category by id
	 */
	public ResultSet findOneById(Integer id) throws DaoCategoryException {		
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
}
