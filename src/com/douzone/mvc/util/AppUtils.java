package com.douzone.mvc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.exception.ParameterNotFoundException;

import jdk.jfr.events.ThrowablesEvent;

public class AppUtils {

	public AppUtils() {
		super();
		// TODO Auto-generated constructor stub
	}


	public static boolean isNullParameter(HttpServletRequest request, String name) {

		if (request.getParameter(name) == null || request.getParameter(name).trim().length() <= 0) {

			return true;

		}

		return false;

	}

	public static long getLongParameter(HttpServletRequest request, String name) {

		if (!isNullParameter(request, name)) {

			return Long.parseLong(request.getParameter(name));

		}

		return -99999999L;

	}

	public static Object getSessionValue(HttpServletRequest request, String key) throws ParameterNotFoundException {

		HttpSession session = request.getSession();
		
		if (session != null && session.getAttribute(key) != null) {

			return session.getAttribute(key);

		}
	
		throw new ParameterNotFoundException(" : 세션이 null이거나 파라미터가 존재하지 않음");
	
	}

	public static boolean isNullSession(HttpServletRequest request, String key) {

		HttpSession session = request.getSession();
		if (session != null && session.getAttribute(key) != null) {

			return true;

		}
		
		return false;
	}

}
