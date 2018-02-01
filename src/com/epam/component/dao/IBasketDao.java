package com.epam.component.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

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
	Integer insert(BasketEntity entity) throws DaoBasketException;
	
	/**
	 * Find book in basket by id and user id
	 */
	BasketEntity findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException;
	
	/**
	 * Find all user's products in basket
	 */
	ArrayList<BasketEntity> findAllByUserId(Integer userId) throws DaoBasketException;
	
	/**
	 * Delete product from basket by user and book id
	 */
	Boolean deleteByUserAndBookId(Integer userId, Integer bookId) throws DaoBasketException;
	
	/**
	 * Delete all user's products from basket
	 */
	Boolean deleteAllByUserId(Integer userId) throws DaoBasketException;	
}
