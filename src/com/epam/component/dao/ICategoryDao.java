package com.epam.component.dao;

import java.util.ArrayList;

import com.epam.component.dao.exception.DaoCategoryException;
import com.epam.entity.CategoryEntity;

/**
 * Interface for category dao
 * 
 * @author Yuriy Sirotenko
 */
public interface ICategoryDao {

	/**
	 * Find all categories
	 */
	ArrayList<CategoryEntity> findAll() throws DaoCategoryException;
	
	/**
	 * Find category by id
	 */
	CategoryEntity findOneById(Integer id) throws DaoCategoryException;
}
