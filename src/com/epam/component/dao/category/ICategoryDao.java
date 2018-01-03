package com.epam.component.dao.category;

import java.sql.ResultSet;

import com.epam.component.dao.category.exception.DaoCategoryException;

public interface ICategoryDao {
	public ResultSet findAll() throws DaoCategoryException;
}
