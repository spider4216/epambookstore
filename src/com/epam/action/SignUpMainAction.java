package com.epam.action;

import java.io.IOException;
import java.security.AccessControlException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.view.Viewer;

/**
 * Sign up action
 * 
 * @author Yuriy Sirotenko
 */
public class SignUpMainAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceLocatorException {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		
		if (ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER) != null) {
			throw new AccessControlException(lang.getValue("have_already_signed_up_access_error"));
		}
		
		FlashMessage flashMessage = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		
		request.setAttribute("fm", flashMessage);
		
		Viewer.execute(request, response, "signUp.jsp");
	}
}
