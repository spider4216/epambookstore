package com.epam.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.epam.component.dao.MysqlUserDao;
import com.epam.component.dao.exception.ConnectionPoolException;
import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.exception.MysqlDaoException;
import com.epam.component.dao.factory.ConnectionPool;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.UserEntity;
import com.epam.service.exception.UserServiceException;

/**
 * User service
 * 
 * @author Yuriy Sirotenko
 */
public class UserService {
	private static final Integer EMPTY_USER = 0;
	
	private MysqlUserDao userDao;
	
	private Lang lang;
	
	public UserService() throws UserServiceException {
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new UserServiceException("Cannot get lang", e);
		}
		
		try {
			DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
			userDao = (MysqlUserDao)MYSQLFactory.getUserDao();
		} catch (DaoUserException | MysqlDaoException e) {
			throw new UserServiceException(lang.getValue("service_user_get_dao_err"));
		}
	}

	/**
	 * Insert user
	 */
	public Boolean insert(UserEntity entity) throws DaoUserException {
		Integer res = userDao.insertUser(entity);
		
		try {
			ConnectionPool.getInstance().release();
		} catch (ConnectionPoolException e) {
			throw new DaoUserException("err to do with insert", e);
		}
		
		if (res <= EMPTY_USER) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check is user exist
	 */
	public Boolean isUserExist(String username) {
		try {
			userDao.findOneByUsername(username);
			ConnectionPool.getInstance().release();
		} catch (DaoUserException | ConnectionPoolException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Find user by username
	 */
	public UserEntity findByUsername(String username) throws UserServiceException {
		try {
			ResultSet res = userDao.findOneByUsername(username);
			UserEntity user = userSetter(res);
			ConnectionPool.getInstance().release();
			return user;
		} catch (SQLException | DaoUserException | ConnectionPoolException e) {
			throw new UserServiceException(lang.getValue("service_user_cannot_find_user"), e);
		}
	}
	
	/**
	 * Hash Password
	 */
	public String passwordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdPass = md.digest(password.getBytes());
		BigInteger bigInt = new BigInteger(1, mdPass);
		return bigInt.toString(16);
	}
	
	/**
	 * Check is password valid
	 */
	public Boolean isPasswordValid(UserEntity entity, String password) throws NoSuchAlgorithmException, ServiceLocatorException, UserServiceException {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		if (!entity.getPassword().equals(passwordHash(password))) {
			throw new UserServiceException(lang.getValue("invalid_login_or_password"));
		}
		
		return true;
	}
	
	/**
	 * Login process
	 */
	public Boolean login(UserEntity entity) throws UserServiceException {
		try {
			HttpSession session = (HttpSession)ServiceLocator.getInstance().getService(ServiceLocatorEnum.SESSION);
			userDao.updateSissionIdByUsername(entity.getUsername(), session.getId());
			ConnectionPool.getInstance().release();
		} catch (DaoUserException | ServiceLocatorException | ConnectionPoolException e) {
			throw new UserServiceException(lang.getValue("service_user_auth_problem"), e);
		}
		
		return true;
	}
	
	/**
	 * Get current user
	 */
	public UserEntity currentUser() throws UserServiceException {
		
		try {
			HttpSession session = (HttpSession) ServiceLocator.getInstance().getService(ServiceLocatorEnum.SESSION);
			ResultSet res = userDao.findOneBySessionId(session.getId());
			UserEntity user = userSetter(res);
			ConnectionPool.getInstance().release();
			return user;
		} catch (DaoUserException | ServiceLocatorException | SQLException | ConnectionPoolException e) {
			throw new UserServiceException(lang.getValue("service_user_current_user_err"), e);
		}
	}
	
	/**
	 * Set data to user entity
	 */
	private UserEntity userSetter(ResultSet result) throws SQLException {
		UserEntity entity = new UserEntity();
		
		entity.setId(result.getInt("id"));
		entity.setUsername(result.getString("username"));
		entity.setPassword(result.getString("password"));
		entity.setFirstName(result.getString("first_name"));
		entity.setLastName(result.getString("last_name"));
		entity.setGender(result.getInt("gender"));
		
		return entity;
	}
}
