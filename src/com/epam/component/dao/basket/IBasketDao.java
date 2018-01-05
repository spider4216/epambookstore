package com.epam.component.dao.basket;

import com.epam.component.dao.basket.exception.DaoBasketException;
import com.epam.entity.BasketEntity;

public interface IBasketDao {
	public Integer insert(BasketEntity entity) throws DaoBasketException;
}
