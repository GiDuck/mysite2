package com.douzone.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

public class ModifyAction implements Action {

	private UserDao dao;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVo authUser = null;
		HttpSession session = request.getSession();

		if (session != null) {
			authUser = (UserVo) session.getAttribute("authuser");
		}
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		UserVo userVo = new UserVo();
		userVo.setNo(authUser.getNo());
		userVo.setName((String) request.getParameter("name"));
		userVo.setEmail((String) request.getParameter("email"));
		userVo.setPassword((String) request.getParameter("password"));
		userVo.setGender((String) request.getParameter("gender"));
		userVo.setJoinDate((String) request.getParameter("joinDate"));
		System.out.println(userVo.toString());

		dao = new UserDao();
		boolean flag = dao.update(userVo);

		if (flag) {

			session.setAttribute("authuser", userVo);
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=modifyform");

		} else {

			WebUtils.redirect(request, response, request.getContextPath());

		}

	}

}
