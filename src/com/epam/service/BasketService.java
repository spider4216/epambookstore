package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.epam.component.dao.basket.MysqlBasketDao;
import com.epam.component.dao.basket.exception.DaoBasketException;
import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.category.exception.DaoCategoryException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BasketEntity;
import com.epam.entity.Book;
import com.epam.entity.CategoryEntity;
import com.epam.service.exception.BasketServiceException;
import com.epam.service.exception.BookServiceException;
import com.epam.service.exception.CategoryServiceException;

public class BasketService {
	private MysqlBasketDao basketDao;
	
	public BasketService() throws BasketServiceException {
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		try {
			basketDao = (MysqlBasketDao)MYSQLFactory.getBasketDao();
		} catch (DaoBasketException e) {
			throw new BasketServiceException("Cannot getting products from basket dao", e);
		}
	}
	
	public Boolean insert(BasketEntity entity) throws BasketServiceException {
		Integer res = null;
		try {
			res = basketDao.insert(entity);
		} catch (DaoBasketException e) {
			throw new BasketServiceException("Cannot insert product to basket", e);
		}
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean isProductInBasket(Integer productId, Integer userId) throws BasketServiceException {
		try {
			basketDao.findOneByProductAndUserId(productId, userId);
			return true;
		} catch (DaoBasketException e) {
			return false;
		}
	}
}
