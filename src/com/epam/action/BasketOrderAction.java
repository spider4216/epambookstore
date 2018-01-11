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
 * Order action
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class BasketOrderAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		BasketService basketService = new BasketService();
		basketService.markBooksAsHistoryByUser(user);
		
		FlashMessage fm = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		fm.setMsg(lang.getValue("basket_ordered_success_hint"));
		
		AjaxResponse ar = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ar.setResponse(response);
		ar.setMessage(lang.getValue("basket_ordered_success_hint")).responseOk();
	}
}
