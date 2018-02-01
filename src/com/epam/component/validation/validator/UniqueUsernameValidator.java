package com.epam.component.validation.validator;

import com.epam.component.dao.exception.DaoUserException;
import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

/**
 * Unique username validator
 * 
 * @author Yuriy Sirotenko
 */
public class UniqueUsernameValidator extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		Lang lang = null;
		try {
			lang = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new ValidationException("Service could not be found", e);
		}
		
		UserService service = null;
		
		try {
			service = new UserService();
		} catch (ServiceLocatorException | DaoUserException e) {
			throw new ValidationException(lang.getValue("username_validator_problem"));
		}

		if (service.isUserExist(value)) {
			throw new ValidationException(lang.getValue("username_unique_hint"));
		}
		
		return true;
	}

}
