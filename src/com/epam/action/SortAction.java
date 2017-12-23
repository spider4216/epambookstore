package com.epam.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.entity.Book;
import com.epam.service.BookService;

public class SortAction implements IAction {
	public Object execute(HttpServletRequest request, HttpServletResponse response) throws DaoBookException {
		BookService service = new BookService(new Book());
		
		Book book = null;
		book = service.findById(3);
		
		return "My First Sort Action And First Science Method " + book.getName();
	}
}
