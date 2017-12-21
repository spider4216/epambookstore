package component.view;

import java.util.HashMap;

import action.IAction;
import action.SortAction;

public class Viewer {
	public static String getViewByAction(IAction action) throws Exception {
		switch (action.getClass().getSimpleName()) {
			case "SortAction":
				return "science.jsp";
			case "SignInMainAction":
				return "signIn.jsp";
			case "SignUpMainAction":
				return "signUp.jsp";

			default:
				// TODO special exception should be situated here
				throw new Exception("View not found");
		}
	}
}
