package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;
import com.epam.entity.Book;
import com.epam.entity.CategoryEntity;
import com.epam.service.BookService;
import com.epam.service.CategoryService;

public class BookAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookService serviceBook = new BookService();
		CategoryService serviceCategory = new CategoryService();
		
		Book book = serviceBook.findById(Integer.parseInt(request.getParameter("id")));
		CategoryEntity category = serviceCategory.findOneById(book.getCategoryId());
		request.setAttribute("book", book);
		request.setAttribute("category", category);
		
		Viewer.execute(request, response, "book.jsp");
	}
}
