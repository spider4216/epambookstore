package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;

public class MysqlOrderToProductDao implements IOrderToProductDao {
	
	private Lang lang = null;

	public MysqlOrderToProductDao() throws DaoOrderToProductException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoOrderToProductException("cannot get lang", e);
		}
	}

	public ResultSet findAllByOrderId(Integer id) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM order_to_product where order_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet res = pr.executeQuery();

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_empty_err"), e);
		}
	}
	
	public Integer insert(OrderToProductEntity entity) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			String sqlInsert = "INSERT INTO order_to_product (order_id, book_id, count) VALUES (?, ?, ?)";
			PreparedStatement pr = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
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
