package com.epam.enum_list;

public enum RoleEnum {
	ADMIN(1),
	USER(2);
	
	private Integer value;
	
	private RoleEnum(Integer value) {
		this.value  = value;
	}

	public Integer getValue() {
		return value;
	}
}
