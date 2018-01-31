package com.epam.component.lang;

/**
 * Lang List
 * 
 * @author Yuriy Sirotenko
 */
public enum LangEnum {
	LANG_RU("ru"),
	LANG_EN("en");
	
	private String value;
	
	private LangEnum(String lang) {
		value = lang;
	}

	public String getValue() {
		return value;
	}
}
