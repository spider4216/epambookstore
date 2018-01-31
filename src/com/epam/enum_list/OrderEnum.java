package com.epam.enum_list;

/**
 * Order statuses
 * 
 * @author Yuriy Sirotenko
 */
public enum OrderEnum {
	UNDER_CONSIDERATION(1),
	APPROVED(2);
	
	private Integer value;
	
	private OrderEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
