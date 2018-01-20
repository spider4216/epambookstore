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
 * Action for clear basket
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class BasketClearAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		BasketService basketService = new BasketService();
		basketService.deleteUserBasketBooks(user.getId());

		FlashMessage flashMessage = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		flashMessage.setMsg(lang.getValue("basket_clear_success_flash_message"));
		
		AjaxResponse ajaxResponse = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ajaxResponse.setResponse(response);
		ajaxResponse.setMessage(lang.getValue("basket_clear_success_flash_message")).responseOk();
	}
}
