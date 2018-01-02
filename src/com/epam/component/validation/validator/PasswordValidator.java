package com.epam.component.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

public class PasswordValidator extends ValidatorFabric {
	public Boolean execute(String value) throws ValidationException {
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}");
		Matcher m = p.matcher(value);
		
		Lang lang = null;
		try {
			lang = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new ValidationException("Service could not be found", e);
		}
		
		if (m.matches() != true) {
			throw new ValidationException(lang.getValue("password_validator_hint"));
		}
		
		return true;
	}
}
