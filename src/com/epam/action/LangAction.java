package com.epam.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.lang.Lang;
import com.epam.component.lang.LangList;
import com.epam.component.service_locator.ServiceLocator;
import com.epam.component.service_locator.ServiceLocatorEnum;

/**
 * Action for change language
 * 
 * @author Yuriy Sirotenko
 */
public class LangAction implements IAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String frontLang = request.getParameter("lang");
		String resLang = null;
		
		switch (frontLang) {
			case LangList.LANG_EN :
				resLang = LangList.LANG_EN;
				break;
			case LangList.LANG_RU :
				resLang = LangList.LANG_RU;
				break;
			default:
				resLang = LangList.LANG_EN;
				break;
		}
		
		Locale locale = new Locale(resLang);
		Lang lang = new Lang(locale);
		
		ServiceLocator.getInstance().setService(ServiceLocatorEnum.LANG, lang);
		String url = request.getHeader("referer");
		
		response.sendRedirect(url);
	}
}
