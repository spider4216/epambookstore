package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.component.dao.CDao;
import com.epam.component.dao.IOrderDao;
import com.epam.component.dao.IOrderToProductDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.entity.UserEntity;
import com.epam.enum_list.OrderEnum;

/**
 * Mysql order dao
 * 
 * @author Yuriy Sirotenko
 */
public class OrderDao extends CDao implements IOrderDao {

	private final static String SQL_FIND_ALL = "SELECT * FROM orders";

	private final static String SQL_FIND_ALL_BY_USER_ID = "SELECT * FROM orders where user_id = ?";

	private final static String SQL_FIND_ALL_BY_STATUS = "SELECT * FROM orders where status = ?";

	private final static String SQL_INSERT = "INSERT INTO orders (user_id, status) VALUES (?, ?)";

	private final static String SQL_UPDATE_STATUS_AS_ACCEPT_BY_ID = "UPDATE orders SET status = ? WHERE id = ?";
	
	private final static Integer RETURN_ID = 1;
	
	private Lang lang = null;

	public OrderDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Find all orders
	 */
	public ResultSet findAll() throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			Statement st = connection.createStatement();
			
			ResultSet result = st.executeQuery(SQL_FIND_ALL);
			closeResources(st);
			
			return result;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	/**
	 * Find all orders by user id
	 */
	public ArrayList<OrderEntity> findAllByUserId(Integer id) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_USER_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet result = pr.executeQuery();
			ArrayList<OrderEntity> orderCollection = new ArrayList<>();
			
			while (result.next()) {
				orderCollection.add(orderSetter(result));
			}
			
			closeResources(pr, result);
			
			return orderCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_empty_err"), e);
		}
	}

	/**
	 * Find all orders by status
	 */
	public ArrayList<OrderEntity> findAllByStatus(Integer status) throws DaoOrderException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_STATUS);
			pr.setInt(IStatementIndex.FIRST, status);
			
			ResultSet result = pr.executeQuery();
			
			ArrayList<OrderEntity> orderCollection = new ArrayList<>();
			
			while (result.next()) {
				orderCollection.add(orderSetter(result));
			}
			
			closeResources(pr, result);
			
			return orderCollection;			
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
			
			ResultSet result = pr.getGeneratedKeys();
			result.next();
			
			Integer id = result.getInt(RETURN_ID);
			closeResources(pr, result);

			return id;
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
			
			Integer result = pr.executeUpdate();
			
			closeResources(pr);

			return result;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderException(lang.getValue("dao_order_cannot_change_status"), e);
		}
	}
	
	/**
	 * Order setter
	 */
	private OrderEntity orderSetter(ResultSet result) throws DaoOrderException {
		DaoFactory MysqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		OrderEntity entity = new OrderEntity();
		
		try {
			entity.setId(result.getInt("id"));
			entity.setUserId(result.getInt("user_id"));
			entity.setStatus(result.getInt("status"));
			entity.setCreateDate(result.getString("create_date"));
		} catch (SQLException e) {
			throw new DaoOrderException(lang.getValue("dao_order_setter_err"), e);
		}
		
		ArrayList<OrderToProductEntity> orderProductCollection = null;
		
		try {
			IOrderToProductDao orderToProductDao = (OrderToProductDao) MysqlFactory.getOrderToProduct();
			orderProductCollection = orderToProductDao.findAllByOrderId(entity.getId());
		} catch (DaoOrderToProductException e) {
			throw new DaoOrderException(lang.getValue("dao_order_many_err"), e);
		}
		
		entity.setProducts(orderProductCollection);
	
		UserEntity user = null;
		
		try {
			IUserDao userDao = (UserDao) MysqlFactory.getUserDao();
			user = userDao.findOneById(entity.getUserId());
		} catch (DaoUserException e) {
			throw new DaoOrderException(lang.getValue("dao_order_user_one_err"), e);
		}
		
		entity.setUser(user);
		
		return entity;
	}
}
