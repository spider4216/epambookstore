package com.epam.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.component.lang.Lang;
import com.epam.component.lang.LangEnum;
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
		
		String en = LangEnum.LANG_EN.getValue();
		String ru = LangEnum.LANG_RU.getValue();
		
		if (!frontLang.equals(en) && !frontLang.equals(ru)) {
			frontLang = en;
		}
		
		Locale locale = new Locale(frontLang);
		Lang lang = new Lang(locale);
		
		ServiceLocator.getInstance().setService(ServiceLocatorEnum.LANG, lang);
		String url = request.getHeader("referer");
		
		response.sendRedirect(url);
	}
}
