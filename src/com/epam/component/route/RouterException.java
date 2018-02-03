package com.epam.component.route;

/**
 * Router exception
 * 
 * @author Yuriy Sirotenko
 */
public class RouterException extends Exception {
	RouterException(String msg) {
		super(msg);
	}
	
	RouterException(String msg, Exception e) {
		super(msg, e);
	}
}
