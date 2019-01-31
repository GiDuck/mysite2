package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.AppUtils;
import com.douzone.mvc.util.WebUtils;

public class InsertFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		if (AppUtils.isNullSession(request, "authuser")) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");
			return;

		}
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=&status=nologin");

	}

}
