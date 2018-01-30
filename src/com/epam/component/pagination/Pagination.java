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
	
	private static final Integer FIRST_PAGE_INDEX = 1;
	
	private static final Integer START_OFFSET_INDEX = 0;
	
	private HttpServletRequest request;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * Get current page number
	 */
	public Integer getCurrentPageNumber() {
		String pageNum = request.getParameter("page");
		
		return pageNum == null ? FIRST_PAGE_INDEX : Integer.parseInt(pageNum);
	}
	
	/**
	 * Get next page number
	 */
	public Integer getNextPageNumber() {
		return getCurrentPageNumber() + FIRST_PAGE_INDEX;
	}
	
	/**
	 * Get previous page number
	 */
	public Integer getPreviousPageNumber() {
		return getCurrentPageNumber().equals(FIRST_PAGE_INDEX) ? FIRST_PAGE_INDEX : getCurrentPageNumber() - FIRST_PAGE_INDEX; 
	}
	
	/**
	 * Is previous page disabled
	 */
	public Boolean isPreviousDisabled() {
		return getCurrentPageNumber().equals(FIRST_PAGE_INDEX) ? true : false;
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

		if (currentPageNum == FIRST_PAGE_INDEX) {
			return START_OFFSET_INDEX;
		}

		return (currentPageNum * COUNT_ITEM) - COUNT_ITEM;
	}
}
