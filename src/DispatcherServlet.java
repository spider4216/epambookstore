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

import action.IAction;
import component.route.MapRouter;
import component.route.RouterException;

public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Получаем Writer
		PrintWriter out = response.getWriter();
		// Сетим тип контента
		response.setContentType("text/html");
		// Получаем url без параметров
		String path = request.getPathInfo();
		
		// Если маршрут не указан, направляем на главную страницу
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

		// Получаем Query string
		String queryString = request.getQueryString();
		HashMap<String, String> args = new HashMap<>();
		
		// Если Query string есть, то делаем коллекцию
		if (queryString != null && !queryString.isEmpty()) {
			String[] params = queryString.split("&");
			

			for (String param : params) {
				String[] keyValue = param.split("=");
				args.put(keyValue[0], keyValue[1]);
			}
		}
		
		String res = action.run(args);
		
		out.println(res);

		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
