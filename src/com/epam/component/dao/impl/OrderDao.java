package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.IOrderDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.constant.OrderStatus;
import com.epam.constant.RoleConstant;
import com.epam.entity.OrderEntity;

public class OrderDao implements IOrderDao {

	private Lang lang = null;

	public OrderDao() throws DaoOrderException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoOrderException("cannot get lang", e);
		}
	}

	public ResultSet findAll() throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM orders";
			Statement pr = connection.createStatement();
			
			ResultSet res = pr.executeQuery(sqlFind);

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}
	
	public ResultSet findAllByUserId(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM orders where user_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet res = pr.executeQuery();

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	public ResultSet findAllByStatus(Integer status) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM orders where status = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, status);
			
			ResultSet res = pr.executeQuery();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	public ResultSet findOneById(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM orders WHERE id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}
	
	public Integer insert(OrderEntity entity) throws DaoOrderException{
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			String sqlInsert = "INSERT INTO orders (user_id, status) VALUES (?, ?)";
			PreparedStatement pr = connection.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getStatus());
			
			pr.executeUpdate();
			
			ResultSet res = pr.getGeneratedKeys();
			res.next();

			Integer id = res.getInt(1);
			
			return id;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_inser_err"), e);
		}
	}
	
	public Integer updateStatusAsAcceptById(Integer id) throws DaoOrderException {
		String sqlDelete = "UPDATE orders SET status = ? WHERE id = ?";

		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, OrderStatus.APPROVED);
			pr.setInt(2, id);
			Integer res = pr.executeUpdate();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_cannot_change_status"), e);
		}
	}
}
