package com.epam.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.OrderDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BasketEntity;
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.entity.UserEntity;
import com.epam.enum_list.OrderEnum;
import com.epam.service.exception.BasketServiceException;
import com.epam.service.exception.OrderServiceException;
import com.epam.service.exception.OrderToProductServiceException;
import com.epam.service.exception.UserServiceException;

/**
 * Service for order logic
 * 
 * @author Yuriy Sirotenko
 */
public class OrderService {
	private OrderDao orderDao;
	
	private Lang lang;
	
	private static final Integer EMPTY_VALUE = 0;
	
	public OrderService() throws OrderServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new OrderServiceException("cannot get lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			orderDao = (OrderDao)MYSQLFactory.getOrderDao();
		} catch (DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_get_dao_err"), e);
		}
	}
	
	/**
	 * Find all user orders
	 */
	public ArrayList<OrderEntity> findAllUserOrders(Integer userId) throws OrderServiceException {
		try {
			ArrayList<OrderEntity> orderCollection = new ArrayList<>();
			ResultSet res = orderDao.findAllByUserId(userId);

			while (res.next()) {
				orderCollection.add(orderSetter(res));
			}
			
			return orderCollection;
		} catch (DaoOrderException | SQLException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
	}
	
	/**
	 * Find all not approved orders
	 */
	public ArrayList<OrderEntity> findAllNotApproved() throws OrderServiceException {
		try {
			ArrayList<OrderEntity> orderCollection = new ArrayList<>();
			ResultSet res = orderDao.findAllByStatus(OrderEnum.UNDER_CONSIDERATION.getValue());
			
			while (res.next()) {
				orderCollection.add(orderSetter(res));
			}
			
			return orderCollection;
		} catch (DaoOrderException | SQLException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
	}
		
	/**
	 * Order setter
	 */
	private OrderEntity orderSetter(ResultSet result) throws SQLException, OrderServiceException {
		OrderEntity entity = new OrderEntity();
		
		entity.setId(result.getInt("id"));
		entity.setUserId(result.getInt("user_id"));
		entity.setStatus(result.getInt("status"));
		entity.setCreateDate(result.getString("create_date"));
		
		OrderToProductService orderProductService = null;
		try {
			orderProductService = new OrderToProductService();
		} catch (OrderToProductServiceException e) {
			throw new OrderServiceException("service_order_create_entity_err", e);
		}
		
		ArrayList<OrderToProductEntity> orderProductCollection = null;
		
		try {
			orderProductCollection = orderProductService.findAllByProductId(entity.getId());
		} catch (OrderToProductServiceException e) {
			throw new OrderServiceException(lang.getValue("service_order_many_err"), e);
		}
		
		entity.setProducts(orderProductCollection);
		
		UserService userService = null;
		UserEntity user = null;
		
		try {
			userService = new UserService();
			user = userService.findById(entity.getUserId());
		} catch (UserServiceException e) {
			throw new OrderServiceException(lang.getValue("service_order_user_one_err"), e);
		}
		
		entity.setUser(user);
		
		return entity;
	}
	
	/**
	 * Insert one order
	 */
	private Integer insert(OrderEntity entity) throws OrderServiceException {
		try {
			return orderDao.insert(entity);
		} catch (DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_insert_err"), e);
		}
	}
	
	/**
	 * Create entire order
	 */
	public Boolean createOrder(Integer userId) throws OrderServiceException {
		Connection connection = null;
		ConnectionPool connectionPool = null;
		
		try {
			connectionPool = ConnectionPool.getInstance();
			connectionPool.useOneConnection(true);
			connection = (Connection) ConnectionPool.getInstance().getConnection();
			connection.setAutoCommit(false);
		} catch (ConnectionPoolException | SQLException e) {
			throwOrderServiceException(e);
		}
		
		// Create order
		Integer orderId = createItemOrder(connection, connectionPool, userId);
		// Find basket content
		ArrayList<BasketEntity> basketCollection = getBasketContent(connection, connectionPool, userId);
		// Create order to product - many to many
		createOrderToProduct(connection, connectionPool, orderId, basketCollection);
		// Clean basket
		cleanBasket(connection, connectionPool, userId);

		connectionPool.useOneConnection(false);
		try {
			connection.commit();
		} catch (SQLException e) {
			throwOrderServiceException(e);
		}
		
		return true;
	}
	
	/**
	 * Accept order
	 */
	public Boolean acceptOrder(Integer id) throws OrderServiceException {
		Integer res = null;
		try {
			res = orderDao.updateStatusAsAcceptById(id);
		} catch (DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_accept_err"), e);
		}
		
		if (res <= EMPTY_VALUE) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Create one order without any relations
	 */
	private Integer createItemOrder(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserId(userId);
		orderEntity.setStatus(OrderEnum.UNDER_CONSIDERATION.getValue());
		
		Integer orderId = null;
		
		try {
			orderId = insert(orderEntity);
		} catch (OrderServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throwOrderServiceException(e1);
			}
			throwOrderServiceException(e);
		}
		
		return orderId;
	}
	
	/**
	 * Get basket content
	 */
	private ArrayList<BasketEntity> getBasketContent(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException {
		BasketService basketService = null;
		ArrayList<BasketEntity> basketCollection = null;
		try {
			basketService = new BasketService();
			basketCollection = basketService.findAllProductsByUserId(userId);
		} catch (BasketServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throwOrderServiceException(e1);
			}
			throwOrderServiceException(e);
		}
		
		return basketCollection;
	}
	
	/**
	 * Create order to product relation (many to many)
	 */
	private Boolean createOrderToProduct(Connection connection, ConnectionPool connectionPool, Integer orderId, ArrayList<BasketEntity> basketCollection) throws OrderServiceException {
		try {
			OrderToProductService orderToProductService = new OrderToProductService();
			
			for (BasketEntity item : basketCollection) {
				OrderToProductEntity orderToProductEntity = new OrderToProductEntity();
				orderToProductEntity.setBookId(item.getBook().getId());
				orderToProductEntity.setCount(item.getCount());
				orderToProductEntity.setOrderId(orderId);
				
				orderToProductService.insert(orderToProductEntity);
			}
		} catch (OrderToProductServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throwOrderServiceException(e1);
			}
			throwOrderServiceException(e);
		}
		
		return true;
	}
	
	/**
	 * Clean basket
	 */
	private Boolean cleanBasket(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException {
		try {
			BasketService basketService = new BasketService();
			basketService.deleteUserBasketBooks(userId);
		} catch (BasketServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throwOrderServiceException(e1);
			}
			throwOrderServiceException(e);
		}
		
		return true;
	}
	
	/**
	 * Total sum of products in order
	 */
	public Double totalSumByCollection(ArrayList<OrderToProductEntity> collection) {
		Double sum = 0.0;
		
		for (OrderToProductEntity item : collection) {
			sum += item.getBook().getPrice() * item.getCount();
		}
		
		return sum;
	}
	
	/**
	 * throw order service exception
	 */
	private void throwOrderServiceException(Exception e) throws OrderServiceException {
		throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
	}
}
