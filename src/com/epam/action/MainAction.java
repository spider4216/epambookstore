package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;
import com.epam.entity.BookEntity;
import com.epam.service.BookService;

/**
 * Main action
 * 
 * @author Yuriy Sirotenko
 */
public class MainAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookService service = new BookService();
		ArrayList<BookEntity> bookCollection = service.findAll();
		request.setAttribute("books", bookCollection);

		Viewer.execute(request, response, "front.jsp");
	}
}
