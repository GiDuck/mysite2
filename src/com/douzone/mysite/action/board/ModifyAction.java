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

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		long boNum = AppUtils.getLongParameter(request, "boNum");
		long writerNum = AppUtils.getLongParameter(request, "writerNum");

		String title = request.getParameter("title");
		String content = request.getParameter("content");

		UserVo authVo = new UserVo();
		try {
			authVo = (UserVo) AppUtils.getSessionValue(request, "authuser");
		} catch (ParameterNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (authVo.getNo() == writerNum) {

			BoardVo vo = new BoardVo();
			vo.setBo_num(boNum);
			vo.setBo_title(title);
			vo.setBo_content(content);
			System.out.println(vo.toString());
			BoardDao dao = new BoardDao();
			boolean isSuccess = dao.update(vo);

			if (isSuccess) {

				WebUtils.redirect(request, response,
						request.getContextPath() + "/board?a=selectOneForm&boNum=" + boNum);
				return;

			}
		}
	}

}
