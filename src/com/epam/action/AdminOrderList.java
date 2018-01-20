package com.epam.action;

import java.security.AccessControlException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;
import com.epam.constant.RoleConstant;
import com.epam.entity.OrderEntity;
import com.epam.entity.UserEntity;
import com.epam.service.OrderService;

/**
 * Admin order list action
 * 
 * @author Yuriy Sirotenko
 */
public class AdminOrderList implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		OrderService orderService = new OrderService();
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER); 
		
		if (user.getRole().getId() != RoleConstant.ADMIN) {
			throw new AccessControlException(lang.getValue("access_denied"));
		}
		
		ArrayList<OrderEntity> orders = orderService.findAllNotApproved();
		request.setAttribute("orders", orders);
		request.setAttribute("orderService", orderService);
		
		Viewer.execute(request, response, "orderList.jsp");
	}
}
