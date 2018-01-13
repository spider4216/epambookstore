package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoBasketException;
import com.epam.entity.BasketEntity;

/**
 * Interface for basket Dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IBasketDao {
	
	/**
	 * Insert product into basket
	 */
	public Integer insert(BasketEntity entity) throws DaoBasketException;
	
	/**
	 * Find book in basket by id and user id
	 */
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException;
	
	/**
	 * Find all user's products in basket
	 */
	public ResultSet findAllByUserId(Integer userId) throws DaoBasketException;
	
	/**
	 * Delete product from basket by user and book id
	 */
	public Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException;
	
	/**
	 * Delete all user's products from basket
	 */
	public Boolean deleteAllByUserId(Integer userId) throws DaoBasketException;	
}
