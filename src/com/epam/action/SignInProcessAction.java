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
import com.epam.service.exception.UserServiceException;

/**
 * Process of sign in - action
 * 
 * @author Yuriy Sirotenko
 */
public class SignInProcessAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlashMessage flashMessage = (FlashMessage) ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		UserService service = new UserService();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ValidatorFabric validatorRequired = ValidatorFabric.initial(ValidatorEnum.REQUIRED);
				
		try {
			validatorRequired.execute(username);
			validatorRequired.execute(password);
		} catch (ValidationException e) {
			flashMessage.setMsg(e.getMessage());
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		Lang lang = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		UserEntity entity = null;
		
		try  {
			entity = service.findByUsername(username);
			service.isPasswordValid(entity, password);
			service.login(entity);
		} catch (UserServiceException e) {
			flashMessage.setMsg(lang.getValue("auth_problem"));
			response.sendRedirect("/BookShop/sign-in.html");
			return;
		}
		
		response.sendRedirect("/BookShop");
	}
}
