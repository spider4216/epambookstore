package com.epam.component.route;

import java.util.HashMap;

import com.epam.action.IAction;
import com.epam.action.LangAction;
import com.epam.action.MainAction;
import com.epam.action.SignInMainAction;
import com.epam.action.SignInProcessAction;
import com.epam.action.SignUpMainAction;
import com.epam.action.SignUpProcessAction;
import com.epam.action.AddToBasket;
import com.epam.action.BasketAction;
import com.epam.action.BookAction;
import com.epam.action.CategoryAction;

public class MapRouter {
	private static HashMap<String, IAction> map = new HashMap<>();
	
	static {
		map.put("/BookShop/category.html", new CategoryAction());
		map.put("/BookShop/sign-in.html", new SignInMainAction());
		map.put("/BookShop/sign-up.html", new SignUpMainAction());
		map.put("/BookShop/sign-up-process.html", new SignUpProcessAction());
		map.put("/BookShop/sign-in-process.html", new SignInProcessAction());
		map.put("/BookShop/", new MainAction());
		map.put("/BookShop/change-lang.html", new LangAction());
		map.put("/BookShop/book.html", new BookAction());
		map.put("/BookShop/ajax/add-to-basket.html", new AddToBasket());
		map.put("/BookShop/basket.html", new BasketAction());
	}
	
	public static IAction getAction(String path) throws RouterException {
		if (!map.containsKey(path)) {
			throw new RouterException("Route does not exist");
		}
		
		return map.get(path);
	}
}
