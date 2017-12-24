package com.epam.component.service_locator;

import java.util.HashMap;

public class ServiceLocator {
	private static ServiceLocator instance = null;
	
	private HashMap<String, Object> pool;
	
	public ServiceLocator() {
		pool = new HashMap<>();
	}
	
	public static ServiceLocator getInstance() {
	    if (instance == null) {
	    	instance = new ServiceLocator();
	    }

		return instance;
	}
	
	public void setService(String name, Object service) {
		pool.put(name, service);
	}
	
	public Object getService(String name) throws ServiceLocatorException {
		if (pool.containsKey(name) == false) {
			throw new ServiceLocatorException("Cannot find service with specified name");
		}
		
		return pool.get(name);
	}
}
