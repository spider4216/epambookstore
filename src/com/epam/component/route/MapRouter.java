package com.epam.component.route;

import java.util.HashMap;

import com.epam.action.IAction;
import com.epam.action.LangAction;
import com.epam.action.MainAction;
import com.epam.action.SearchAction;
import com.epam.action.SignInMainAction;
import com.epam.action.SignInProcessAction;
import com.epam.action.SignUpMainAction;
import com.epam.action.SignUpProcessAction;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.action.BasketAddToAction;
import com.epam.action.BasketAction;
import com.epam.action.BasketClearAction;
import com.epam.action.BasketDeleteBookAction;
import com.epam.action.BasketOrderAction;
import com.epam.action.BookAction;
import com.epam.action.CategoryAction;
import com.epam.action.HistoryAction;

/**
 * Main router component. If you create your own action
 * you have to map this one with url. Each url has suffix ".html" and
 * prefix "BookShop"
 * 
 * @author Yuriy Sirotenko
 */
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
		map.put("/BookShop/ajax/add-to-basket.html", new BasketAddToAction());
		map.put("/BookShop/basket.html", new BasketAction());
		map.put("/BookShop/ajax/delete-book-from-basket.html", new BasketDeleteBookAction());
		map.put("/BookShop/ajax/clear-basket.html", new BasketClearAction());
		map.put("/BookShop/ajax/order-books.html", new BasketOrderAction());
		map.put("/BookShop/history.html", new HistoryAction());
		map.put("/BookShop/ajax/search.html", new SearchAction());
	}
	
	public static IAction getAction(String path) throws RouterException {
		Lang lang = null;
		
		try {
			lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new RouterException("Problem with service lang", e);
		}
		
		if (!map.containsKey(path)) {
			throw new RouterException(lang.getValue("route_does_not_exist"));
		}
		
		return map.get(path);
	}
}
