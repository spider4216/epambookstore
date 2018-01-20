package com.epam.component.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

/**
 * Username validator
 * Minimum four characters, special character not permitted
 * 
 * @author Yuriy Sirotenko
 */
public class UsernameValidator extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		Lang lang = null;
		try {
			lang = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new ValidationException("Service could not be found", e);
		}
		
		Pattern p = Pattern.compile("^[a-zA-Z1-9_]{4,14}");
		Matcher m = p.matcher(value);
		
		if (m.matches() != true) {
			throw new ValidationException(lang.getValue("login_validator_hint"));
		}
		
		return true;
	}
	
}
