package com.epam;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.action.IAction;
import com.epam.component.auth.Auth;
import com.epam.component.route.MapRouter;
import com.epam.component.route.RouterException;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.view.Viewer;
import com.epam.system.Init;

public class DispatcherServlet extends HttpServlet {
	
	public final static Logger logger = Logger.getLogger(DispatcherServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Put Session to Service Locator
		ServiceLocator sl = ServiceLocator.getInstance();
		sl.setService(ServiceLocatorEnum.SESSION, request.getSession(true));

		Init.execute(request, response);
		
		// Set type of content
		response.setContentType("text/html");
		// Get url without params
		String path = request.getRequestURI();
		
		IAction action = null;
		try {
			action = MapRouter.getAction(path);
		} catch (RouterException e) {
			logger.error(e.getMessage());
			request.setAttribute("errMsg", e.getMessage());
			Viewer.execute(request, response, "error.jsp");
			return;
		}
		
		// Redirect to login if not auth
		if (!Auth.contains(action)) {
			// Redirect to auth page if user not log in
			try {
				if (ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER) == null) {
					response.sendRedirect("/sign-in.html");
				}
			} catch (ServiceLocatorException e) {
				request.setAttribute("errMsg", e.getMessage());
				Viewer.execute(request, response, "error.jsp");
				return;
			}
		}
		
		try {
			action.execute(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("errMsg", e.getMessage());
			Viewer.execute(request, response, "error.jsp");
			return;
		}
	}
}
