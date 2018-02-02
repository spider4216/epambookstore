package com.epam.component.route;

import java.util.HashMap;

import com.epam.action.AcceptOrderAction;
import com.epam.action.AdminOrderList;
import com.epam.action.BasketAction;
import com.epam.action.BasketAddToAction;
import com.epam.action.BasketClearAction;
import com.epam.action.BasketDeleteBookAction;
import com.epam.action.BasketOrderAction;
import com.epam.action.BookAction;
import com.epam.action.CategoryAction;
import com.epam.action.HistoryAction;
import com.epam.action.IAction;
import com.epam.action.LangAction;
import com.epam.action.LogoutAction;
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
		map.put("/BookStore/category.html", new CategoryAction());
		map.put("/BookStore/sign-in.html", new SignInMainAction());
		map.put("/BookStore/sign-up.html", new SignUpMainAction());
		map.put("/BookStore/sign-up-process.html", new SignUpProcessAction());
		map.put("/BookStore/sign-in-process.html", new SignInProcessAction());
		map.put("/BookStore/", new MainAction());
		map.put("/BookStore/change-lang.html", new LangAction());
		map.put("/BookStore/book.html", new BookAction());
		map.put("/BookStore/ajax/add-to-basket.html", new BasketAddToAction());
		map.put("/BookStore/basket.html", new BasketAction());
		map.put("/BookStore/ajax/delete-book-from-basket.html", new BasketDeleteBookAction());
		map.put("/BookStore/ajax/clear-basket.html", new BasketClearAction());
		map.put("/BookStore/ajax/order-books.html", new BasketOrderAction());
		map.put("/BookStore/history.html", new HistoryAction());
		map.put("/BookStore/ajax/search.html", new SearchAction());
		map.put("/BookStore/order-list.html", new AdminOrderList());
		map.put("/BookStore/ajax/accept-order.html", new AcceptOrderAction());
		map.put("/BookStore/sign-out.html", new LogoutAction());
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
