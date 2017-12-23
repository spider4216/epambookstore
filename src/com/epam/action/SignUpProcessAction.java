package com.epam.action;

import java.util.HashMap;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;
import com.epam.service.UserService;

public class SignUpProcessAction implements IAction {

	public Object run(HashMap<String, String> params) throws DaoUserException {
		System.out.println(params);
		// TODO Service sign up here
		User user = new User();
		user.setUsername(params.get("username"));
		// TODO HASH HERE
		user.setPassword(params.get("password"));
		user.setFirstName(params.get("first_name"));
		user.setLastName(params.get("last_name"));
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
