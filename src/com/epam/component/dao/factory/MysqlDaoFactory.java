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

public class MysqlDaoFactory extends DaoFactory {
	
	public IBookDao getBookDao() throws DaoBookException {
		return new BookDao();
	}
	
	public IUserDao getUserDao() throws DaoUserException {
		return new UserDao();
	}
	
	public ICategoryDao getCategoryDao() throws DaoCategoryException {
		return new CategoryDao();
	}
	
	public IBasketDao getBasketDao() throws DaoBasketException {
		return new BasketDao();
	}
	
	public IRoleDao getRoleDao() throws DaoRoleException {
		return new RoleDao();
	}
	
	public IOrderDao getOrderDao() throws DaoOrderException {
		return new OrderDao();
	}
	
	public IOrderToProductDao getOrderToProduct() throws DaoOrderToProductException {
		return new OrderToProductDao();
	}
}
