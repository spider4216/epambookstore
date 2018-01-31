package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.IOrderDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.OrderEntity;
import com.epam.enum_list.OrderEnum;

/**
 * Mysql order dao
 * 
 * @author Yuriy Sirotenko
 */
public class OrderDao implements IOrderDao {

	private final static String SQL_FIND_ALL = "SELECT * FROM orders";

	private final static String SQL_FIND_ALL_BY_USER_ID = "SELECT * FROM orders where user_id = ?";

	private final static String SQL_FIND_ALL_BY_STATUS = "SELECT * FROM orders where status = ?";

	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM orders WHERE id = ?";

	private final static String SQL_INSERT = "INSERT INTO orders (user_id, status) VALUES (?, ?)";

	private final static String SQL_UPDATE_STATUS_AS_ACCEPT_BY_ID = "UPDATE orders SET status = ? WHERE id = ?";
	
	private Lang lang = null;

	public OrderDao() throws DaoOrderException, ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Find all orders
	 */
	public ResultSet findAll() throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			Statement pr = connection.createStatement();
			
			return pr.executeQuery(SQL_FIND_ALL);
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	/**
	 * Find all orders by user id
	 */
	public ResultSet findAllByUserId(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_USER_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	/**
	 * Find all orders by status
	 */
	public ResultSet findAllByStatus(Integer status) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_STATUS);
			pr.setInt(IStatementIndex.FIRST, status);
			
			return pr.executeQuery();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	/**
	 * Find one order by id
	 */
	public ResultSet findOneById(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}
	
	/**
	 * Insert order
	 */
	public Integer insert(OrderEntity entity) throws DaoOrderException{
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pr.setInt(IStatementIndex.FIRST, entity.getUserId());
			pr.setInt(IStatementIndex.SECOND, entity.getStatus());
			
			pr.executeUpdate();
			
			ResultSet res = pr.getGeneratedKeys();
			res.next();

			return res.getInt(1);
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_inser_err"), e);
		}
	}
	
	/**
	 * Update order status as accept by id
	 */
	public Integer updateStatusAsAcceptById(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_STATUS_AS_ACCEPT_BY_ID);
			pr.setInt(IStatementIndex.FIRST, OrderEnum.APPROVED.getValue());
			pr.setInt(IStatementIndex.SECOND, id);

			return pr.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_cannot_change_status"), e);
		}
	}
}
