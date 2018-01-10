package com.epam.component.flash;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;

/**
 * This component put into Service Locator in init script
 * If you want to use this component, don't create new instance. Use
 * Service Locator to get component
 * 
 * @author Yuriy Sirotenko
 */
public class FlashMessage {
	
	private HashMap<String, String> pool;
	
	public FlashMessage() {
		pool = new HashMap<>();
	}
	
	public void setMsg(String msg) throws ServiceLocatorException {
		HttpSession session = getSession();
		
		pool.put(session.getId(), msg);
	}
	
	/**
	 * Get message from pool. If exists, get it and
	 * delete from pool
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public String getMsg() throws ServiceLocatorException {
		HttpSession session = getSession();
		String sId = session.getId();
		
		if (hasMsg() == false) {
			return "";
		}
		
		String msg = pool.get(sId);
		pool.remove(sId);
		
		return msg;
	}
	
	/**
	 * Check, is message exist in pool
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public Boolean hasMsg() throws ServiceLocatorException {
		HttpSession session = getSession();
		String sId = session.getId();
		
		if (pool.containsKey(sId) != true) {
			return false;
		}
		
		return true;
	}
	
	private HttpSession getSession() throws ServiceLocatorException {
		return (HttpSession)ServiceLocator.getInstance().getService(ServiceLocatorEnum.SESSION);
	}
}
