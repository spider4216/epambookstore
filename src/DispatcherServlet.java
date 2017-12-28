import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.action.IAction;
import com.epam.component.route.MapRouter;
import com.epam.component.route.RouterException;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.view.Viewer;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Put Session to Service Locator
		ServiceLocator sl = ServiceLocator.getInstance();
		sl.setService("session", request.getSession(true));
				
		try {
			UserService us = new UserService();
			User user = us.currentUser();
			// TODO use constant for service name
			sl.setService("user", user);
		} catch (UserServiceException e) {
			// TODO use constant for service name
			sl.setService("user", null);
		}
		
		// Getting Writer
		PrintWriter out = response.getWriter();
		// Set type of content
		response.setContentType("text/html");
		// Get url without params
		String path = request.getRequestURI();
		
		// If route does not specified, redirect to front page
		if (path == null || path.equals("/")) {
			// TODO Front Page
			out.println("Front Page Here");
			return;
		}
		
		IAction action = null;
		try {
			action = MapRouter.getAction(path);
		} catch (RouterException e) {
			// TODO 404
			out.println("Page Not Found");
			return;
		}
		
		try {
			action.execute(request, response);
		} catch (Exception e) {
			out.println("Exception error. Pretty page here");
			return;
		}

		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
