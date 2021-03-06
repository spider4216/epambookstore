package com.epam.action;

import java.security.AccessControlException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.view.Viewer;

/**
 * Sign in main action
 * 
 * @author Yuriy Sirotenko
 */
public class SignInMainAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		if (ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER) != null) {
			throw new AccessControlException(lang.getValue("has_already_sign_in_access_error"));
		}
		
		FlashMessage flashMessage = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		
		request.setAttribute("fm", flashMessage);

		Viewer.execute(request, response, "signIn.jsp");
	}
}
