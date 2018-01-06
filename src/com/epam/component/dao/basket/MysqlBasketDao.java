package com.epam.component.dao.basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.basket.exception.DaoBasketException;
import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.category.exception.DaoCategoryException;
import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.BasketEntity;
import com.epam.entity.User;

public class MysqlBasketDao implements IBasketDao{
	private Connection connection = null;
	
	public MysqlBasketDao(Connection connection) {
		this.connection = connection;
	}
	
	public Integer insert(BasketEntity entity) throws DaoBasketException {
		String sqlInsert = "INSERT INTO basket (user_id, book_id, count) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getBookId());
			pr.setInt(3, entity.getCount());

			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot insert product into basket", e);
		}
	}
	
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException {
		String sqlFind = "SELECT * FROM basket WHERE book_id = ? AND user_id = ?";		
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
		String sqlFind = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ?";
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBasketException("Cannot find basket products by user id", e);
		}
	}
}
