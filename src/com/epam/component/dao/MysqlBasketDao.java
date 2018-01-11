package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.entity.BasketEntity;
import com.epam.entity.BookEntity;
import com.epam.entity.UserEntity;

public class MysqlBasketDao implements IBasketDao {
	private Connection connection = null;
	
	public MysqlBasketDao(Connection connection) {
		this.connection = connection;
	}
	
	public Integer insert(BasketEntity entity) throws DaoBasketException {
		String sqlInsert = "INSERT INTO basket (user_id, book_id, count, is_history) VALUES (?, ?, ?, ?)";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getBookId());
			pr.setInt(3, entity.getCount());
			pr.setInt(4, entity.getIsHistory());

			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot insert product into basket", e);
		}
	}
	
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException {
		String sqlFind = "SELECT * FROM basket WHERE book_id = ? AND user_id = ? AND is_history = 0";		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, productId);
			pr.setInt(2, userId);
			ResultSet rs = pr.executeQuery();
			rs.next();
						
			if (rs.getRow() <= 0) {
				throw new DaoBasketException("Product in basket not found");
			}
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot find basket by product and user id", e);
		}
	}
	
	public ResultSet findAllByUserId(Integer userId) throws DaoBasketException {
		String sqlFind = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ? AND is_history = 0";
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot find basket products by user id", e);
		}
	}
	
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException {
		String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND book_id = ? AND is_history = 0";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			pr.setInt(2, bookId);
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoBasketException("Cannot delete book from basket", e);
		}
	}
	
	public Boolean deleteAllByUserId(Integer userId) throws DaoBasketException {
		String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND is_history = 0";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoBasketException("Cannot delete books from basket", e);
		}
	}
	
	public Integer markAsHistoryByUserId(Integer userId) throws DaoBasketException {
		String sqlDelete = "UPDATE basket SET is_history = 1 WHERE user_id = ? AND is_history = 0";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			
			return pr.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoBasketException("Cannot mark books as history in basket", e);
		}
	}
	
	public ResultSet findAllInUserHistory(Integer userId) throws DaoBasketException {
		String sqlFind = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ? AND is_history = 1";
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot find products in history", e);
		}
	}
}
