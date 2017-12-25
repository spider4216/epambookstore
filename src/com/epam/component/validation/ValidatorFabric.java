package com.epam.component.validation;

import com.epam.component.validation.exception.ValidationException;
import com.epam.component.validation.validator.PasswordValidator;
import com.epam.component.validation.validator.UniqueUsername;
import com.epam.component.validation.validator.UsernameValidator;

public abstract class ValidatorFabric {
	public abstract Boolean execute(String value) throws ValidationException;
	
	public static ValidatorFabric initial(ValidatorEnum name) {
		ValidatorFabric validator = null;
		
		switch (name) {
			case PASSWORD :
				validator = new PasswordValidator();
				break;
			case USERNAME :
				validator = new UsernameValidator();
				break;
			case USERNAME_UNIQUE :
				validator = new UniqueUsername();
				break;
		}
		
		return validator;
	}
}
