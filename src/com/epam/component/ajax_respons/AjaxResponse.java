package com.epam.component.ajax_respons;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxResponse {
	private AjaxResponseStatus status;
	
	private String message;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	public AjaxResponseStatus getStatus() {
		return status;
	}

	public AjaxResponse setStatus(AjaxResponseStatus status) {
		this.status = status;
		
		return this;
	}

	public String getMessage() {
		return message;
	}

	public AjaxResponse setMessage(String message) {
		this.message = message;
		
		return this;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public AjaxResponse setRequest(HttpServletRequest request) {
		this.request = request;
		
		return this;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public AjaxResponse setResponse(HttpServletResponse response) {
		this.response = response;
		
		return this;
	}
	
	public void response() throws IOException {
//		HashMap<String, String> data = new HashMap<>();
		
//		data.put("status", status);
//		data.put("message", msg);
		
		String resStatus = "err";
		
		switch (status) {
			case STATUS_OK :
				resStatus = "ok";
				break;
			case STATUS_ERROR :
				resStatus = "err";
				break;
		}
		
		// TODO Maybe there is api for json?
		String json = "{\"status\":\""+ resStatus + "\",\"message\":\"" + message + "\"}";
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
