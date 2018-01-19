package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.UserEntity;
import com.epam.service.OrderService;

public class AcceptOrderAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		OrderService orderService = new OrderService();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		orderService.acceptOrder(orderId);
		
		FlashMessage fm = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		fm.setMsg(lang.getValue("order_was_successfully_accepted"));
		
		AjaxResponse ar = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ar.setResponse(response);
		ar.setMessage(lang.getValue("order_was_successfully_accepted")).responseOk();
	}
}
