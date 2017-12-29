package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.flash.FlashMessage;
import com.epam.component.validation.ValidatorEnum;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

public class SignInProcessAction implements IAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlashMessage fm = FlashMessage.getInstance();
		UserService service = new UserService();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ValidatorFabric validatorRequired = ValidatorFabric.initial(ValidatorEnum.REQUIRED);
				
		try {
			validatorRequired.execute(username);
			validatorRequired.execute(password);
		} catch (ValidationException e) {
			fm.setMsg(e.getMessage());
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		User entity = null;
		
		try  {
			entity = service.findByUsername(username);
		} catch (UserServiceException e) {
			fm.setMsg("Incorrect username or password");
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		if (!service.isPasswordValid(entity, password)) {
			// TODO DRY
			fm.setMsg("Incorrect username or password");
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		// TODO DRY
		try {
			service.login(entity);
		} catch(UserServiceException e) {
			fm.setMsg("Incorrect username or password");
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		response.sendRedirect("/BookShop");
	}
}
