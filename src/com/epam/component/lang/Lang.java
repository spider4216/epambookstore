package com.epam.component.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * Multilingual component. It is put into Service Locator in init script
 * If you want to use this component, get it from service locator
 * Don't create new instance, because it has't any sense and reason to do it.
 * Use Service Locator
 * 
 * @author Yuriy Sirotenko
 */
public class Lang {
	private Properties property;
	
	private Locale locale;
	
	public Lang(Locale locale) throws IOException {
		this.locale = locale;
		String msgPath = "message/global_" + locale.getLanguage() + ".properties";
		property = new Properties();
		InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(msgPath);
		
		property.load(resourceStream);
	}
	
	public String getValue(String key) {
		return property.getProperty(key);
	}
	
	public String getLangAsString() {
		return locale.getLanguage();
	}
	
	public String getColumnSuffix() {
		return this.getLangAsString().equals(new Locale(LangEnum.LANG_EN.getValue()).getLanguage()) != true ? "_" + this.getLangAsString() : "";
	}
}
