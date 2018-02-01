package com.epam.service;

import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.BasketDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BasketEntity;
import com.epam.service.exception.BasketServiceException;

/**
 * Basket Service
 * 
 * @author Yuriy Sirotenko
 */
public class BasketService {
	private final static Integer EMPTY_BASKET = 0;
	
	private BasketDao basketDao;
	
	private Lang lang;
	
	public BasketService() throws ServiceLocatorException, DaoBasketException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		basketDao = (BasketDao)MYSQLFactory.getBasketDao();
	}
	
	/**
	 * Insert product into basket
	 */
	public Boolean insert(BasketEntity entity) throws BasketServiceException {
		Integer res = null;
		try {
			res = basketDao.insert(entity);
		} catch (DaoBasketException e) {
			throw new BasketServiceException(lang.getValue("service_basket_insert_err"), e);
		}
		
		if (res <= EMPTY_BASKET) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check is product in basket
	 */
	public Boolean isProductInBasket(Integer productId, Integer userId) {
		try {
			basketDao.findOneByProductAndUserId(productId, userId);
			return true;
		} catch (DaoBasketException e) {
			return false;
		}
	}
	
	/**
	 * Find all user's products in basket
	 */
	public ArrayList<BasketEntity> findAllProductsByUserId(Integer userId) throws BasketServiceException {
		try {
			return basketDao.findAllByUserId(userId);
		} catch (DaoBasketException e) {
			throw new BasketServiceException(lang.getValue("service_basket_empty_err"), e);
		}
	}
	
	/**
	 * Delete product from basket
	 */
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws BasketServiceException {
		try {
			return basketDao.deleteByUserAndBookId(userId, bookId);
		} catch (DaoBasketException e) {
			throw new BasketServiceException(lang.getValue("cannot_delete_books_from_basket"), e);
		}
	}
	
	/**
	 * Total sum of products in basket
	 */
	public Double totalSumByCollection(ArrayList<BasketEntity> collection) {
		Double sum = 0.0;
		
		for (BasketEntity item : collection) {
			sum += item.getBook().getPrice() * item.getCount();
		}
		
		return sum;
	}
	
	/**
	 * Delete all books from user's basket
	 */
	public Boolean deleteUserBasketBooks(Integer userId) throws BasketServiceException {
		try {
			return basketDao.deleteAllByUserId(userId);
		} catch (DaoBasketException e) {
			throw new BasketServiceException(lang.getValue("cannot_delete_books_from_basket"), e);
		}
	}	
}
