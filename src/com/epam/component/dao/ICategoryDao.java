package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoCategoryException;

/**
 * Interface for category dao
 * 
 * @author Yuriy Sirotenko
 */
public interface ICategoryDao {

	/**
	 * Find all categories
	 */
	public ResultSet findAll() throws DaoCategoryException;
	
	/**
	 * Find category by id
	 */
	public ResultSet findOneById(Integer id) throws DaoCategoryException;
}
