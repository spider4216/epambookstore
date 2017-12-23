package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.entity.Book;
import com.epam.service.BookService;

public class SortAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws DaoBookException {
		BookService service = new BookService(new Book());
		
		Book book = null;
		book = service.findById(3);		
	}
}
