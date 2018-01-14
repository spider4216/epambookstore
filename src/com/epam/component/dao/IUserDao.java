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
	public Integer insertUser(UserEntity entity) throws DaoUserException;
	
	/**
	 * Find user by username
	 */
	public ResultSet findOneByUsername(String username) throws DaoUserException;
	
	/**
	 * Update session id by username
	 */
	public Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException;
	
	/**
	 * Find user by session id
	 */
	public ResultSet findOneBySessionId(String sessionId) throws DaoUserException;
}
