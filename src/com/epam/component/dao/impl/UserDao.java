package com.epam.component.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.CDao;
import com.epam.component.dao.IStatementIndex;
import com.epam.component.dao.IUserDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoRoleException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.RoleEntity;
import com.epam.entity.UserEntity;
import com.epam.enum_list.RoleEnum;

/**
 * Mysql users dao
 * 
 * @author Yuriy Sirotenko
 */
public class UserDao extends CDao implements IUserDao {
	private final static String SQL_INSERT_USER = "INSERT INTO users (username, password, first_name, last_name, gender, role_id) VALUES (?, ?, ?, ?, ?, ?)";

	private final static String SQL_FIND_ONE_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

	private final static String SQL_UPDATE_SESSION_ID_BY_USERNAME = "UPDATE users SET session_id = ? where username = ?";

	private final static String SQL_FIND_ONE_BY_SESSION_ID = "SELECT * FROM users WHERE session_id = ?";

	private final static String SQL_FIND_ONE_BY_ID = "SELECT * FROM users WHERE id = ?";
	
	private static final Integer EMPTY_USER = 0;
	
	private Lang lang = null;
	
	public UserDao() throws ServiceLocatorException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
	}
	
	/**
	 * Insert user
	 */
	public Integer insertUser(UserEntity entity) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_INSERT_USER);
			pr.setString(IStatementIndex.FIRST, entity.getUsername());
			pr.setString(IStatementIndex.SECOND, entity.getPassword());
			pr.setString(IStatementIndex.THIRD, entity.getFirstName());
			pr.setString(IStatementIndex.FORTH, entity.getLastName());
			pr.setInt(IStatementIndex.FIFTH, entity.getGender());
			pr.setInt(IStatementIndex.SIXTH, RoleEnum.USER.getValue());
			
			Integer result = pr.executeUpdate();
			closeResources(pr);
			
			return result;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_cannot_inser_err"), e);
		}
	}

	/**
	 * Find user by username
	 */
	public UserEntity findOneByUsername(String username) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_USERNAME);
			pr.setString(IStatementIndex.FIRST, username);
			ResultSet result = pr.executeQuery();
			
			result.next();

			if (result.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}
			
			UserEntity user = userSetter(result);
			closeResources(pr, result);

			return user;
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
			PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_SESSION_ID_BY_USERNAME);
			pr.setString(IStatementIndex.FIRST, sessionId);
			pr.setString(IStatementIndex.SECOND, username);
			
			Integer result = pr.executeUpdate();
			closeResources(pr);
			
			return result;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_session_update_err"), e);
		}
	}
	
	/**
	 * Find user by session id
	 */
	public UserEntity findOneBySessionId(String sessionId) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionPool.getInstance().freeConnection(connection);
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_SESSION_ID);
			pr.setString(IStatementIndex.FIRST, sessionId);
			ResultSet result = pr.executeQuery();
			result.next();

			if (result.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}
			
			UserEntity user = userSetter(result);
			
			closeResources(pr, result);

			return user;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_not_found"), e);
		}
	}
	
	/**
	 * Find user by id
	 */
	public UserEntity findOneById(Integer id) throws DaoUserException {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pr = connection.prepareStatement(SQL_FIND_ONE_BY_ID);
			pr.setInt(IStatementIndex.FIRST, id);
			ResultSet result = pr.executeQuery();
			
			result.next();

			if (result.getRow() <= EMPTY_USER) {
				throw new DaoUserException(lang.getValue("dao_user_not_found"));
			}
			
			UserEntity user = userSetter(result);
			
			closeResources(pr, result);

			return user;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoUserException(lang.getValue("dao_user_not_found"), e);
		}
	}
	
	/**
	 * Set data to user entity
	 */
	private UserEntity userSetter(ResultSet result) throws DaoUserException {
		try {
			UserEntity entity = new UserEntity();
			
			entity.setId(result.getInt("id"));
			entity.setUsername(result.getString("username"));
			entity.setPassword(result.getString("password"));
			entity.setFirstName(result.getString("first_name"));
			entity.setLastName(result.getString("last_name"));
			entity.setGender(result.getInt("gender"));
			
			Integer roleId = result.getInt("role_id");
			
			DaoFactory MysqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			RoleDao roleDao = (RoleDao) MysqlFactory.getRoleDao();
			
			RoleEntity role = roleDao.findOneById(roleId);
			entity.setRole(role);
			
			return entity;
		} catch (DaoRoleException | SQLException e) {
			throw new DaoUserException(lang.getValue("dao_user_role_getting_err"), e);
		}
	}
}
