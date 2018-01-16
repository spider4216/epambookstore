package com.epam.entity;

import com.epam.constant.RoleConstant;

public class UserEntity {
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String first_name;
	
	private String last_name;
	
	private Integer gender;
	
	private String session_id;
	
	private Integer role_id = RoleConstant.USER;
	
	/**
	 * Virtual field like a relation one to one
	 */
	private RoleEntity role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getSessionId() {
		return session_id;
	}

	public void setSessionId(String session_id) {
		this.session_id = session_id;
	}

	public Integer getRoleId() {
		return role_id;
	}

	public void setRoleId(Integer role_id) {
		this.role_id = role_id;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}
}
