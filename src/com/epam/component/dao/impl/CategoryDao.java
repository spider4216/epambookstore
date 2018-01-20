package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * Mysql category dao
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryDao implements ICategoryDao {

	private final static String SQL_FIND_ALL = "SELECT * FROM category";

	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM category WHERE id = ?";
	
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
			
			ResultSet res = pr.executeQuery(SQL_FIND_ALL);

			return res;
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
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
}