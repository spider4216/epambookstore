package com.epam.component.route;

public class RouterException extends Exception {
	public RouterException(String msg) {
		super(msg);
	}
	
	public RouterException(String msg, Exception e) {
		super(msg, e);
	}
}
