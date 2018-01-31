package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.OrderToProductDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;
import com.epam.entity.OrderToProductEntity;
import com.epam.service.exception.BookServiceException;
import com.epam.service.exception.OrderToProductServiceException;

/**
 * Order to product (many to many) service
 * 
 * @author Yuriy Sirotenko
 */
class OrderToProductService {
	private OrderToProductDao orderToProductDao;
	
	private Lang lang;
	
	public OrderToProductService() throws OrderToProductServiceException, ServiceLocatorException, DaoOrderToProductException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		orderToProductDao = (OrderToProductDao)MYSQLFactory.getOrderToProduct();
	}
	
	/**
	 * Find all relations by product id
	 */
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
	
	/**
	 * Insert new relation
	 */
	public Integer insert(OrderToProductEntity entity) throws OrderToProductServiceException {
		try {
			return orderToProductDao.insert(entity);
		} catch (DaoOrderToProductException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_insert_err"), e);
		}
	}
	
	/**
	 * Order to product setter
	 */
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
		} catch (SQLException | BookServiceException | ServiceLocatorException | DaoBookException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_insert_err"), e);
		}
	}
}
