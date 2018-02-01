package com.epam.component.dao;

import java.util.ArrayList;

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
	ArrayList<OrderToProductEntity> findAllByOrderId(Integer id) throws DaoOrderToProductException;
	
	/**
	 * Insert order to product map (many to many)
	 */
	Integer insert(OrderToProductEntity entity) throws DaoOrderToProductException;
}
