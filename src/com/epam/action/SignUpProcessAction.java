package com.epam.action;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.component.flash.FlashMessage;
import com.epam.component.validation.ValidatorEnum;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;
import com.epam.entity.User;
import com.epam.service.UserService;

public class SignUpProcessAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlashMessage fm = FlashMessage.getInstance();
		ValidatorFabric validatorPassword = ValidatorFabric.initial(ValidatorEnum.PASSWORD);
		ValidatorFabric validatorUsername = ValidatorFabric.initial(ValidatorEnum.USERNAME);
		// TODO unique login validation
		
		try {
			validatorPassword.execute(request.getParameter("password"));
			validatorUsername.execute(request.getParameter("username"));
		} catch (ValidationException e) {
			fm.setMsg(e.getMessage());
			response.sendRedirect("/BookShop/sign-up.html");
		}
		
		
		UserService userService = new UserService();
		User user = new User();
		user.setUsername(request.getParameter("username"));
		// TODO DB unique password
		// TODO not null in database
		user.setPassword(userService.passwordHash(request.getParameter("password")));
		user.setFirstName(request.getParameter("first_name"));
		user.setLastName(request.getParameter("last_name"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		
		Boolean res = userService.insert(user);
		
		if (res != true) {
			throw new Exception("Cannot insert user");
		}
		
		fm.setMsg("You were successfully registered!");
		
		response.sendRedirect("/BookShop/sign-in.html");
	}

}
