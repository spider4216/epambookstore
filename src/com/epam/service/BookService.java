package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoException;
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
	
	public BookService() throws BookServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new BookServiceException("Cannot getting book dao", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			bookDao = (BookDao)MYSQLFactory.getBookDao();
		} catch (DaoBookException | DaoException e) {
			throw new BookServiceException(lang.getValue("service_book_dao_err"), e);
		}
	}

	/**
	 * Delete one book by entity
	 */
	public Boolean delete(BookEntity entity) throws DaoBookException {
		Boolean res = bookDao.deleteBook(entity.getId());
		
		return res;
	}

	/**
	 * Find book by id
	 */
	public BookEntity findById(Integer id) throws BookServiceException {
		try {
			ResultSet result = bookDao.findBook(id);
			BookEntity res = bookSetter(result);
			return res;
		} catch (SQLException | DaoBookException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books
	 */
	public ArrayList<BookEntity> findAll() throws BookServiceException {
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findBooks();

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find books with limit and position for pagination
	 */
	public ArrayList<BookEntity> findNextPageBooks(Integer offset, Integer limit) throws BookServiceException {		
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findBooksWithPagination(offset, limit);

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find books with limit and position for pagination and category id
	 */
	public ArrayList<BookEntity> findNextPageCategoryBooks(Integer categoryId, Integer offset, Integer limit) throws BookServiceException {		
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findBooksByCategoryIdWithPagination(categoryId, offset, limit);

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Search by name
	 */
	public ArrayList<BookEntity> searchByName(String name) throws BookServiceException {
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findAllLikeName(name);

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Search by name and category id
	 */
	public ArrayList<BookEntity> searchByNameAndCategoryId(String name, Integer categoryId) throws BookServiceException {
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findAllLikeNameByCategoryId(name, categoryId);

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by category id
	 */
	public ArrayList<BookEntity> findAllByCategoryId(Integer id) throws BookServiceException {
		try {
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = bookDao.findAllByCategoryId(id);

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}

			return bookCollection;
		} catch (DaoBookException | SQLException e) {
			throw new BookServiceException(lang.getValue("service_book_not_found"), e);
		}
	}
	
	/**
	 * Book setter
	 */
	private BookEntity bookSetter(ResultSet result) throws SQLException {
		BookEntity book = new BookEntity();
		
		String columnSuffix = lang.getColumnSuffix();
		book.setId(result.getInt("id"));
		book.setName(result.getString("name" + columnSuffix));
		book.setPrice(result.getDouble("price"));
		book.setAuthor(result.getString("author" + columnSuffix));
		book.setDescription(result.getString("description" + columnSuffix));
		book.setIsbn(result.getString("isbn"));
		book.setPage(result.getInt("page"));
		book.setCategoryId(result.getInt("category_id"));
		book.setImgPath(result.getString("img_path"));
		
		return book;
	}
}
