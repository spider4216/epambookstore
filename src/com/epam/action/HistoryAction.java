package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;
import com.epam.constant.OrderStatus;
import com.epam.entity.OrderEntity;
import com.epam.entity.UserEntity;
import com.epam.service.OrderService;

/**
 * History main action
 * 
 * @author Yuriy Sirotenko
 */
public class HistoryAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrderService orderService = new OrderService();
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER); 
		
		ArrayList<OrderEntity> orders = orderService.findAllUserOrders(user.getId());
		request.setAttribute("orders", orders);
		request.setAttribute("approveStatus", OrderStatus.APPROVED);
		request.setAttribute("underConsiderationStatus", OrderStatus.UNDER_CONSIDERATION);
		request.setAttribute("orderService", orderService);
		
		Viewer.execute(request, response, "history.jsp");
	}
}
