package com.epam.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;
import com.epam.entity.BasketEntity;
import com.epam.entity.UserEntity;
import com.epam.service.BasketService;

/**
 * History main action
 * 
 * @author Yuriy Sirotenko
 */
public class HistoryAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasketService basketService = new BasketService();
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER); 
		ArrayList<BasketEntity> history = basketService.getUserHistory(user.getId());
		
		request.setAttribute("history", history);
		
		Viewer.execute(request, response, "history.jsp");
	}
}
