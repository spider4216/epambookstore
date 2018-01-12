package com.epam.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;
import com.epam.entity.BookEntity;
import com.epam.service.BookService;
import com.epam.service.exception.BookServiceException;

/**
 * Main action for category domain
 * 
 * @author Yuriy Sirotenko
 */
public class CategoryAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws BookServiceException, ServletException, IOException {
		BookService service = new BookService();
		ArrayList<BookEntity> bookCollection = service.findAllByCategoryId(Integer.parseInt(request.getParameter("id")));
		
		request.setAttribute("books", bookCollection);
		request.setAttribute("category_id", request.getParameter("id"));
		
		Viewer.execute(request, response, "category.jsp");
	}
}
