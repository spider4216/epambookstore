package com.epam.component.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

public class PasswordValidator extends ValidatorFabric {
	public Boolean execute(String value) throws ValidationException {
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}");
		Matcher m = p.matcher(value);
		
		if (m.matches() != true) {
			throw new ValidationException("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
		}
		
		return true;
	}
}
