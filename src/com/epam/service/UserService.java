package com.epam.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.epam.component.dao.factory.DAOFactory;
import com.epam.component.dao.user.MysqlUserDao;
import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.User;
import com.epam.service.exception.UserServiceException;

public class UserService {
	private MysqlUserDao userDao;
	
	public UserService() throws DaoUserException {
		DAOFactory MYSQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		userDao = (MysqlUserDao)MYSQLFactory.getUserDAO();
	}

	public Boolean insert(User entity) throws DaoUserException {
		Integer res = userDao.insertUser(entity);
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean isUserExist(String username) {
		try {
			userDao.findOneByUsername(username);
		} catch (DaoUserException e) {
			return false;
		}
		
		return true;
	}
	
	// TODO write in documentation fact that every service have to return entity
	public User findByUsername(String username) throws UserServiceException {
		ResultSet res;
		try {
			res = userDao.findOneByUsername(username);
		} catch (DaoUserException e) {
			throw new UserServiceException("Cannot find user with username specified", e);
		}
		User entity = new User();
		
		try {
			entity.setId(res.getInt("id"));
			entity.setUsername(res.getString("username"));
			entity.setPassword(res.getString("password"));
			entity.setFirstName(res.getString("first_name"));
			entity.setLastName(res.getString("last_name"));
			entity.setGender(res.getInt("gender"));
		} catch (SQLException e) {
			throw new UserServiceException("Problem with getting data in user service", e);
		}
		
		return entity;
	}
	
	public String passwordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdPass = md.digest(password.getBytes());
		BigInteger bigInt = new BigInteger(1, mdPass);
		return bigInt.toString(16);
	}
	
	public Boolean isPasswordValid(User entity, String password) throws NoSuchAlgorithmException {
		if (entity.getPassword().equals(passwordHash(password))) {
			return true;
		}
		
		return false;
	}
	
	public Boolean login(User entity) throws UserServiceException {
		// TODO constant session here
		HttpSession session = null;
		try {
			session = (HttpSession)ServiceLocator.getInstance().getService("session");
		} catch (ServiceLocatorException e) {
			throw new UserServiceException("cannot login", e);
		}
		
		try {
			userDao.updateSissionIdByUsername(entity.getUsername(), session.getId());
		} catch (DaoUserException e) {
			throw new UserServiceException("cannot auth. Problem with session set", e);
		}
		
		return true;
	}
}
