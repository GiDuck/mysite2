package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.UserVo;

public class DeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub

		long boNum = Long.parseLong(request.getParameter("boNum"));
		long writerNum = Long.parseLong(request.getParameter("boWriter"));
		
		HttpSession session = request.getSession();
		UserVo user = (UserVo)session.getAttribute("authuser");
		
		if(user!=null && user.getNo() == writerNum) {
			
			BoardDao dao = new BoardDao();
			boolean isSuccess = dao.delete(boNum);
			if(isSuccess) {
				WebUtils.redirect(request, response, request.getContextPath() + "/board?status=delok"); 
				return;
			}
		}
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?status=delfail");

		
	}

	
	
}
