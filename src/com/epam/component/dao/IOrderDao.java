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
	public ResultSet findAll() throws DaoOrderException;

	public ResultSet findOneById(Integer id) throws DaoOrderException;
	
	public Integer insert(OrderEntity entity) throws DaoOrderException;
}
