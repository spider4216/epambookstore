package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.MysqlOrderToProductDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.exception.MysqlDaoException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;
import com.epam.entity.CategoryEntity;
import com.epam.entity.OrderEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.service.exception.BookServiceException;
import com.epam.service.exception.CategoryServiceException;
import com.epam.service.exception.OrderServiceException;
import com.epam.service.exception.OrderToProductServiceException;

public class OrderToProductService {
	private MysqlOrderToProductDao orderToProductDao;
	
	private Lang lang;
	
	public OrderToProductService() throws OrderToProductServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new OrderToProductServiceException("cannot get lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			orderToProductDao = (MysqlOrderToProductDao)MYSQLFactory.getOrderToProduct();
		} catch (DaoOrderToProductException | MysqlDaoException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_get_dao_err"), e);
		}
	}
	
	public ArrayList<OrderToProductEntity> findAllByProductId(Integer id) throws OrderToProductServiceException {
		try {
			ArrayList<OrderToProductEntity> orderToProductCollection = new ArrayList<>();
			ResultSet res = orderToProductDao.findAllByOrderId(id);

			while (res.next()) {
				orderToProductCollection.add(orderToProductSetter(res));
			}
			
			return orderToProductCollection;
		} catch (DaoOrderToProductException | SQLException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_empty_err"), e);
		}
	}
	
	public Integer insert(OrderToProductEntity entity) throws OrderToProductServiceException {
		Integer id = null;

		try {
			id = orderToProductDao.insert(entity);
		} catch (DaoOrderToProductException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_insert_err"), e);
		}

		return id;
	}
	
	private OrderToProductEntity orderToProductSetter(ResultSet result) throws OrderToProductServiceException {
		BookService bookService = null;
		
		try {
			OrderToProductEntity entity = new OrderToProductEntity();
			entity.setId(result.getInt("id"));
			entity.setBookId(result.getInt("book_id"));
			entity.setOrderId(result.getInt("order_id"));
			entity.setCount(result.getInt("count"));
			
			bookService = new BookService();
			BookEntity book = bookService.findById(result.getInt("book_id"));
			
			entity.setBook(book);
			
			return entity;
		} catch (SQLException | BookServiceException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_insert_err"), e);
		}
	}
}