package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IStatementIndex;
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
public class BasketDao implements IBasketDao {
	private static final String SQL_INSERT = "INSERT INTO basket (user_id, book_id, count) VALUES (?, ?, ?)";

	private static final String SQL_FIND_ONE_BY_PRODUCT_AND_USER_ID = "SELECT * FROM basket WHERE book_id = ? AND user_id = ?";

	private static final String SQL_FIND_ALL_BY_USER_ID = "SELECT bt.*, bk.* FROM basket bt INNER JOIN books bk ON bt.book_id = bk.id WHERE bt.user_id = ?";

	private static final String SQL_DELETE_BY_USER_AND_BOOK_ID = "DELETE FROM basket WHERE user_id = ? AND book_id = ?";

	private static final String SQL_DELETE_ALL_BY_USER_ID = "DELETE FROM basket WHERE user_id = ?";
	
	private static final Integer EMPTY_BASKET = 0;
	
	private Lang lang = null;
	
	public BasketDao() throws DaoBasketException, ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}
	
	/**
	 * Insert product into basket
	 */
	public Integer insert(BasketEntity entity) throws DaoBasketException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_INSERT);
			pr.setInt(IStatementIndex.FIRST, entity.getUserId());
			pr.setInt(IStatementIndex.SECOND, entity.getBookId());
			pr.setInt(IStatementIndex.THIRD, entity.getCount());

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
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_PRODUCT_AND_USER_ID);
			pr.setInt(IStatementIndex.FIRST, productId);
			pr.setInt(IStatementIndex.SECOND, userId);
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
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_USER_ID);
			pr.setInt(IStatementIndex.FIRST, userId);

			return pr.executeQuery();
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
			PreparedStatement pr = connection.prepareStatement(SQL_DELETE_BY_USER_AND_BOOK_ID);
			pr.setInt(IStatementIndex.FIRST, userId);
			pr.setInt(IStatementIndex.SECOND, bookId);
			
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
			PreparedStatement pr = connection.prepareStatement(SQL_DELETE_ALL_BY_USER_ID);
			pr.setInt(IStatementIndex.FIRST, userId);
			
			pr.executeUpdate();
			
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoBasketException(lang.getValue("dao_basket_cannot_delete_product"), e);
		}
	}
}
