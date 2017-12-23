package com.epam.component.dao.book;

import java.util.ArrayList;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.entity.Book;

public interface IBookDAO {
	public Integer insertBook() throws DaoBookException;
	
	public Boolean deleteBook() throws DaoBookException;
	
	public ArrayList<Book> findBooks();
	
	public Book findBook(Integer id) throws DaoBookException;
}