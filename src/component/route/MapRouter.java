package component.route;

import java.util.HashMap;

import action.IAction;
import action.SortAction;

public class MapRouter {
	private static HashMap<String, IAction> map = new HashMap<>();
	
	static {
		map.put("/BookShop/sort/science.html", new SortAction());
	}
	
	public static IAction getAction(String path) throws RouterException {
		if (!map.containsKey(path)) {
			throw new RouterException("Route does not exist");
		}
		
		return map.get(path);
	}
}
