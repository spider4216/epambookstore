package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.lang.Lang;
import com.epam.component.pagination.Pagination;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;

public class MysqlBookDao implements IBookDao {
	
	private Connection connection = null;
	
	public MysqlBookDao(Connection connection) {
		this.connection = connection;
	}

	public Integer insertBook(BookEntity entity) throws DaoBookException {
		String sqlInsert = "INSERT INTO books (name, price, author, description, isbn, pages) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setString(1, entity.getName());
			pr.setDouble(2, entity.getPrice());
			pr.setString(3, entity.getAuthor());
			pr.setString(4, entity.getDescription());
			pr.setString(5, entity.getIsbn());
			pr.setInt(6, entity.getPage());
			
			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoBookException("Cannot insert book", e);
		}
	}

	// TODO not checking
	public Boolean deleteBook(BookEntity entity) throws DaoBookException {
		String sqlDelete = "DELETE FROM books WHERE id = ?";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, entity.getId());
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot delete book", e);
		}
	}

	public ResultSet findBooks() throws DaoBookException {
		try {
			Pagination pager = (Pagination) ServiceLocator.getInstance().getService(ServiceLocatorEnum.PAGINATION);
			
			String sqlFind = "SELECT * FROM books LIMIT ?,?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, pager.getStartOffset());
			pr.setInt(2, pager.COUNT_ITEM);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException | ServiceLocatorException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
	
	// TODO DRY
	public ResultSet findNextPageBooks() throws DaoBookException {
		try {
			Pagination pager = (Pagination) ServiceLocator.getInstance().getService(ServiceLocatorEnum.PAGINATION);
			
			String sqlFind = "SELECT * FROM books LIMIT ?,?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, pager.getStartOffset() + pager.COUNT_ITEM);
			pr.setInt(2, pager.COUNT_ITEM);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException | ServiceLocatorException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
	
	public ResultSet findAllByCategoryId(Integer id) throws DaoBookException {
		String sqlFind = "SELECT * FROM books WHERE category_id = ?";		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
	
	public ResultSet findAllLikeName(String name) throws DaoBookException {
		try {
			Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";
			
			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(1, "%" + name + "%");
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException | ServiceLocatorException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
	
	public ResultSet findAllLikeNameByCategoryId(String name, Integer categoryId) throws DaoBookException {
		try {
			Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
			String columnSuffix = lang.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + lang.getLangAsString() : "";
			
			String sqlFind = "SELECT * FROM books WHERE name" + columnSuffix +" LIKE ? AND category_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(1, "%" + name + "%");
			pr.setInt(2, categoryId);
			
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException | ServiceLocatorException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
	
	public ResultSet findBook(Integer id) throws DaoBookException {
		String sqlFind = "SELECT * FROM books WHERE id = ?";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet rs = pr.executeQuery();
			rs.next();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
}
