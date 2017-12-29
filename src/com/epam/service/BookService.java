package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.entity.Book;
import com.epam.service.exception.BookServiceException;

/**
 * Service for Book logic
 * 
 * @author Yuriy Sirotenko
 */
public class BookService {
	private MysqlBookDao bookDao;
	
	public BookService() throws DaoBookException {
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		bookDao = (MysqlBookDao)MYSQLFactory.getBookDao();
	}
	
	public Boolean insert(Book entity) throws DaoBookException {
		Integer res = bookDao.insertBook(entity);
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Book entity) throws DaoBookException {
		return bookDao.deleteBook(entity);
	}
	
	public Book findById(Integer id) throws DaoBookException {
		return bookDao.findBook(id);
	}
	
	public ArrayList<Book> findAll() throws BookServiceException {
		ArrayList<Book> bookCollection = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = bookDao.findBooks();

			while (rs.next()) {
				Book book = new Book();
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setPrice(rs.getDouble("price"));
				book.setAuthor(rs.getString("author"));
				book.setDescription(rs.getString("description"));
				book.setIsbn(rs.getString("isbn"));
				book.setPage(rs.getInt("page"));
				bookCollection.add(book);
			}

			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException("Cannot find books", e);
		}
	}
}
