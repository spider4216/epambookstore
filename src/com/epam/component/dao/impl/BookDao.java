package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.IBookDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * Mysql Dao for Books
 * 
 * @author Yuriy Sirotenko
 */
public class BookDao implements IBookDao {
	
	private final static String SQL_DELETE_BOOK = "DELETE FROM books WHERE id = ?";

	private final static String SQL_FIND_BOOKS = "SELECT * FROM books";
	
	private final static String SQL_FIND_BOOKS_WITH_PAGINATION = "SELECT * FROM books LIMIT ?,?";

	private final static String SQL_FIND_BOOKS_BY_CATEGORY_ID_WITH_PAGINATION = "SELECT * FROM books WHERE category_id = ? LIMIT ?,?";

	private final static String SQL_FIND_ALL_BY_CATEGORY_ID = "SELECT * FROM books WHERE category_id = ?";

	private final static String SQL_FIND_BOOK = "SELECT * FROM books WHERE id = ?";
	
	private Lang lang = null;
	
	public BookDao() throws DaoBookException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoBookException("Problem with book dao", e);
		}
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
			
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_delete_err"), e);
		}
	}

	/**
	 * Find All books
	 */
	public ResultSet findBooks() throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			Statement pr = connection.createStatement();

			return pr.executeQuery(SQL_FIND_BOOKS);
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books according to pagination params
	 */
	public ResultSet findBooksWithPagination(Integer offset, Integer limit) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOKS_WITH_PAGINATION);
			pr.setInt(IStatementIndex.FIRST, offset);
			pr.setInt(IStatementIndex.SECOND, limit);
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books according to pagination params and category id
	 */
	public ResultSet findBooksByCategoryIdWithPagination(Integer categoryId, Integer offset, Integer limit) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOKS_BY_CATEGORY_ID_WITH_PAGINATION);
			pr.setInt(IStatementIndex.FIRST, categoryId);
			pr.setInt(IStatementIndex.SECOND, offset);
			pr.setInt(IStatementIndex.THIRD, limit);
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by category id
	 */
	public ResultSet findAllByCategoryId(Integer id) throws DaoBookException {		
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_CATEGORY_ID);
			pr.setInt(IStatementIndex.FIRST, id);

			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by name with like operator
	 */
	public ResultSet findAllLikeName(String name) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String columnSuffix = lang.getColumnSuffix();
			
			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(IStatementIndex.FIRST, "%" + name + "%");
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
	
	/**
	 * Find all books by name with like operator and category id
	 */
	public ResultSet findAllLikeNameByCategoryId(String name, Integer categoryId) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String columnSuffix = lang.getColumnSuffix();

			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ? AND category_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(IStatementIndex.FIRST, "%" + name + "%");
			pr.setInt(IStatementIndex.SECOND, categoryId);
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}

	/**
	 * Find one book by id
	 */
	public ResultSet findBook(Integer id) throws DaoBookException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_BOOK);
			pr.setInt(IStatementIndex.FIRST, id);
			
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
}
