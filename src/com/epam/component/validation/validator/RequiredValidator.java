package com.epam.component.validation.validator;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

/**
 * Require validator
 * Field have to contains something
 * 
 * @author Yuriy Sirotenko
 */
public class RequiredValidator extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		
		Lang lang = null;
		try {
			lang = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
		} catch (ServiceLocatorException e) {
			throw new ValidationException("Service could not be found", e);
		}
		
		if (value.isEmpty()) {
			throw new ValidationException(lang.getValue("required_hint"));
		}
		
		return true;
	}

}
