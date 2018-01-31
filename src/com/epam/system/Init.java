package com.epam.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.ajax_response.AjaxResponse;
import com.epam.component.flash.FlashMessage;
import com.epam.component.lang.Lang;
import com.epam.component.pagination.Pagination;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.UserEntity;
import com.epam.enum_list.RoleEnum;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

/**
 * The important class of the application. This class
 * describe application init logic and have to be used in
 * entry script (DispatcherServlet - Front Controller)
 * 
 * @author Yuriy Sirotenko
 */
public class Init {
	private Init() {}
	
	public static void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceLocatorException {
		try {
			Lang langInstance = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
			request.setAttribute("lang", langInstance);
		} catch (ServiceLocatorException e) {
			String lang = "en";
			
			Locale locale = new Locale(lang);
			Lang langInstance = new Lang(locale);
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.LANG, langInstance);
			// put lang to request on global level
			request.setAttribute("lang", langInstance);
		}
		
		try {
			UserService us = new UserService();
			UserEntity user = us.currentUser();
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.USER, user);
		} catch (UserServiceException e) {
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.USER, null);
		}
		
		AjaxResponse ar = new AjaxResponse().setResponse(response);
		
		try {
			ServiceLocator.getInstance().getService(ServiceLocatorEnum.AJAX_RESPONSE);
		} catch (ServiceLocatorException e) {
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.AJAX_RESPONSE, ar);
		}
		
		try {
			ServiceLocator.getInstance().getService(ServiceLocatorEnum.FLASH_MESSAGE);
		} catch (ServiceLocatorException e) {
			FlashMessage fm = new FlashMessage();
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.FLASH_MESSAGE, fm);
		}
		
		try {
			ServiceLocator.getInstance().getService(ServiceLocatorEnum.PAGINATION);
		} catch (ServiceLocatorException e) {
			Pagination pagination = new Pagination();
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.PAGINATION, pagination);
		}
		
		request.setAttribute("adminRoleId", RoleEnum.ADMIN.getValue());
	}
}
