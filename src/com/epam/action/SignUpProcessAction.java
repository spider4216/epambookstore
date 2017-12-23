package com.epam.action;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;
import com.epam.service.UserService;

public class SignUpProcessAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserService userService = new UserService();
		User user = new User();
		user.setUsername(request.getParameter("username"));
		// TODO validator
		// TODO unique login
		// TODO complicated password and login
		// TODO DB unique password
		// TODO not null in database
		user.setPassword(userService.passwordHash(request.getParameter("password")));
		user.setFirstName(request.getParameter("first_name"));
		user.setLastName(request.getParameter("last_name"));
		// TODO replace it
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		
		Boolean res = userService.insert(user);
		
		if (res != true) {
			throw new Exception("Cannot insert user");
		}
		
		// TODO flash message here
		
		response.sendRedirect("/BookShop/sign-in.html");
	}

}
