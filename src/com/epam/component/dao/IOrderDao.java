package com.epam.component.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoOrderException;
import com.epam.entity.OrderEntity;

/**
 * Interfacefor order dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IOrderDao {
	
	/**
	 * Find all orders
	 */
	ResultSet findAll() throws DaoOrderException;

	/**
	 * Insert order
	 */
	Integer insert(OrderEntity entity) throws DaoOrderException;
	
	/**
	 * Find all orders by user id
	 */
	ArrayList<OrderEntity> findAllByUserId(Integer id) throws DaoOrderException;
	
	/**
	 * Find all orders by status
	 */
	ArrayList<OrderEntity> findAllByStatus(Integer status) throws DaoOrderException;
	
	/**
	 * Update order status as accept by id
	 */
	Integer updateStatusAsAcceptById(Integer id) throws DaoOrderException;
}
