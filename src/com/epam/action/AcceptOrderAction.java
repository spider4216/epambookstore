package com.epam.action;

import java.security.AccessControlException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.constant.RoleConstant;
import com.epam.entity.UserEntity;
import com.epam.service.OrderService;

/**
 * Accept order action
 * Ajax request was expected by this action
 * 
 * @author Yuriy Sirotenko
 */
public class AcceptOrderAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		if (!user.getRole().getId().equals(RoleConstant.ADMIN)) {
			throw new AccessControlException(lang.getValue("access_denied"));
		}
		
		OrderService orderService = new OrderService();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		orderService.acceptOrder(orderId);
		
		FlashMessage flashMsg = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		flashMsg.setMsg(lang.getValue("order_was_successfully_accepted"));
		
		AjaxResponse ajaxResponse = (AjaxResponse) ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		ajaxResponse.setResponse(response);
		ajaxResponse.setMessage(lang.getValue("order_was_successfully_accepted")).responseOk();
	}
}
