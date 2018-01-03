package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import com.epam.component.dao.category.exception.DaoCategoryException;
import com.epam.component.dao.category.exception.MysqlCategoryDao;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.CategoryEntity;
import com.epam.service.exception.CategoryServiceException;

// TODO Навести порядок. DRY во всем DAO
public class CategoryService {
	private MysqlCategoryDao categoryDao;
	
	public CategoryService() throws CategoryServiceException {
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		try {
			categoryDao = (MysqlCategoryDao)MYSQLFactory.getCategoryDao();
		} catch (DaoCategoryException e) {
			throw new CategoryServiceException("Cannot getting category dao", e);
		}
	}
	
	public ArrayList<CategoryEntity> findAll() throws CategoryServiceException {
		Lang lang = null;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new CategoryServiceException("Problem with getting all categories", e);
		}

		ArrayList<CategoryEntity> categoryCollection = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = categoryDao.findAll();
			// TODO DRY
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";

			while (rs.next()) {
				CategoryEntity category = new CategoryEntity();
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name" + columnSuffix));
				categoryCollection.add(category);
			}

			return categoryCollection;
		} catch (DaoCategoryException | SQLException e) {
				throw new CategoryServiceException("Cannot find categories", e);
		}
	}
}
