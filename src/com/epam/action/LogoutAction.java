package com.epam.action;

import java.security.AccessControlException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.entity.UserEntity;
import com.epam.service.UserService;

/**
 * Action for user logout
 * 
 * @author Yuriy Sirotenko
 */
public class LogoutAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		UserEntity user = (UserEntity) ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER);
		
		if (user == null) {
			throw new AccessControlException(lang.getValue("unauth_logout_err"));
		}
		
		UserService userService = new UserService();
		userService.logOut(user);
		
		response.sendRedirect("/BookShop/");
	}

}
