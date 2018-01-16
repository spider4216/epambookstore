package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.entity.UserEntity;
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
}
