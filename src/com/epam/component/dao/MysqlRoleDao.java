package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

public class MysqlRoleDao implements IRoleDao {
	
	private Lang lang = null;
	
	public MysqlRoleDao() throws DaoRoleException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoRoleException("cannot get lang", e);
		}
	}

	public ResultSet findOneById(Integer id) throws DaoRoleException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlFind = "SELECT * FROM roles WHERE id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			res.next();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoRoleException(lang.getValue("dao_role_empty_err"), e);
		}
	}

}
