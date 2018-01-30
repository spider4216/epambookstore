package com.epam.component.ajax_response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Component for ajax response. It is put into ServiceLocator
 * in init script
 * 
 * IMPORTANT
 * When you get this component from service locator you have to
 * set new response as argument of setter to prevent issue to do with
 * null pointer on low layers
 * 
 * @author Yuriy Sirotenko
 */
public class AjaxResponse {
	private AjaxResponseStatus status;
	
	private String message;
		
	private HttpServletResponse response;

	public AjaxResponse setMessage(String message) {
		this.message = message;
		
		return this;
	}

	public AjaxResponse setResponse(HttpServletResponse response) {
		this.response = response;
		
		return this;
	}
	
	public void responseOk()  throws IOException {
		status = AjaxResponseStatus.STATUS_OK;
		response();
	}
	
	/**
	 * It is said that there are a lot of libraries to convert
	 * HashMap to JSON, but I cannot use any libs according to
	 * project criteria specified in document which I get when
	 * start JavaLab course. That's because I have to hardcode json
	 * as string
	 */
	private void response() throws IOException {
		String resStatus;
		
		switch (status) {
			case STATUS_OK :
				resStatus = "ok";
				break;
			case STATUS_ERROR :
				resStatus = "err";
				break;
			default:
				resStatus = "err";
		}
		
		// the reason was wrote in comment above
		String json = "{\"status\":\""+ resStatus + "\",\"message\":\"" + message + "\"}";
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
