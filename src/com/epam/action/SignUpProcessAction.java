package com.epam.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;
import com.epam.service.UserService;

public class SignUpProcessAction implements IAction {

	public Object execute(HttpServletRequest request, HttpServletResponse response) throws DaoUserException {
		User user = new User();
		user.setUsername(request.getParameter("username"));
		// TODO HASH HERE
		user.setPassword(request.getParameter("password"));
		user.setFirstName(request.getParameter("first_name"));
		user.setLastName(request.getParameter("last_name"));
		// TODO replace it
		user.setGender(1);
		
		// TODO remove entity from constructor
		UserService userService = new UserService(user);
		Boolean res = userService.insert(user);
		
		if (res != true) {
			// TODO throw exception
		}
		
		// TODO I Want Redirect here
		
		return true;
	}

}
