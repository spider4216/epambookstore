package com.epam.component.validation;

import com.epam.component.validation.exception.ValidationException;
import com.epam.component.validation.validator.PasswordValidator;

public abstract class ValidatorFabric {
	public abstract Boolean execute(String value) throws ValidationException;
	
	public static ValidatorFabric initial(ValidatorEnum name) {
		ValidatorFabric validator = null;
		
		switch (name) {
			case PASSWORD :
				validator = new PasswordValidator();
				break;
		}
		
		return validator;
	}
}
