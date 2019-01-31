package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.AppUtils;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.exception.ParameterNotFoundException;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		long boNum = AppUtils.getLongParameter(request, "boNum");
		long writerNo = AppUtils.getLongParameter(request, "writerNo");

		UserVo authVo  = new UserVo();
		try {
			authVo = (UserVo) AppUtils.getSessionValue(request, "authuser");
		} catch (ParameterNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (authVo.getNo() == writerNo) {
			BoardDao dao = new BoardDao();
			BoardVo vo = dao.getSelectedBoard(boNum);
			request.setAttribute("vo", vo);
			WebUtils.forward(request, response, "/WEB-INF/views/board/modify.jsp");
			return;
		}

	}

}
