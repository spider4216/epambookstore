package com.epam.component.pagination;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.epam.entity.BookEntity;
import com.epam.service.BookService;
import com.epam.service.exception.BookServiceException;

public class Pagination {
	public final Integer COUNT_ITEM = 10;
	
	private HttpServletRequest request;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Integer getCurrentPageNumber() {
		String pageNum = request.getParameter("page");
		
		if (pageNum == null) {
			return 1;
		}
		
		return Integer.parseInt(pageNum);
	}
	
	public Integer getNextPageNumber() {
		return getCurrentPageNumber() + 1;
	}
	
	public Integer getPreviousPageNumber() {
		if (getCurrentPageNumber() == 1) {
			return 1;
		}

		return getCurrentPageNumber() - 1;
	}
	
	public Boolean isPreviousDisabled() {
		return getCurrentPageNumber() == 1 ? true : false;
	}
	
	public Boolean isNextDisabled() {
		try {
			BookService bookeService = new BookService();
			ArrayList<BookEntity> collection = bookeService.findNextPageBooks();
			
			if (collection.isEmpty()) {
				return true;
			}
			
			return false;
		} catch (BookServiceException e) {
			return true;
		}
	}
	
	public Integer getStartOffset() {
		Integer currentPageNum = getCurrentPageNumber();
		
		if (currentPageNum == 1) {
			return 0;
		}
		
		return (currentPageNum * COUNT_ITEM) - COUNT_ITEM;
	}
}
