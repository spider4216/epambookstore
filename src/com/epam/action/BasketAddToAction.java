package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.BasketEntity;
import com.epam.entity.BookEntity;
import com.epam.entity.UserEntity;
import com.epam.service.BasketService;
import com.epam.service.BookService;

/**
 * Add product into basket
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class BasketAddToAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer count = Integer.parseInt(request.getParameter("count"));
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		BookService bookService = new BookService();
		BookEntity book = bookService.findById(id);
		
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		
		BasketEntity entity = new BasketEntity();
		entity.setCount(count);
		entity.setBookId(book.getId());
		entity.setUserId(user.getId());
		
		
		BasketService basketService = new BasketService();
		basketService.insert(entity);
		AjaxResponse ajaxResponse = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ajaxResponse.setResponse(response);
		ajaxResponse.setMessage(lang.getValue("book_added_to_basket_success_hint")).responseOk();
	}
}
