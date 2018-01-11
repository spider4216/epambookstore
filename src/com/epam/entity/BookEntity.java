package com.epam.entity;

/**
 * Book
 * 
 * @author Yuriy Sitotenko
 */
public class BookEntity {
	
	private Integer id;
	
	/**
	 * ISBN's book
	 */
	private String isbn;
	
	private String name;
	
	private String author;
	
	private String description;
	
	private Integer year;
	
	/**
	 * Page numbers
	 */
	private Integer page;
	
	private Integer category_id;
	
	private Double price;
	
	private String img_path;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer pages) {
		this.page = pages;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getCategoryId() {
		return category_id;
	}

	public void setCategoryId(Integer category_id) {
		this.category_id = category_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPath() {
		return img_path;
	}

	public void setImgPath(String img_path) {
		this.img_path = img_path;
	}
}
