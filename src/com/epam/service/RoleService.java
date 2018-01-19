package com.epam.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.MysqlRoleDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.exception.MysqlDaoException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.CategoryEntity;
import com.epam.entity.RoleEntity;
import com.epam.service.exception.CategoryServiceException;
import com.epam.service.exception.RoleServiceException;

public class RoleService {
	private MysqlRoleDao roleDao;
	
	private Lang lang;
	
	public RoleService() throws RoleServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new RoleServiceException("cannot get lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			roleDao = (MysqlRoleDao)MYSQLFactory.getRoleDao();
		} catch (DaoRoleException | MysqlDaoException e) {
			throw new RoleServiceException(lang.getValue("service_role_get_dao_err"), e);
		}
	}
	
	/**
	 * Find role by id
	 */
	public RoleEntity findOneById(Integer id) throws RoleServiceException {
		try {
			ResultSet res = roleDao.findOneById(id);
			RoleEntity role = roleSetter(res);
			
			return role;
		} catch (DaoRoleException | SQLException e) {
			throw new RoleServiceException(lang.getValue("service_role_empty_err"), e);
		}
	}
	
	private RoleEntity roleSetter(ResultSet result) throws SQLException {
		RoleEntity role = new RoleEntity();
		role.setId(result.getInt("id"));
		role.setName(result.getString("name"));
		
		return role;
	}
}
