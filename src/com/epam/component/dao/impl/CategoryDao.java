package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.component.dao.CDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.CategoryEntity;

/**
 * Mysql categories dao
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryDao extends CDao implements ICategoryDao {

	private final static String SQL_FIND_ALL = "SELECT * FROM categories";

	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM categories WHERE id = ?";
	
	private Lang lang = null;
	
	public CategoryDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Find all categories
	 */
	public ArrayList<CategoryEntity> findAll() throws DaoCategoryException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			Statement pr = connection.createStatement();
			
			ArrayList<CategoryEntity> categoryCollection = new ArrayList<>();
			ResultSet result = pr.executeQuery(SQL_FIND_ALL);
			
			while (result.next()) {
				categoryCollection.add(categorySetter(result));
			}
			
			closeResources(pr, result);
			
			return categoryCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
	
	/**
	 * Find category by id
	 */
	public CategoryEntity findOneById(Integer id) throws DaoCategoryException {		
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet result = pr.executeQuery();
			result.next();
			
			CategoryEntity category = categorySetter(result);
			closeResources(pr, result);
			
			return category;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoCategoryException(lang.getValue("dao_category_empty_err"), e);
		}
	}
	
	/**
	 * Setter category entity
	 */
	private CategoryEntity categorySetter(ResultSet result) throws SQLException {
		String columnSuffix = lang.getColumnSuffix();
		CategoryEntity category = new CategoryEntity();
		category.setId(result.getInt("id"));
		category.setName(result.getString("name" + columnSuffix));
		
		return category;
	}
}
