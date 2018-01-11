package com.epam.component.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.entity.BookEntity;

public interface IBookDao {
	public Integer insertBook(BookEntity entity) throws DaoBookException;
	
	public Boolean deleteBook(BookEntity entity) throws DaoBookException;
	
	public ResultSet findBooks() throws DaoBookException;
	
	public ResultSet findBook(Integer id) throws DaoBookException;
	
	public ResultSet findAllByCategoryId(Integer id) throws DaoBookException;
}
