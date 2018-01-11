package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.UserEntity;
import com.epam.service.BasketService;

/**
 * Action for delete product from basket
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class BasketDeleteBookAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasketService basketService = new BasketService();
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		basketService.deleteByUserAndBookId(user.getId(), bookId);
		
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		FlashMessage fm = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		fm.setMsg(lang.getValue("delete_book_from_basket_success"));
		
		AjaxResponse ar = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ar.setResponse(response);
		ar.setMessage(lang.getValue("delete_book_from_basket_success")).responseOk();
	}
}
