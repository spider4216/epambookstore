package com.epam.component.dao;

import com.epam.component.dao.exception.DaoUserException;
import com.epam.entity.UserEntity;

/**
 * Interface for user dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IUserDao {
	
	/**
	 * Insert user
	 */
	Integer insertUser(UserEntity entity) throws DaoUserException;
	
	/**
	 * Find user by username
	 */
	UserEntity findOneByUsername(String username) throws DaoUserException;
	
	/**
	 * Update session id by username
	 */
	Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException;
	
	/**
	 * Find user by session id
	 */
	UserEntity findOneBySessionId(String sessionId) throws DaoUserException;
	
	/**
	 * Find user by id
	 */
	UserEntity findOneById(Integer id) throws DaoUserException;
}
