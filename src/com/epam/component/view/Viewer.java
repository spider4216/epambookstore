package com.epam.component.view;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Viewer {	
	public static void execute(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
		request.setAttribute("includeJsp", name);
		
		request.getRequestDispatcher("/jsp/main.jsp").include(request, response);
	}
}
