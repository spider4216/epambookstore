package com.epam.component.dao.basket;

import java.sql.ResultSet;

import com.epam.component.dao.basket.exception.DaoBasketException;
import com.epam.entity.BasketEntity;

public interface IBasketDao {
	public Integer insert(BasketEntity entity) throws DaoBasketException;
	
	public ResultSet findOneByProductAndUserId(Integer productId, Integer userId) throws DaoBasketException;
}
