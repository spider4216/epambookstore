package com.epam.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.epam.component.dao.factory.DAOFactory;
import com.epam.component.dao.user.MysqlUserDao;
import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;

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
	
	public String passwordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdPass = md.digest(password.getBytes());
		BigInteger bigInt = new BigInteger(1, mdPass);
		return bigInt.toString(16);
	}
}
