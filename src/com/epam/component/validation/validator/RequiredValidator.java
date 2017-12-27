package com.epam.component.validation.validator;

import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

public class RequiredValidator extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		if (value.isEmpty()) {
			throw new ValidationException("Field cannot be empty");
		}
		
		return true;
	}

}
