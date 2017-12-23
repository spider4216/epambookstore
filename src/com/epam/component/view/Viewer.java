package com.epam.component.view;

import java.util.HashMap;

import com.epam.action.IAction;
import com.epam.action.SortAction;

public class Viewer {
	public static String getViewByAction(IAction action) throws Exception {
		switch (action.getClass().getSimpleName()) {
			case "SortAction":
				return "science.jsp";
			case "SignInMainAction":
				return "signIn.jsp";
			case "SignUpMainAction":
				return "signUp.jsp";
			case "SignUpProcessAction":
				return "signUpProcess.jsp";

			default:
				// TODO special exception should be situated here
				throw new Exception("View not found");
		}
	}
}
