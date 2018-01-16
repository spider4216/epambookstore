package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoRoleException;

public interface IRoleDao {
	public ResultSet findOneById(Integer id) throws DaoRoleException;
}
