package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.entity.BasketEntity;

public interface IBasketDao {
	public Integer insert(BasketEntity entity) throws DaoBasketException;
	
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException;
	
	public ResultSet findAllByUserId(Integer userId) throws DaoBasketException;
	
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException;
	
	public Boolean deleteAllByUserId(Integer userId) throws DaoBasketException;
	
	public Integer markAsHistoryByUserId(Integer userId) throws DaoBasketException;
}
