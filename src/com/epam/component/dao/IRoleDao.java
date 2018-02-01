package com.epam.component.dao;

import com.epam.component.dao.exception.DaoRoleException;
import com.epam.entity.RoleEntity;

/**
 * Interface for Role dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IRoleDao {
	
	/**
	 * Find role by id
	 */
	RoleEntity findOneById(Integer id) throws DaoRoleException;
}
