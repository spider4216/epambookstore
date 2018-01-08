package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.User;
import com.epam.service.BasketService;

public class BasketClearAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		BasketService basketService = new BasketService();
		basketService.deleteUserBasketBooks(user.getId());
		FlashMessage fm = FlashMessage.getInstance();
		fm.setMsg(lang.getValue("basket_clear_success_flash_message"));
		
		AjaxResponse ar = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ar.setMessage(lang.getValue("basket_clear_success_flash_message")).responseOk();
	}

}
