package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class MysqlBookDao implements IBookDao {
	
	private Lang lang = null;
	
	public MysqlBookDao() throws DaoBookException {
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
			String sqlDelete = "DELETE FROM books WHERE id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, id);
			
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
			String sqlFind = "SELECT * FROM books";
			Statement pr = connection.createStatement();
			ResultSet rs = pr.executeQuery(sqlFind);
			
			return rs;
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
			String sqlFind = "SELECT * FROM books LIMIT ?,?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, offset);
			pr.setInt(2, limit);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
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
			String sqlFind = "SELECT * FROM books WHERE category_id = ? LIMIT ?,?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, categoryId);
			pr.setInt(2, offset);
			pr.setInt(3, limit);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
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
			String sqlFind = "SELECT * FROM books WHERE category_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);

			ResultSet res = pr.executeQuery();
			
			return res;
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
			pr.setString(1, "%" + name + "%");
			
			ResultSet res = pr.executeQuery();
			
			return res;
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
			pr.setString(1, "%" + name + "%");
			pr.setInt(2, categoryId);
			
			ResultSet res = pr.executeQuery();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}

	/**
	 * Find one book by id
	 */
	public ResultSet findBook(Integer id) throws DaoBookException {
		String sqlFind = "SELECT * FROM books WHERE id = ?";
		
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBookException(lang.getValue("dao_book_not_found"), e);
		}
	}
}
