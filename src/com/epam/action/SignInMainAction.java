package com.epam.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInMainAction implements IAction {

	public Object execute(HttpServletRequest request, HttpServletResponse response) {
		return "Hello SignIn action";
	}
	
}
