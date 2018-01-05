package com.epam.service;

import com.epam.component.dao.basket.MysqlBasketDao;
import com.epam.component.dao.basket.exception.DaoBasketException;
import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.entity.BasketEntity;
import com.epam.entity.Book;
import com.epam.service.exception.BasketServiceException;
import com.epam.service.exception.BookServiceException;

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
	
}
