package com.epam.component.dao.factory;


import com.epam.component.dao.MysqlBasketDao;
import com.epam.component.dao.MysqlBookDao;
import com.epam.component.dao.MysqlCategoryDao;
import com.epam.component.dao.MysqlRoleDao;
import com.epam.component.dao.MysqlUserDao;
import com.epam.component.dao.IBasketDao;
import com.epam.component.dao.IBookDao;
import com.epam.component.dao.ICategoryDao;
import com.epam.component.dao.IRoleDao;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.DaoBasketException;
import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.exception.DaoUserException;

public class MysqlDaoFactory extends DaoFactory {
	
	public IBookDao getBookDao() throws DaoBookException {
		return new MysqlBookDao();
	}
	
	public IUserDao getUserDao() throws DaoUserException {
		return new MysqlUserDao();
	}
	
	public ICategoryDao getCategoryDao() throws DaoCategoryException {
		return new MysqlCategoryDao();
	}
	
	public IBasketDao getBasketDao() throws DaoBasketException {
		return new MysqlBasketDao();
	}
	
	public IRoleDao getRoleDao() throws DaoRoleException {
		return new MysqlRoleDao();
	}
}
