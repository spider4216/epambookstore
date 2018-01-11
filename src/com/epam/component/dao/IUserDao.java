package com.epam.component.dao;

import java.util.ArrayList;

import com.epam.component.dao.exception.DaoUserException;
import com.epam.entity.UserEntity;

public interface IUserDao {
	public Integer insertUser(UserEntity entity) throws DaoUserException;
	
	public Boolean deleteUser() throws DaoUserException;
		
	public UserEntity findOneById(Integer id) throws DaoUserException;
	
	public Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException;
}
