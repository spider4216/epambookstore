package com.epam.component.dao;

import java.sql.ResultSet;

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
	public ResultSet findAll() throws DaoOrderException;

	/**
	 * Find one order by id
	 */
	public ResultSet findOneById(Integer id) throws DaoOrderException;
	
	/**
	 * Insert order
	 */
	public Integer insert(OrderEntity entity) throws DaoOrderException;
	
	/**
	 * Find all orders by user id
	 */
	public ResultSet findAllByUserId(Integer id) throws DaoOrderException;
	
	/**
	 * Find all orders by status
	 */
	public ResultSet findAllByStatus(Integer status) throws DaoOrderException;
	
	/**
	 * Update order status as accept by id
	 */
	public Integer updateStatusAsAcceptById(Integer id) throws DaoOrderException;
}
