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
import com.epam.action.SignInMainAction;
import com.epam.action.SignInProcessAction;
import com.epam.action.SignUpMainAction;
import com.epam.action.SignUpProcessAction;
import com.epam.component.lang.Lang;
import com.epam.component.route.MapRouter;
import com.epam.component.route.RouterException;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.view.Viewer;
import com.epam.entity.UserEntity;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;
import com.epam.system.Init;

public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// Put Session to Service Locator
		ServiceLocator sl = ServiceLocator.getInstance();
		sl.setService(ServiceLocatorEnum.SESSION, request.getSession(true));

		try {
			Init.execute(request, response);
		} catch (ServiceLocatorException e) {
			request.setAttribute("errMsg", e.getMessage());
			Viewer.execute(request, response, "error.jsp");
			return;
		}
		
		// Set type of content
		response.setContentType("text/html");
		// Get url without params
		String path = request.getRequestURI();
		
		IAction action = null;
		try {
			action = MapRouter.getAction(path);
		} catch (RouterException e) {
			request.setAttribute("errMsg", e.getMessage());
			Viewer.execute(request, response, "error.jsp");
			return;
		}
		
		// Redirect to login if not auth
		if (
				!(action instanceof SignInMainAction) &&
				!(action instanceof SignInProcessAction) &&
				!(action instanceof SignUpMainAction) &&
				!(action instanceof SignUpProcessAction)
			) {
			// Redirect to auth page if user not log in
			try {
				if (ServiceLocator.getInstance().getService(ServiceLocatorEnum.USER) == null) {
					response.sendRedirect("/BookShop/sign-in.html");
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
			System.out.println(123);
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute("errMsg", e.getMessage());
			Viewer.execute(request, response, "error.jsp");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
