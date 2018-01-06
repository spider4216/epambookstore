package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	public ArrayList<BasketEntity> findAllProductsByUserId(Integer userId) throws BasketServiceException {
		Lang lang = null;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new BasketServiceException("Problem with getting all books from basket", e);
		}

		ArrayList<BasketEntity> basketCollection = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = basketDao.findAllByUserId(userId);
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";

			while (rs.next()) {
				Book book = new Book();
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				book.setId(rs.getInt("bk.id"));
				book.setName(rs.getString("bk.name" + columnSuffix));
				book.setPrice(rs.getDouble("bk.price"));
				book.setAuthor(rs.getString("bk.author" + columnSuffix));
				book.setDescription(rs.getString("bk.description" + columnSuffix));
				book.setIsbn(rs.getString("bk.isbn"));
				book.setPage(rs.getInt("bk.page"));
				book.setImgPath(rs.getString("bk.img_path"));
				book.setCategoryId(rs.getInt("bk.category_id"));
				
				BasketEntity basket = new BasketEntity();
				
				basket.setId(rs.getInt("bt.id"));
				basket.setUserId(rs.getInt("bt.user_id"));
				basket.setBookId(rs.getInt("bt.book_id"));
				basket.setCount(rs.getInt("bt.count"));
				basket.setBook(book);
				
				
				basketCollection.add(basket);
			}

			return basketCollection;
		} catch (DaoBasketException | SQLException e) {
			throw new BasketServiceException("Cannot find books in basket", e);
		}
	}
}
