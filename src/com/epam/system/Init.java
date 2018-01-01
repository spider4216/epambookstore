package com.epam.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.component.lang.Lang;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;
import com.epam.component.service_locator.ServiceLocatorException;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.exception.UserServiceException;

public class Init {
	public static void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceLocatorException {
		HttpSession session = (HttpSession)ServiceLocator
				.getInstance()
				.getService(ServiceLocatorEnum.SESSION);

		
		try {
			Lang langInstance = (Lang)ServiceLocator.getInstance().getService(ServiceLocatorEnum.LANG);
			request.setAttribute("lang", langInstance);
		} catch (ServiceLocatorException e) {
			// TODO from config lang by default
			String lang = "en";
			
			Locale locale = new Locale(lang);
			Lang langInstance = new Lang(locale);
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.LANG, langInstance);
			// put lang to request on global level
			request.setAttribute("lang", langInstance);
		}
		
		try {
			UserService us = new UserService();
			User user = us.currentUser();
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.USER, user);
		} catch (UserServiceException e) {
			ServiceLocator.getInstance().setService(ServiceLocatorEnum.USER, null);
		}
	}
}
