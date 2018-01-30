package com.epam.component.dao;

import java.sql.ResultSet;

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
	ResultSet findOneByUsername(String username) throws DaoUserException;
	
	/**
	 * Update session id by username
	 */
	Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException;
	
	/**
	 * Find user by session id
	 */
	ResultSet findOneBySessionId(String sessionId) throws DaoUserException;
	
	/**
	 * Find user by id
	 */
	ResultSet findOneById(Integer id) throws DaoUserException;
}
