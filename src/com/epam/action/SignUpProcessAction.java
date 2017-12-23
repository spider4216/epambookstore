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
		User user = new User();
		user.setUsername(request.getParameter("username"));
		// TODO HASH HERE
		// TODO move into class helper
		// TODO validator
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] password = md.digest(request.getParameter("password").getBytes());
		BigInteger bigInt = new BigInteger(1, password);
		String hashPass = bigInt.toString(16);
		user.setPassword(hashPass);
		user.setFirstName(request.getParameter("first_name"));
		user.setLastName(request.getParameter("last_name"));
		// TODO replace it
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		
		// TODO remove entity from constructor
		UserService userService = new UserService(user);
		Boolean res = userService.insert(user);
		
		if (res != true) {
			throw new Exception("Cannot insert user");
		}
		
		response.sendRedirect("/BookShop/sign-in.html");
	}

}
