package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.constant.RoleConstant;
import com.epam.entity.OrderEntity;

public class MysqlOrderDao implements IOrderDao {

	private Lang lang = null;

	public MysqlOrderDao() throws DaoOrderException {
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
			PreparedStatement pr = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, entity.getUserId());
			pr.setInt(2, entity.getStatus());
			
			Integer id = pr.executeUpdate();
			
			return id;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_inser_err"), e);
		}
	}
}
