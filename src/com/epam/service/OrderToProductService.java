package com.epam.service;

import java.util.ArrayList;

import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.OrderToProductDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.OrderToProductEntity;
import com.epam.service.exception.OrderToProductServiceException;

/**
 * Order to product (many to many) service
 * 
 * @author Yuriy Sirotenko
 */
class OrderToProductService {
	private OrderToProductDao orderToProductDao;
	
	private Lang lang;
	
	public OrderToProductService() throws ServiceLocatorException, DaoOrderToProductException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		orderToProductDao = (OrderToProductDao)MYSQLFactory.getOrderToProduct();
	}
	
	/**
	 * Insert new relation
	 */
	public Integer insert(OrderToProductEntity entity) throws OrderToProductServiceException {
		try {
			return orderToProductDao.insert(entity);
		} catch (DaoOrderToProductException e) {
			throw new OrderToProductServiceException(lang.getValue("service_order_to_product_insert_err"), e);
		}
	}
}
