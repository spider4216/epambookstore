package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {
	abstract public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
