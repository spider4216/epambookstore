package com.epam.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.dao.factory.DaoFactory;
import com.epam.component.dao.impl.UserDao;
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
	
	private UserDao userDao;
	
	private Lang lang;
	
	public UserService() throws ServiceLocatorException, DaoUserException {
		lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		DaoFactory MYSQLFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
		userDao = (UserDao)MYSQLFactory.getUserDao();
	}

	/**
	 * Insert user
	 */
	public Boolean insert(UserEntity entity) throws UserServiceException {
		Integer res;
		try {
			res = userDao.insertUser(entity);
		} catch (DaoUserException e) {
			throw new UserServiceException(lang.getValue("service_user_insert_err"), e);
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
		} catch (DaoUserException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Find user by username
	 */
	public UserEntity findByUsername(String username) throws UserServiceException {
		try {
			return userDao.findOneByUsername(username);
		} catch (DaoUserException e) {
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
		} catch (DaoUserException | ServiceLocatorException e) {
			throw new UserServiceException(lang.getValue("service_user_auth_problem"), e);
		}
		
		return true;
	}
	
	/**
	 * Logout
	 */
	public Boolean logOut(UserEntity entity) throws UserServiceException {
		try {
			userDao.updateSissionIdByUsername(entity.getUsername(), "");
		} catch (DaoUserException e) {
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
			return userDao.findOneBySessionId(session.getId());
		} catch (DaoUserException | ServiceLocatorException e) {
			throw new UserServiceException(lang.getValue("service_user_current_user_err"), e);
		}
	}
}
