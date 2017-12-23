package com.epam.entity;

import java.io.Serializable;

/**
 * Abstract class of product
 * 
 * @author Yuriy Sirotenko
 */
abstract public class Product {
	
	/**
	 * Product ID
	 */
	private Integer id;
	
	/**
	 * Product name
	 */
	private String name;
	
	/**
	 * Product's price
	 */
	private Double price;
	
	/**
	 * Author
	 */
	private String author;
	
	/**
	 * Product's description
	 */
	private String description;
	
	/**
	 * Category's product
	 */
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
