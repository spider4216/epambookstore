package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.IOrderToProductDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.OrderToProductEntity;

/**
 * Mysql order to product dao (map - many to many)
 * 
 * @author Yuriy Sirotenko
 */
public class OrderToProductDao implements IOrderToProductDao {
	
	private final static String SQL_FIND_ALL_BY_ORDER_ID = "SELECT * FROM order_to_product where order_id = ?";

	private final static String SQL_INSERT = "INSERT INTO order_to_product (order_id, book_id, count) VALUES (?, ?, ?)";
	
	private Lang lang = null;

	public OrderToProductDao() throws DaoOrderToProductException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoOrderToProductException("cannot get lang", e);
		}
	}

	/**
	 * Find all order to product map (many to many) by order id
	 */
	public ResultSet findAllByOrderId(Integer id) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_ORDER_ID);
			pr.setInt(1, id);
			
			ResultSet res = pr.executeQuery();

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_empty_err"), e);
		}
	}
	
	/**
	 * Insert order to product map (many to many)
	 */
	public Integer insert(OrderToProductEntity entity) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, entity.getOrderId());
			pr.setInt(2, entity.getBookId());
			pr.setInt(3, entity.getCount());
			
			Integer id = pr.executeUpdate();
			
			return id;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_inser_err"), e);
		}
	}
}
