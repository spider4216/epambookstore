package com.epam.component.dao.basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.epam.component.dao.basket.exception.DaoBasketException;
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
}
