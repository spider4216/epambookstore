package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.BasketDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BasketEntity;
import com.epam.entity.BookEntity;
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
	
	public BasketService() throws BasketServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new BasketServiceException("Problem with lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			basketDao = (BasketDao)MYSQLFactory.getBasketDao();
		} catch (DaoBasketException e) {
			throw new BasketServiceException("service_basket_create_dao_err", e);
		}
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
			ArrayList<BasketEntity> basketCollection = new ArrayList<>();
			ResultSet res = basketDao.findAllByUserId(userId);

			while (res.next()) {
				basketCollection.add(basketSetter(res));
			}
			
			return basketCollection;
		} catch (DaoBasketException | SQLException e) {
			throw new BasketServiceException(lang.getValue("service_basket_empty_err"), e);
		}
	}
	
	/**
	 * Basket setter
	 */
	private BasketEntity basketSetter(ResultSet result) throws SQLException {
		String columnSuffix = lang.getColumnSuffix();
		
		BookEntity book = new BookEntity();
		book.setId(result.getInt("bk.id"));
		book.setName(result.getString("bk.name" + columnSuffix));
		book.setPrice(result.getDouble("bk.price"));
		book.setAuthor(result.getString("bk.author" + columnSuffix));
		book.setDescription(result.getString("bk.description" + columnSuffix));
		book.setIsbn(result.getString("bk.isbn"));
		book.setPage(result.getInt("bk.page"));
		book.setImgPath(result.getString("bk.img_path"));
		book.setCategoryId(result.getInt("bk.category_id"));
		
		BasketEntity basket = new BasketEntity();
		
		basket.setId(result.getInt("bt.id"));
		basket.setUserId(result.getInt("bt.user_id"));
		basket.setBookId(result.getInt("bt.book_id"));
		basket.setCount(result.getInt("bt.count"));
		basket.setCreateDate(result.getString("bt.create_date"));
		basket.setBook(book);
		
		return basket;
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
