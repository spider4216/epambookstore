package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_respons.AjaxResponse;
import com.epam.component.ajax_respons.AjaxResponseStatus;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.User;
import com.epam.service.BasketService;

public class BasketDeleteBookAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasketService basketService = new BasketService();
		User user = (User) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		basketService.deleteByUserAndBookId(user.getId(), bookId);
		
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		FlashMessage fm = FlashMessage.getInstance();
		fm.setMsg(lang.getValue("delete_book_from_basket_success"));
		
		new AjaxResponse()
		.setRequest(request)
		.setResponse(response)
		.setStatus(AjaxResponseStatus.STATUS_OK)
		.setMessage(lang.getValue("delete_book_from_basket_success"))
		.response();
	}

}
