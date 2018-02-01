package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.component.dao.CDao;
import com.epam.component.dao.IOrderToProductDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.BookEntity;
import com.epam.entity.OrderToProductEntity;

/**
 * Mysql order to product dao (map - many to many)
 * 
 * @author Yuriy Sirotenko
 */
public class OrderToProductDao extends CDao implements IOrderToProductDao {
	
	private final static String SQL_FIND_ALL_BY_ORDER_ID = "SELECT * FROM order_to_product where order_id = ?";

	private final static String SQL_INSERT = "INSERT INTO order_to_product (order_id, book_id, count) VALUES (?, ?, ?)";
	
	private Lang lang = null;

	public OrderToProductDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Find all order to product map (many to many) by order id
	 */
	public ArrayList<OrderToProductEntity> findAllByOrderId(Integer id) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ALL_BY_ORDER_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			
			ResultSet result = pr.executeQuery();			
			ArrayList<OrderToProductEntity> orderToProductCollection = new ArrayList<>();

			while (result.next()) {
				orderToProductCollection.add(orderToProductSetter(result));
			}
			
			closeResources(pr, result);
			
			return orderToProductCollection;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_empty_err"), e);
		}
	}
	
	/**
	 * Insert order to product map (many to many)
	 */
	public Integer insert(OrderToProductEntity entity) throws DaoOrderToProductException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(IStatementIndex.FIRST, entity.getOrderId());
			pr.setInt(IStatementIndex.SECOND, entity.getBookId());
			pr.setInt(IStatementIndex.THIRD, entity.getCount());
			
			Integer result = pr.executeUpdate();
			closeResources(pr);
			
			return result;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_inser_err"), e);
		}
	}
	
	/**
	 * Order to product setter
	 */
	private OrderToProductEntity orderToProductSetter(ResultSet result) throws DaoOrderToProductException {
		try {
			OrderToProductEntity entity = new OrderToProductEntity();
			entity.setId(result.getInt("id"));
			entity.setBookId(result.getInt("book_id"));
			entity.setOrderId(result.getInt("order_id"));
			entity.setCount(result.getInt("count"));
			
			DaoFactory MysqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			BookDao bookDao = (BookDao) MysqlFactory.getBookDao();
			
			BookEntity book = bookDao.findBook(result.getInt("book_id"));
			
			entity.setBook(book);
			
			return entity;
		} catch (SQLException | DaoBookException e) {
			throw new DaoOrderToProductException(lang.getValue("dao_order_to_product_setter_err"), e);
		}
	}
}
