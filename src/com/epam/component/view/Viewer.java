package com.epam.component.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.entity.CategoryEntity;
import com.epam.service.CategoryService;
import com.epam.service.exception.CategoryServiceException;

public class Viewer {	
	public static void execute(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
		request.setAttribute("includeJsp", name);
		
		try {
			// get all categories for layout
			ArrayList<CategoryEntity> categories = new CategoryService().findAll();
			request.setAttribute("categories", categories);
		} catch (CategoryServiceException e) {
			request.setAttribute("categories", null);
		}
		
		request.getRequestDispatcher("/jsp/main.jsp").include(request, response);
	}
	
	public static void renderPartial(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/" + name).include(request, response);
	}
}
