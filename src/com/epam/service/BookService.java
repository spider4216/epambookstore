package com.epam.service;

import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.entity.Book;

/**
 * Service for Book logic
 * 
 * @author Yuriy Sirotenko
 */
public class BookService {
	private MysqlBookDao bookDao;
	
	public BookService(Book entity) throws DaoBookException {
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		bookDao = (MysqlBookDao)MYSQLFactory.getBookDao();
		bookDao.setBookEntity(entity);
	}
	
	public Boolean insert(Book entity) throws DaoBookException {
		Integer res = bookDao.insertBook();
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Book entity) throws DaoBookException {
		bookDao.setBookEntity(entity);
		
		return bookDao.deleteBook();
	}
	
	public Book findById(Integer id) throws DaoBookException {
		return bookDao.findBook(id);
	}
}
