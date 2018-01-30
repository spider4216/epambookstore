package com.epam.component.pagination;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.epam.entity.BookEntity;
import com.epam.service.BookService;
import com.epam.service.exception.BookServiceException;

/**
 * Pagination component
 * 
 * @author Yuriy Sirotenko
 */
public class Pagination {
	public final Integer COUNT_ITEM = 10;
	
	private HttpServletRequest request;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * Get current page number
	 */
	public Integer getCurrentPageNumber() {
		String pageNum = request.getParameter("page");
		
		return pageNum == null ? 1 : Integer.parseInt(pageNum);
	}
	
	/**
	 * Get next page number
	 */
	public Integer getNextPageNumber() {
		return getCurrentPageNumber() + 1;
	}
	
	/**
	 * Get previous page number
	 */
	public Integer getPreviousPageNumber() {
		return getCurrentPageNumber() == 1 ? 1 : getCurrentPageNumber() - 1; 
	}
	
	/**
	 * Is previous page disabled
	 */
	public Boolean isPreviousDisabled() {
		return getCurrentPageNumber() == 1 ? true : false;
	}
	
	/**
	 * Is next page disabled
	 */
	public Boolean isNextDisabled() {
		try {
			BookService bookeService = new BookService();
			ArrayList<BookEntity> collection = bookeService.findNextPageBooks(getCurrentStartOffset() + COUNT_ITEM, COUNT_ITEM);
			
			return collection.isEmpty();
		} catch (BookServiceException e) {
			return true;
		}
	}
	
	/**
	 * Is next page disabled
	 */
	public Boolean isNextForCategoryDisabled(Integer categoryId) {
		try {
			BookService bookeService = new BookService();
			ArrayList<BookEntity> collection = bookeService.findNextPageCategoryBooks(categoryId, getCurrentStartOffset() + COUNT_ITEM, COUNT_ITEM);
			
			return collection.isEmpty();
		} catch (BookServiceException e) {
			return true;
		}
	}
	
	/**
	 * Get current start offset
	 */
	public Integer getCurrentStartOffset() {
		Integer currentPageNum = getCurrentPageNumber();

		if (currentPageNum == 1) {
			return 0;
		}

		return (currentPageNum * COUNT_ITEM) - COUNT_ITEM;
	}
}
