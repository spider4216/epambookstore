package com.epam.entity;

public class OrderToProductEntity {
	private Integer id;
	
	private Integer order_id;
	
	private Integer book_id;
	
	private Integer count;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return order_id;
	}

	public void setOrderId(Integer order_id) {
		this.order_id = order_id;
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
}
