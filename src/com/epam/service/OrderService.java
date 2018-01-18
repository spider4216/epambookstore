package com.epam.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.action.BasketOrderAction;
import com.epam.component.dao.MysqlCategoryDao;
import com.epam.component.dao.MysqlOrderDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.exception.MysqlDaoException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.constant.OrderStatus;
import com.epam.entity.BasketEntity;
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.entity.UserEntity;
import com.epam.service.exception.BasketServiceException;
import com.epam.service.exception.OrderServiceException;
import com.epam.service.exception.OrderToProductServiceException;

public class OrderService {
	private MysqlOrderDao orderDao;
	
	private Lang lang;
	
	public OrderService() throws OrderServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new OrderServiceException("cannot get lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			orderDao = (MysqlOrderDao)MYSQLFactory.getOrderDao();
		} catch (DaoOrderException | MysqlDaoException e) {
			throw new OrderServiceException(lang.getValue("service_order_get_dao_err"), e);
		}
	}
	
	public ArrayList<OrderEntity> findAll() throws OrderServiceException {
		try {
			ArrayList<OrderEntity> orderCollection = new ArrayList<>();
			ResultSet res = orderDao.findAll();

			while (res.next()) {
				orderCollection.add(orderSetter(res));
			}
			
			ConnectionPool.getInstance().release();

			return orderCollection;
		} catch (DaoOrderException | SQLException | ConnectionPoolException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
	}
	
	public OrderEntity findOneById(Integer id) throws OrderServiceException {
		try {
			ResultSet res = orderDao.findOneById(id);
			OrderEntity entity = orderSetter(res);
			ConnectionPool.getInstance().release();
			return entity;
		} catch (DaoOrderException | SQLException | ConnectionPoolException e) {
			throw new OrderServiceException(lang.getValue("service_order_empty_err"), e);
		}
	}
	
	public OrderEntity orderSetter(ResultSet result) throws SQLException, OrderServiceException {
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
		
		return entity;
	}
	
	public Integer insert(OrderEntity entity) throws OrderServiceException {
		Integer id = null;
		
		try {
			id = orderDao.insert(entity);
			ConnectionPool.getInstance().release();
		} catch (ConnectionPoolException | DaoOrderException e) {
			throw new OrderServiceException(lang.getValue("service_order_insert_err"), e);
		}
		
		return id;
	}
	
	public Boolean createOrder(Integer userId) throws OrderServiceException {
		Connection connection = null;
		ConnectionPool connectionPool = null;
		
		try {
			connectionPool = ConnectionPool.getInstance();
			connectionPool.useOneConnection(true);
			connection = (Connection) ConnectionPool.getInstance().getConnection();
			connection.setAutoCommit(false);
		} catch (ConnectionPoolException | SQLException e) {
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}
		
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserId(userId);
		orderEntity.setStatus(OrderStatus.UNDER_CONSIDERATION);
		
		OrderService orderService = new OrderService();
		Integer orderId = orderService.insert(orderEntity);
		
		BasketService basketService;
		try {
			basketService = new BasketService();
		} catch (BasketServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e1);
			}
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}
		ArrayList<BasketEntity> basketCollection;
		try {
			basketCollection = basketService.findAllProductsByUserId(userId);
		} catch (BasketServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e1);
			}
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}
		
		OrderToProductService orderToProductService;
		try {
			orderToProductService = new OrderToProductService();
		} catch (OrderToProductServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e1);
			}
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}

		for (BasketEntity item : basketCollection) {
			OrderToProductEntity orderToProductEntity = new OrderToProductEntity();
			orderToProductEntity.setBookId(item.getBook().getId());
			orderToProductEntity.setCount(item.getCount());
			orderToProductEntity.setOrderId(orderId);

			try {
				orderToProductService.insert(orderToProductEntity);
			} catch (OrderToProductServiceException e) {
				connectionPool.useOneConnection(false);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e1);
				}
				throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
			}
		}
		
		try {
			basketService.deleteUserBasketBooks(userId);
		} catch (BasketServiceException e) {
			connectionPool.useOneConnection(false);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e1);
			}
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}
		
		try {
			connection.commit();
		} catch (SQLException e) {
			connectionPool.useOneConnection(false);
			throw new OrderServiceException(lang.getValue("service_order_create_order_err"), e);
		}
		
		connectionPool.useOneConnection(false);
		
		return true;
	}
}
