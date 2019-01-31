package com.douzone.mysite.action.board;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
	

		UserVo sessionUser = new UserVo();;
		try {
			sessionUser = (UserVo)AppUtils.getSessionValue(request, "authuser");
		} catch (ParameterNotFoundException e) {
			e.printStackTrace();
		}
		
		if(sessionUser == null) {
			
			WebUtils.redirect(request, response, request.getContextPath() + "/board");
			return;
		}
		
		BoardVo vo = new BoardVo();
		String refNum = request.getParameter("refNum");
		String orderNum = request.getParameter("orderNum");

		
		vo.setBo_title(request.getParameter("title"));
		vo.setBo_num(0);
		vo.setBo_writer(sessionUser);
		vo.setBo_timestamp(new Timestamp(new Date().getTime()));
		vo.setBo_count(0);

		if(!AppUtils.isNullParameter(request, "refNum")
				&& !AppUtils.isNullParameter(request, "orderNum")) {
			
			vo.setBo_ref(Long.parseLong(refNum));
			vo.setBo_order(Long.parseLong(orderNum));

		}

		vo.setBo_content(request.getParameter("content"));

		BoardDao dao = new BoardDao();
		boolean isSuccess = dao.insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board");
			
		
	}

	
	
	
	
	
}
