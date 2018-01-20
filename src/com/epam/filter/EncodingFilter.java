package com.epam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter for encoding
 * 
 * @author Yuriy Sirotenko
 */
public class EncodingFilter implements Filter {

	private String encoding;
	
	public void init(FilterConfig arg0) throws ServletException {
		encoding = "utf-8";
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {
		response.setCharacterEncoding(encoding);
		
		next.doFilter(request, response);
	}

	public void destroy() {
		encoding = null;
	}
}
