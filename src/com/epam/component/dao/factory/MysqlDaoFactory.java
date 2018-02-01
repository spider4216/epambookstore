package com.epam.component.dao.factory;


import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IOrderDao;
import com.epam.component.dao.IOrderToProductDao;
import com.epam.component.dao.IRoleDao;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoOrderException;
import com.epam.component.dao.exception.DaoOrderToProductException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.impl.BasketDao;
import com.epam.component.dao.impl.BookDao;
import com.epam.component.dao.impl.CategoryDao;
import com.epam.component.dao.impl.OrderDao;
import com.epam.component.dao.impl.OrderToProductDao;
import com.epam.component.dao.impl.RoleDao;
import com.epam.component.dao.impl.UserDao;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * Mysql dao factory
 * 
 * @author Yuriy Sirotenko
 */
class MysqlDaoFactory extends DaoFactory {
	
	private static final String excMsg = "service locator problem";
	
	public IBookDao getBookDao() throws DaoBookException {
		try {
			return new BookDao();
		} catch (ServiceLocatorException e) {
			throw new DaoBookException(excMsg, e);
		}
	}
	
	public IUserDao getUserDao() throws DaoUserException {
		try {
			return new UserDao();
		} catch (ServiceLocatorException e) {
			throw new DaoUserException(excMsg, e);
		}
	}
	
	public ICategoryDao getCategoryDao() throws DaoCategoryException {
		try {
			return new CategoryDao();
		} catch (ServiceLocatorException e) {
			throw new DaoCategoryException(excMsg, e);
		}
	}
	
	public IBasketDao getBasketDao() throws DaoBasketException {
		try {
			return new BasketDao();
		} catch (ServiceLocatorException e) {
			throw new DaoBasketException(excMsg, e);
		}
	}
	
	public IRoleDao getRoleDao() throws DaoRoleException {
		try {
			return new RoleDao();
		} catch (ServiceLocatorException e) {
			throw new DaoRoleException(excMsg, e);
		}
	}
	
	public IOrderDao getOrderDao() throws DaoOrderException {
		try {
			return new OrderDao();
		} catch (ServiceLocatorException e) {
			throw new DaoOrderException(excMsg, e);
		}
	}
	
	public IOrderToProductDao getOrderToProduct() throws DaoOrderToProductException {
		try {
			return new OrderToProductDao();
		} catch (ServiceLocatorException e) {
			throw new DaoOrderToProductException(excMsg, e);
		}
	}
}
