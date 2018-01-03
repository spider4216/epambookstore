package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;
import com.epam.entity.Book;
import com.epam.service.BookService;

public class BookAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookService service = new BookService();
		
		Book book = service.findById(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("book", book);
		
		Viewer.execute(request, response, "book.jsp");
	}
}
