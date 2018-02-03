package com.epam.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.validation.ValidatorEnum;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;
import com.epam.entity.UserEntity;
import com.epam.service.UserService;

/**
 * Sign up process - action
 * 
 * @author Yuriy Sirotenko
 */
public class SignUpProcessAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlashMessage flashMessage = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		ValidatorFabric validatorPassword = ValidatorFabric.initial(ValidatorEnum.PASSWORD);
		ValidatorFabric validatorUsername = ValidatorFabric.initial(ValidatorEnum.USERNAME);
		ValidatorFabric validatorUsernameUnique = ValidatorFabric.initial(ValidatorEnum.USERNAME_UNIQUE);

		try {
			validatorPassword.execute(request.getParameter("password"));
			validatorUsername.execute(request.getParameter("username"));
			validatorUsernameUnique.execute(request.getParameter("username"));
		} catch (ValidationException e) {
			flashMessage.setMsg(e.getMessage());
			response.sendRedirect("/sign-up.html");
			return;
		}

		UserService userService = new UserService();
		UserEntity user = new UserEntity();
		user.setUsername(request.getParameter("username"));
		user.setPassword(userService.passwordHash(request.getParameter("password")));
		user.setFirstName(request.getParameter("first_name"));
		user.setLastName(request.getParameter("last_name"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));

		userService.insert(user);

		flashMessage.setMsg(lang.getValue("successfully_registered_message"));

		response.sendRedirect("/sign-in.html");
	}
}
