package com.epam.component.dao.book;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.entity.Book;

public interface IBookDao {
	public Integer insertBook(Book entity) throws DaoBookException;
	
	public Boolean deleteBook(Book entity) throws DaoBookException;
	
	public ResultSet findBooks() throws DaoBookException;
	
	public Book findBook(Integer id) throws DaoBookException;
	
	public ResultSet findAllByCategoryId(Integer id) throws DaoBookException;
}
