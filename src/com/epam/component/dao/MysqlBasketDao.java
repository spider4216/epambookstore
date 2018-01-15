package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.factory.ConnectionPool;
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
	private static final Integer EMPTY_BASKET = 0;
	
	private Lang lang = null;
	
	public MysqlBasketDao() throws DaoBasketException {
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
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlInsert = "INSERT INTO basket (user_id, book_id, count, is_history) VALUES (?, ?, ?, ?)";
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getBookId());
			pr.setInt(3, entity.getCount());
			pr.setInt(4, entity.getIsHistory());

			Integer res = pr.executeUpdate();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_drop_into_basket_err"), e);
		}
	}
	
	/**
	 * Find book in basket by id and user id
	 */
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException {		
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM basket WHERE book_id = ? AND user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, productId);
			pr.setInt(2, userId);
			ResultSet res = pr.executeQuery();
			res.next();
			
			
			if (res.getRow() <= EMPTY_BASKET) {
				throw new DaoBasketException(lang.getValue("dao_basket_product_not_found"));
			}
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_err_by_product_and_user_id"), e);
		}
	}
	
	/**
	 * Find all user's products in basket
	 */
	public ResultSet findAllByUserId(Integer userId) throws DaoBasketException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet res = pr.executeQuery();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_product_not_found"), e);
		}
	}
	
	/**
	 * Delete product from basket by user and book id
	 */
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND book_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			pr.setInt(2, bookId);
			
			pr.executeUpdate();
			
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_cannot_delete_product"), e);
		}
	}
	
	/**
	 * Delete all user's products from basket
	 */
	public Boolean deleteAllByUserId(Integer userId) throws DaoBasketException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlDelete = "DELETE FROM basket WHERE user_id = ? AND is_history = 0";
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			
			pr.executeUpdate();
			
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_cannot_delete_product"), e);
		}
	}

	/**
	 * @deprecated
	 */
	public Integer markAsHistoryByUserId(Integer userId) throws DaoBasketException {
		String sqlDelete = "UPDATE basket SET is_history = 1 WHERE user_id = ? AND is_history = 0";

		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, userId);
			Integer res = pr.executeUpdate();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
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
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, userId);
			ResultSet rs = pr.executeQuery();
			
			return rs;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException("Cannot find products in history", e);
		}
	}
}
