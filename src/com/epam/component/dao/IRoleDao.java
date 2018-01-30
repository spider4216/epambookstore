package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoRoleException;

/**
 * Interface for Role dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IRoleDao {
	
	/**
	 * Find role by id
	 */
	ResultSet findOneById(Integer id) throws DaoRoleException;
}
