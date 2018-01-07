package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_respons.AjaxResponse;
import com.epam.component.ajax_respons.AjaxResponseStatus;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.BasketEntity;
import com.epam.entity.Book;
import com.epam.entity.User;
import com.epam.service.BasketService;
import com.epam.service.BookService;
import com.epam.service.UserService;

public class AddToBasketAction implements IAction {
	// TODO catch exceptions in action and response to client
	// TODO block form after click in view
	// TODO alert message js
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer count = Integer.parseInt(request.getParameter("count"));
		
		BookService bookService = new BookService();
		Book book = bookService.findById(id);
		
		User user = (User) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		
		BasketEntity entity = new BasketEntity();
		entity.setCount(count);
		entity.setBookId(book.getId());
		entity.setUserId(user.getId());
		
		
		BasketService basketService = new BasketService();
		basketService.insert(entity);
		
		new AjaxResponse()
		.setRequest(request)
		.setResponse(response)
		.setStatus(AjaxResponseStatus.STATUS_OK)
		.setMessage("Everything is ok")
		.response();
	}

}
