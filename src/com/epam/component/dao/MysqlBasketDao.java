package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BasketEntity;

/**
 * Mysql dao for basket
 * 
 * @author Yuriy Sirotenko
 */
public class MysqlBasketDao implements IBasketDao {
	private Connection connection = null;
	
	private Lang lang = null;
	
	public MysqlBasketDao(Connection connection) throws DaoBasketException {
		this.connection = connection;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoBasketException("Problem with create dao basket", e);
		}
	}
	
	/**
	 * Insert product into basket
	 */
	public Integer insert(BasketEntity entity) throws DaoBasketException {
		try {
			String sqlInsert = "INSERT INTO basket (user_id, book_id, count, is_history) VALUES (?, ?, ?, ?)";
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getBookId());
			pr.setInt(3, entity.getCount());
			pr.setInt(4, entity.getIsHistory());

			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_drop_into_basket_err"), e);
		}
	}
	
	/**
	 * Find book in basket by id and user id
	 */
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException {		
		try {
			String sqlFind = "SELECT * FROM basket WHERE book_id = ? AND user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, productId);
			pr.setInt(2, userId);
			ResultSet res = pr.executeQuery();
			res.next();
						
			if (res.getRow() <= 0) {
				throw new DaoBasketException(lang.getValue("dao_basket_product_not_found"));
			}
			
			return res;
		} catch (SQLException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_err_by_product_and_user_id"), e);
		}
	}
	
	/**
	 * Find all user's products in basket
	 */
	public ResultSet findAllByUserId(Integer userId) throws DaoBasketException {
		try {
			String sqlFind = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_product_not_found"), e);
		}
	}
	
	/**
	 * Delete product from basket by user and book id
	 */
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException {
		try {
			String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND book_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			pr.setInt(2, bookId);
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_cannot_delete_product"), e);
		}
	}
	
	/**
	 * Delete all user's products from basket
	 */
	public Boolean deleteAllByUserId(Integer userId) throws DaoBasketException {
		try {
			String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_cannot_delete_product"), e);
		}
	}
	
	/**
	 * @deprecated
	 */
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
	
	/**
	 * @deprecated
	 */
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
