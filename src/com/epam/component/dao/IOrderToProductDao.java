package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.entity.OrderToProductEntity;

/**
 * Interface for order to product dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IOrderToProductDao {
	
	/**
	 * Find all order to product map (many to many) by order id
	 */
	public ResultSet findAllByOrderId(Integer id) throws DaoOrderToProductException;
	
	/**
	 * Insert order to product map (many to many)
	 */
	public Integer insert(OrderToProductEntity entity) throws DaoOrderToProductException;
}
