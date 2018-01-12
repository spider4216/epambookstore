package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;
import com.epam.entity.BookEntity;
import com.epam.service.BookService;

public class SearchAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String searchFor = request.getParameter("text");
		
		BookService bookService = new BookService();
		ArrayList<BookEntity> collection = bookService.searchByName(searchFor);
		
		request.setAttribute("books", collection);
		
		Viewer.renderPartial(request, response, "bookList.jsp");
	}

}
