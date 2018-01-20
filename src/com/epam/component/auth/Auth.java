package com.epam.component.auth;

import java.util.ArrayList;

import com.epam.action.IAction;
import com.epam.action.SignInMainAction;
import com.epam.action.SignInProcessAction;
import com.epam.action.SignUpMainAction;
import com.epam.action.SignUpProcessAction;

/**
 * Auth component helps exclude actions which 
 * haven't to be part of redirect
 * 
 * @author Yuriy Sirotenko
 */
public class Auth {
	private static ArrayList<String> actions = new ArrayList<>();
	
	static {
		actions.add(SignInMainAction.class.getName());
		actions.add(SignInProcessAction.class.getName());
		actions.add(SignUpMainAction.class.getName());
		actions.add(SignUpProcessAction.class.getName());
	}
	
	public static Boolean contains(IAction action) {
		return actions.contains(action.getClass().getName());
	}
}
