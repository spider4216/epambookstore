package com.epam.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.view.Viewer;

public class SignInMainAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Viewer.execute(request, response, "signIn.jsp");
	}
	
}
