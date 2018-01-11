package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoCategoryException;

public interface ICategoryDao {
	public ResultSet findAll() throws DaoCategoryException;
	
	public ResultSet findOneById(Integer id) throws DaoCategoryException;
}
