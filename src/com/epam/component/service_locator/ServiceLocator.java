package com.epam.component.service_locator;

import java.util.HashMap;

/**
 * Service locator component. The most important component in
 * application. Container contains significant services which
 * were often used from different part of application
 * Service locator start to put services in front controller
 * and init class
 * 
 * @author Yuriy Sirotenko
 */
public class ServiceLocator {
	private static ServiceLocator instance = null;
	
	private HashMap<ServiceLocatorEnum, Object> pool;
	
	private ServiceLocator() {
		pool = new HashMap<>();
	}
	
	public static ServiceLocator getInstance() {
	    if (instance == null) {
	    	instance = new ServiceLocator();
	    }

		return instance;
	}
	
	public void setService(ServiceLocatorEnum name, Object service) {
		pool.put(name, service);
	}
	
	public Object getService(ServiceLocatorEnum name) throws ServiceLocatorException {
		if (!pool.containsKey(name)) {
			throw new ServiceLocatorException("Cannot find service with specified name");
		}
		
		return pool.get(name);
	}
}
