package com.epam.entity;

public class BasketEntity {
	private Integer id;
	
	private Integer user_id;
	
	private Integer book_id;
	
	private Integer count;
	
	private Integer is_history = 0;
	
	/**
	 * Virtual field like a relation one to one
	 */
	private Book book;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return user_id;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getBookId() {
		return book_id;
	}

	public void setBookId(Integer book_id) {
		this.book_id = book_id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getIsHistory() {
		return is_history;
	}

	public void setIsHistory(Integer is_history) {
		this.is_history = is_history;
	}
}
