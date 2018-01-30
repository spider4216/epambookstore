package com.epam.component.validation;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.validation.exception.ValidationException;
import com.epam.component.validation.exception.ValidatorNotFound;
import com.epam.component.validation.validator.PasswordValidator;
import com.epam.component.validation.validator.RequiredValidator;
import com.epam.component.validation.validator.UniqueUsernameValidator;
import com.epam.component.validation.validator.UsernameValidator;

/**
 * Validator fabric
 * 
 * @author Yuriy Sirotenko
 */
public abstract class ValidatorFabric {
	public abstract Boolean execute(String value) throws ValidationException;
	
	public static ValidatorFabric initial(ValidatorEnum name) throws ValidatorNotFound, ServiceLocatorException {
		Lang lang = (Lang) ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		ValidatorFabric validator = null;
		
		switch (name) {
			case PASSWORD :
				validator = new PasswordValidator();
				break;
			case USERNAME :
				validator = new UsernameValidator();
				break;
			case USERNAME_UNIQUE :
				validator = new UniqueUsernameValidator();
				break;
			case REQUIRED :
				validator = new RequiredValidator();
				break;
			default:
				throw new ValidatorNotFound(lang.getValue("validator_not_found"));
		}
		
		return validator;
	}
}
