package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.constant.RoleConstant;
import com.epam.entity.UserEntity;

/**
 * Mysql user dao
 * 
 * @author Yuriy Sirotenko
 */
public class MysqlUserDao implements IUserDao {
	private static final Integer EMPTY_USER = 0;
	
	private Lang lang = null;
	
	public MysqlUserDao() throws DaoUserException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new DaoUserException("Cannot get lang", e);
		}
	}
	
	/**
	 * Insert user
	 */
	public Integer insertUser(UserEntity entity) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			String sqlInsert = "INSERT INTO user (username, password, first_name, last_name, gender, role_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setString(1, entity.getUsername());
			pr.setString(2, entity.getPassword());
			pr.setString(3, entity.getFirstName());
			pr.setString(4, entity.getLastName());
			pr.setInt(5, entity.getGender());
			pr.setInt(6, RoleConstant.USER);
			
			Integer res = pr.executeUpdate();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_cannot_inser_err"), e);
		}
	}

	/**
	 * Find user by username
	 */
	public ResultSet findOneByUsername(String username) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			String sqlFind = "SELECT * FROM user WHERE username = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(1, username);
			ResultSet res = pr.executeQuery();
			
			res.next();

			if (res.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_not_found"), e);
		}
	}
	
	/**
	 * Update session id by username
	 */
	public Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlUpdate = "UPDATE user SET session_id = ? where username = ?";
			PreparedStatement pr = connection.prepareStatement(sqlUpdate);
			pr.setString(1, sessionId);
			pr.setString(2, username);
			
			Integer res = pr.executeUpdate();
			
			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_session_update_err"), e);
		}
	}
	
	/**
	 * Find user by session id
	 */
	public ResultSet findOneBySessionId(String sessionId) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			String sqlSelect = "SELECT * FROM user WHERE session_id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlSelect);
			pr.setString(1, sessionId);
			ResultSet res = pr.executeQuery();
			res.next();

			if (res.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_not_found"), e);
		}
	}
	
	public ResultSet findOneById(Integer id) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			String sqlFind = "SELECT * FROM user WHERE id = ?";
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			ResultSet res = pr.executeQuery();
			
			res.next();

			if (res.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}

			return res;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_not_found"), e);
		}
	}
}
