package com.epam.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.exception.DaoBookException;
import com.epam.component.pagination.Pagination;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
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
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pagination pager = (Pagination) ServiceLocator.getInstance().getService(ServiceLocatorEnum.PAGINATION);
		pager.setRequest(request);
		BookService service = new BookService();
		ArrayList<BookEntity> bookCollection = service.findNextPageCategoryBooks(Integer.parseInt(request.getParameter("id")), pager.getCurrentStartOffset(), pager.COUNT_ITEM);
		
		request.setAttribute("books", bookCollection);
		request.setAttribute("category_id", request.getParameter("id"));
		request.setAttribute("pager", pager);
		
		Viewer.execute(request, response, "category.jsp");
	}
}
