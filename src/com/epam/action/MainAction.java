package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;
import com.epam.entity.Book;
import com.epam.service.BookService;

public class MainAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookService service = new BookService();
		// TODO if empty
		ArrayList<Book> bookCollection = service.findAll();
		request.setAttribute("books", bookCollection);

		Viewer.execute(request, response, "front.jsp");
	}

}
