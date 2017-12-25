package com.epam.component.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.component.validation.ValidatorFabric;
import com.epam.component.validation.exception.ValidationException;

public class UsernameValidator extends ValidatorFabric {

	public Boolean execute(String value) throws ValidationException {
		Pattern p = Pattern.compile("^[a-zA-Z1-9_]{4,14}");
		Matcher m = p.matcher(value);
		
		if (m.matches() != true) {
			throw new ValidationException("Minimum four characters, special character not permitted");
		}
		
		return true;
	}
	
}
