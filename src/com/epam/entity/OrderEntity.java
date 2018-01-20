package com.epam.entity;

import java.util.ArrayList;

import com.epam.constant.OrderStatus;

/**
 * Order entity
 * 
 * @author Yuriy Sirotenko
 */
public class OrderEntity {
	private Integer id;
	
	private String create_date;
	
	private Integer user_id;
	
	private Integer status = OrderStatus.UNDER_CONSIDERATION;
	
	/**
	 * Virtual field like a relation one to many
	 */
	private ArrayList<OrderToProductEntity> products;
	
	/**
	 * Virtual field like a relation one to one
	 */
	private UserEntity user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateDate() {
		return create_date;
	}

	public void setCreateDate(String create_date) {
		this.create_date = create_date;
	}

	public Integer getUserId() {
		return user_id;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ArrayList<OrderToProductEntity> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<OrderToProductEntity> products) {
		this.products = products;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
