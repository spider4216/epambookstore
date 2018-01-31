package com.epam.component.lang;

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
