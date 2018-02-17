package com.epam.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoOrderToProductException;
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
import com.epam.enum_list.OrderEnum;
import com.epam.service.exception.BasketServiceException;
import com.epam.service.exception.OrderServiceException;
import com.epam.service.exception.OrderToProductServiceException;

/**
 * Service for order logic
 * 
 * @author Yuriy Sirotenko
 */
public class OrderService {
	private OrderDao orderDao;
	
	private Lang lang;
	
	private static final Integer EMPTY_VALUE = 0;
	
	public OrderService() throws ServiceLocatorException, DaoOrderException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		orderDao = (OrderDao)MYSQLFactory.getOrderDao();
	}
	
	/**
	 * Find all user orders
	 */
	public ArrayList<OrderEntity> findAllUserOrders(Integer userId) throws OrderServiceException {
		try {
			return orderDao.findAllByUserId(userId);
		} catch (DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
	}
	
	/**
	 * Find all not approved orders
	 */
	public ArrayList<OrderEntity> findAllNotApproved() throws OrderServiceException {
		try {
			return orderDao.findAllByStatus(OrderEnum.UNDER_CONSIDERATION.getValue());
		} catch (DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
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

			// Create order
			Integer orderId = createItemOrder(connection, connectionPool, userId);
			// Find basket content
			ArrayList<BasketEntity> basketCollection = getBasketContent(connection, connectionPool, userId);
			// Create order to product - many to many
			createOrderToProduct(connection, connectionPool, orderId, basketCollection);
			// Clean basket
			cleanBasket(connection, connectionPool, userId);

			connection.commit();
		} catch (SQLException | ConnectionPoolException e) {
			throwOrderServiceException(e);
		} finally {
			connectionPool.useOneConnection(false);
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
	private Integer createItemOrder(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException, SQLException {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserId(userId);
		orderEntity.setStatus(OrderEnum.UNDER_CONSIDERATION.getValue());
		
		Integer orderId = null;
		
		try {
			orderId = insert(orderEntity);
		} catch (OrderServiceException e) {
			connectionPool.useOneConnection(false);
			connection.rollback();
			throwOrderServiceException(e);
		}
		
		return orderId;
	}
	
	/**
	 * Get basket content
	 */
	private ArrayList<BasketEntity> getBasketContent(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException, SQLException {
		BasketService basketService = null;
		ArrayList<BasketEntity> basketCollection = null;
		try {
			basketService = new BasketService();
			basketCollection = basketService.findAllProductsByUserId(userId);
		} catch (BasketServiceException | ServiceLocatorException | DaoBasketException e) {
			connectionPool.useOneConnection(false);
			connection.rollback();
			throwOrderServiceException(e);
		}
		
		return basketCollection;
	}
	
	/**
	 * Create order to product relation (many to many)
	 */
	private Boolean createOrderToProduct(Connection connection, ConnectionPool connectionPool, Integer orderId, ArrayList<BasketEntity> basketCollection) throws OrderServiceException, SQLException {
		try {
			OrderToProductService orderToProductService = new OrderToProductService();
			
			for (BasketEntity item : basketCollection) {
				OrderToProductEntity orderToProductEntity = new OrderToProductEntity();
				orderToProductEntity.setBookId(item.getBook().getId());
				orderToProductEntity.setCount(item.getCount());
				orderToProductEntity.setOrderId(orderId);
				
				orderToProductService.insert(orderToProductEntity);
			}
		} catch (OrderToProductServiceException | ServiceLocatorException | DaoOrderToProductException e) {
			connectionPool.useOneConnection(false);
			connection.rollback();
			throwOrderServiceException(e);
		}
		
		return true;
	}
	
	/**
	 * Clean basket
	 */
	private Boolean cleanBasket(Connection connection, ConnectionPool connectionPool, Integer userId) throws OrderServiceException, SQLException {
		try {
			BasketService basketService = new BasketService();
			basketService.deleteUserBasketBooks(userId);
		} catch (BasketServiceException | ServiceLocatorException | DaoBasketException e) {
			connectionPool.useOneConnection(false);
			connection.rollback();
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
