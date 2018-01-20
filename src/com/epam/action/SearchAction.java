package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;
import com.epam.entity.BookEntity;
import com.epam.service.BookService;

/**
 * Action for search products
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class SearchAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String searchFor = request.getParameter("text");
		String categoryId = request.getParameter("categoryId");
		
		BookService bookService = new BookService();
		ArrayList<BookEntity> collection = null;
		
		if (categoryId == null) {
			collection = bookService.searchByName(searchFor);
		} else {
			collection = bookService.searchByNameAndCategoryId(searchFor, Integer.parseInt(categoryId));
		}
		
		request.setAttribute("books", collection);
		
		Viewer.renderPartial(request, response, "bookList.jsp");
	}
}
