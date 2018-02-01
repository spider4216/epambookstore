package com.epam.component.dao;

import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.entity.BookEntity;

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
	 * Find one book by id
	 */
	BookEntity findBook(Integer id) throws DaoBookException;
	
	/**
	 * Find all books by name with like operator
	 */
	ArrayList<BookEntity> findAllLikeName(String name) throws DaoBookException;
	
	/**
	 * Find all books according to pagination params
	 */
	ArrayList<BookEntity> findBooksWithPagination(Integer offset, Integer limit) throws DaoBookException;
	
	/**
	 * Find all books by name with like operator and category id
	 */
	ArrayList<BookEntity> findAllLikeNameByCategoryId(String name, Integer categoryId) throws DaoBookException;
	
	/**
	 * Find all books by category id with pagination
	 */
	ArrayList<BookEntity> findBooksByCategoryIdWithPagination(Integer categoryId, Integer offset, Integer limit) throws DaoBookException;
}
