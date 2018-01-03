package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import com.epam.component.dao.book.MysqlBookDao;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.Book;
import com.epam.service.exception.BookServiceException;

/**
 * Service for Book logic
 * 
 * @author Yuriy Sirotenko
 */
public class BookService {
	private MysqlBookDao bookDao;
	
	public BookService() throws BookServiceException {
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		try {
			bookDao = (MysqlBookDao)MYSQLFactory.getBookDao();
		} catch (DaoBookException e) {
			throw new BookServiceException("Cannot getting book dao", e);
		}
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
		Lang lang = null;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new BookServiceException("Problem with getting all books", e);
		}

		ArrayList<Book> bookCollection = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = bookDao.findBooks();
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";

			while (rs.next()) {
				Book book = new Book();
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name" + columnSuffix));
				book.setPrice(rs.getDouble("price"));
				book.setAuthor(rs.getString("author" + columnSuffix));
				book.setDescription(rs.getString("description" + columnSuffix));
				book.setIsbn(rs.getString("isbn"));
				book.setPage(rs.getInt("page"));
				book.setImgPath(rs.getString("img_path"));
				book.setCategoryId(rs.getInt("category_id"));
				bookCollection.add(book);
			}

			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException("Cannot find books", e);
		}
	}
	
	// TODO DRY
	public ArrayList<Book> findAllByCategoryId(Integer id) throws BookServiceException {
		Lang lang = null;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new BookServiceException("Problem with getting books by category", e);
		}

		ArrayList<Book> bookCollection = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = bookDao.findAllByCategoryId(id);
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";

			while (rs.next()) {
				Book book = new Book();
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name" + columnSuffix));
				book.setPrice(rs.getDouble("price"));
				book.setAuthor(rs.getString("author" + columnSuffix));
				book.setDescription(rs.getString("description" + columnSuffix));
				book.setIsbn(rs.getString("isbn"));
				book.setPage(rs.getInt("page"));
				book.setImgPath(rs.getString("img_path"));
				book.setCategoryId(rs.getInt("category_id"));
				bookCollection.add(book);
			}

			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException("Cannot find books", e);
		}
	}
}
