package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.pagination.Pagination;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
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
		Pagination pager = (Pagination) ServiceLocator.getInstance().getService(ServiceLocatorEnum.PAGINATION);
		pager.setRequest(request);
		ServiceLocator.getInstance().setService(ServiceLocatorEnum.PAGINATION, pager);
		BookService service = new BookService();
		ArrayList<BookEntity> bookCollection = service.findNextPageBooks(pager.getCurrentStartOffset(), pager.COUNT_ITEM);
		request.setAttribute("books", bookCollection);
		request.setAttribute("pager", pager);

		Viewer.execute(request, response, "front.jsp");
	}
}
