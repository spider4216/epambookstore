package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.CategoryDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.CategoryEntity;
import com.epam.service.exception.CategoryServiceException;

/**
 * Service for category
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryService {
	private CategoryDao categoryDao;
	
	private Lang lang;
	
	public CategoryService() throws CategoryServiceException, ServiceLocatorException, DaoCategoryException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		categoryDao = (CategoryDao)MYSQLFactory.getCategoryDao();
	}

	/**
	 * Find all categories
	 */
	public ArrayList<CategoryEntity> findAll() throws CategoryServiceException {
		try {
			ArrayList<CategoryEntity> categoryCollection = new ArrayList<>();
			ResultSet res = categoryDao.findAll();

			while (res.next()) {
				categoryCollection.add(categorySetter(res));
			}
			
			return categoryCollection;
		} catch (DaoCategoryException | SQLException e) {
			throw new CategoryServiceException(lang.getValue("service_category_empty_err"), e);
		}
	}

	/**
	 * Find category by id
	 */
	public CategoryEntity findOneById(Integer id) throws CategoryServiceException {
		try {
			ResultSet res = categoryDao.findOneById(id);
			return categorySetter(res);
		} catch (DaoCategoryException | SQLException e) {
			throw new CategoryServiceException(lang.getValue("service_category_empty_err"), e);
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
