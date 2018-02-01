package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.CDao;
import com.epam.component.dao.IRoleDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.RoleEntity;

/**
 * Mysql roles dao
 * 
 * @author Yuriy Sirotenko
 */
public class RoleDao extends CDao implements IRoleDao {
	
	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM roles WHERE id = ?";
	
	private Lang lang = null;
	
	public RoleDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}

	/**
	 * Find role by id
	 */
	public RoleEntity findOneById(Integer id) throws DaoRoleException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet result = pr.executeQuery();
			result.next();
			
			RoleEntity role = roleSetter(result);
			closeResources(pr, result);
			
			return role;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoRoleException(lang.getValue("dao_role_empty_err"), e);
		}
	}
	
	/**
	 * Role setter
	 */
	private RoleEntity roleSetter(ResultSet result) throws SQLException {
		RoleEntity role = new RoleEntity();
		role.setId(result.getInt("id"));
		role.setName(result.getString("name"));
		
		return role;
	}
}
