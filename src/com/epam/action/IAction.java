package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action interface. Each action have to implement
 * this interface and realize execute method.
 * 
 * @author Yuriy Sirotenko
 */
public interface IAction {
	
	/**
	 * This method is used in entry point of application
	 */
	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
