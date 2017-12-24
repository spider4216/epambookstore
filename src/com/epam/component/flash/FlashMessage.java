package com.epam.component.flash;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorException;

public class FlashMessage {
	private static FlashMessage instance = null;
	
	private HashMap<String, String> pool;
	
	public FlashMessage() {
		pool = new HashMap<>();
	}
	
	public static FlashMessage getInstance() {
	    if (instance == null) {
	    	instance = new FlashMessage();
	    }

		return instance;
	}
	
	public void setMsg(String msg) throws ServiceLocatorException {
		HttpSession session = getSession();
		
		pool.put(session.getId(), msg);
	}
	
	public String getMsg() throws ServiceLocatorException {
		HttpSession session = getSession();
		String sId = session.getId();
		
		if (pool.containsKey(sId) != true) {
			return "";
		}
		
		String msg = pool.get(sId);
		pool.remove(sId);
		
		return msg;
	}
	
	private HttpSession getSession() throws ServiceLocatorException {
		// TODO constant name of services
		return (HttpSession)ServiceLocator.getInstance().getService("session");
	}
}
