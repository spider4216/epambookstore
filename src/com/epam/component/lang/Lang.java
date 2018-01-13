package com.epam.component.lang;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Multilingual component. It is put into Service Locator in init script
 * If you want to use this component, get it from service locator
 * Don't create new instance, because it has't any sense and reason to do it.
 * Use Service Locator
 * 
 * @author Yuriy Sirotenko
 */
public class Lang {
	private ResourceBundle bundle;
	
	public Lang(Locale locale) {
		bundle = ResourceBundle.getBundle("com.epam.component.lang.message.global", locale);
	}
	
	public String getValue(String key) {
		return bundle.getString(key);
	}
	
	public String getLangAsString() {
		Locale locale = bundle.getLocale();
		
		return locale.getLanguage();
	}
	
	public String getColumnSuffix() {
		return this.getLangAsString().equals(new Locale("en").getLanguage()) != true ? "_" + this.getLangAsString() : "";
	}
}
