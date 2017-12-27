package com.epam.component.route;

import java.util.HashMap;

import com.epam.action.IAction;
import com.epam.action.SignInMainAction;
import com.epam.action.SignInProcessAction;
import com.epam.action.SignUpMainAction;
import com.epam.action.SignUpProcessAction;
import com.epam.action.SortAction;

public class MapRouter {
	private static HashMap<String, IAction> map = new HashMap<>();
	
	static {
		map.put("/BookShop/sort/science.html", new SortAction());
		map.put("/BookShop/sign-in.html", new SignInMainAction());
		map.put("/BookShop/sign-up.html", new SignUpMainAction());
		map.put("/BookShop/sign-up-process.html", new SignUpProcessAction());
		map.put("/BookShop/sign-in-process.html", new SignInProcessAction());
	}
	
	public static IAction getAction(String path) throws RouterException {
		if (!map.containsKey(path)) {
			throw new RouterException("Route does not exist");
		}
		
		return map.get(path);
	}
}
