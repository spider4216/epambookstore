package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * Mysql category dao
 * 
 * @author Yuriy Sirotenko
 */
public class MysqlCategoryDao implements ICategoryDao {
	private Connection connection = null;
	
	private Lang lang = null;
	
	public MysqlCategoryDao(Connection connection) throws DaoCategoryException {
		this.connection = connection;
		
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
			String sqlFind = "SELECT * FROM category";
			Statement pr = connection.createStatement();
			
			ResultSet res = pr.executeQuery(sqlFind);
			
			return res;
		} catch (SQLException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
	
	/**
	 * Find category by id
	 */
	public ResultSet findOneById(Integer id) throws DaoCategoryException {		
		try {
			String sqlFind = "SELECT * FROM category WHERE id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
}
