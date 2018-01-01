package com.epam.component.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lang {
	private ResourceBundle bundle;
	
	public Lang(Locale locale) {
		bundle = ResourceBundle.getBundle("com.epam.component.lang.message.global", locale);
	}
	
	public String getValue(String key) {
		return bundle.getString(key);
	}
}
