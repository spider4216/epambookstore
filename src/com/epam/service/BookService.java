package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.BookDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;
import com.epam.service.exception.BookServiceException;

/**
 * Service for Book logic
 * 
 * @author Yuriy Sirotenko
 */
public class BookService {
	private BookDao bookDao;
	private Lang lang;
	
	public BookService() throws BookServiceException, ServiceLocatorException, DaoBookException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		bookDao = (BookDao)MYSQLFactory.getBookDao();
	}

	/**
	 * Find book by id
	 */
	public BookEntity findById(Integer id) throws BookServiceException {
		try {
			return bookDao.findBook(id);
		} catch (DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find books with limit and position for pagination
	 */
	public ArrayList<BookEntity> findNextPageBooks(Integer offset, Integer limit) throws BookServiceException {		
		try {
			return bookDao.findBooksWithPagination(offset, limit);
		} catch (DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find books with limit and position for pagination and category id
	 */
	public ArrayList<BookEntity> findNextPageCategoryBooks(Integer categoryId, Integer offset, Integer limit) throws BookServiceException {		
		try {
			return  bookDao.findBooksByCategoryIdWithPagination(categoryId, offset, limit);
		} catch (DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Search by name
	 */
	public ArrayList<BookEntity> searchByName(String name) throws BookServiceException {
		try {
			return bookDao.findAllLikeName(name);
		} catch (DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Search by name and category id
	 */
	public ArrayList<BookEntity> searchByNameAndCategoryId(String name, Integer categoryId) throws BookServiceException {
		try {
			return bookDao.findAllLikeNameByCategoryId(name, categoryId);
		} catch (DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
}
