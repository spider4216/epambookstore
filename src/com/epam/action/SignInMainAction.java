package com.epam.action;

import java.io.IOException;
import java.security.AccessControlException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.view.Viewer;

public class SignInMainAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceLocatorException {
		if (ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER) != null) {
			throw new AccessControlException("You have already signed in");
		}
		
		Viewer.execute(request, response, "signIn.jsp");
	}
	
}
