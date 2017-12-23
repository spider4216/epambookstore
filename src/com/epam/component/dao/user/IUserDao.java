package com.epam.component.dao.user;

import java.util.ArrayList;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;

public interface IUserDao {
	public Integer insertUser(User entity) throws DaoUserException;
	
	public Boolean deleteUser() throws DaoUserException;
		
	public User findUser(Integer id) throws DaoUserException;
}
