package com.epam.component.dao;

import java.sql.ResultSet;

import com.epam.component.dao.exception.DaoBookException;

/**
 * Interface for Book Dao
 * 
 * @author Yuriy Sirotenko
 */
public interface IBookDao {
	
	/**
	 * Delete book by ID
	 */
	Boolean deleteBook(Integer id) throws DaoBookException;
	
	/**
	 * Find All books
	 */
	ResultSet findBooks() throws DaoBookException;
	
	/**
	 * Find one book by id
	 */
	ResultSet findBook(Integer id) throws DaoBookException;
	
	/**
	 * Find all books by category id
	 */
	ResultSet findAllByCategoryId(Integer id) throws DaoBookException;
	
	/**
	 * Find all books by name with like operator
	 */
	ResultSet findAllLikeName(String name) throws DaoBookException;
	
	/**
	 * Find all books according to pagination params
	 */
	ResultSet findBooksWithPagination(Integer offset, Integer limit) throws DaoBookException;
	
	/**
	 * Find all books by name with like operator and category id
	 */
	ResultSet findAllLikeNameByCategoryId(String name, Integer categoryId) throws DaoBookException;
}
