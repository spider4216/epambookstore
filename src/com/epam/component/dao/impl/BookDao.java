package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.CDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;

/**
 * Mysql Dao for Books
 * 
 * @author Yuriy Sirotenko
 */
public class BookDao extends CDao implements IBookDao {
	
	private final static String SQL_DELETE_BOOK = "DELETE FROM books WHERE id = ?";

	private final static String SQL_FIND_BOOKS_WITH_PAGINATION = "SELECT * FROM books LIMIT ?,?";

	private final static String SQL_FIND_BOOKS_BY_CATEGORY_ID_WITH_PAGINATION = "SELECT * FROM books WHERE category_id = ? LIMIT ?,?";

	private final static String SQL_FIND_BOOK = "SELECT * FROM books WHERE id = ?";
	
	private Lang lang = null;
	
	public BookDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Delete book by ID
	 */
	public Boolean deleteBook(Integer id) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_DELETE_BOOK);
			pr.setInt(IStatementIndex.FIRST, id);
			
			pr.executeUpdate();
			
			closeResources(pr);
			
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_delete_err"), e);
		}
	}
	
	/**
	 * Find all books according to pagination params
	 */
	public ArrayList<BookEntity> findBooksWithPagination(Integer offset, Integer limit) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOKS_WITH_PAGINATION);
			pr.setInt(IStatementIndex.FIRST, offset);
			pr.setInt(IStatementIndex.SECOND, limit);
			
			ResultSet result = pr.executeQuery();
			
			ArrayList<BookEntity> bookCollection = new ArrayList<>();

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			closeResources(pr, result);
			
			return bookCollection;
			
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books according to pagination params and category id
	 */
	public ArrayList<BookEntity> findBooksByCategoryIdWithPagination(Integer categoryId, Integer offset, Integer limit) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOKS_BY_CATEGORY_ID_WITH_PAGINATION);
			pr.setInt(IStatementIndex.FIRST, categoryId);
			pr.setInt(IStatementIndex.SECOND, offset);
			pr.setInt(IStatementIndex.THIRD, limit);
			
			ResultSet result = pr.executeQuery();
			
			ArrayList<BookEntity> bookCollection = new ArrayList<>();

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			closeResources(pr, result);
			
			return bookCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by name with like operator
	 */
	public ArrayList<BookEntity> findAllLikeName(String name) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String columnSuffix = lang.getColumnSuffix();
			
			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(IStatementIndex.FIRST, "%" + name + "%");
			
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = pr.executeQuery();

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			closeResources(pr, result);
			
			return bookCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by name with like operator and category id
	 */
	public ArrayList<BookEntity> findAllLikeNameByCategoryId(String name, Integer categoryId) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String columnSuffix = lang.getColumnSuffix();

			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ? AND category_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(IStatementIndex.FIRST, "%" + name + "%");
			pr.setInt(IStatementIndex.SECOND, categoryId);
			
			ArrayList<BookEntity> bookCollection = new ArrayList<>();
			ResultSet result = pr.executeQuery();

			while (result.next()) {
				bookCollection.add(bookSetter(result));
			}
			
			closeResources(pr, result);
			
			return bookCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}

	/**
	 * Find one book by id
	 */
	public BookEntity findBook(Integer id) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOK);
			pr.setInt(IStatementIndex.FIRST, id);
			
			ResultSet result = pr.executeQuery();
			result.next();
			
			BookEntity book = bookSetter(result);

			closeResources(pr, result);
			
			return book;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
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
		book.setYear(result.getInt("year"));
		
		return book;
	}
}
