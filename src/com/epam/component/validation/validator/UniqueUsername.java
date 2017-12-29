package com.epam.component.validation.validator;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

// TODO rename validator. It should be something like "UniqueUsernameValidator"
public class UniqueUsername extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		UserService service = null;
		
		try {
			service = new UserService();
		} catch (UserServiceException e) {
			throw new ValidationException("Username validator problem");
		}

		if (service.isUserExist(value)) {
			throw new ValidationException("Username has already exist");
		}
		
		return true;
	}

}
