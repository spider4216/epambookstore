package com.epam.service;

import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.RoleDao;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.RoleEntity;
import com.epam.service.exception.RoleServiceException;

/**
 * Service for role logic
 * 
 * @author Yuriy Sirotenko
 */
class RoleService {
	private RoleDao roleDao;
	
	private Lang lang;
	
	public RoleService() throws ServiceLocatorException, DaoRoleException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		roleDao = (RoleDao)MYSQLFactory.getRoleDao();
	}
	
	/**
	 * Find role by id
	 */
	public RoleEntity findOneById(Integer id) throws RoleServiceException {
		try {
			return roleDao.findOneById(id);
		} catch (DaoRoleException e) {
			throw new RoleServiceException(lang.getValue("service_role_empty_err"), e);
		}
	}
}
