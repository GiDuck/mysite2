package com.douzone.mysite.action.reply;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.ReplyDao;
import com.douzone.mysite.vo.ReplyVo;
import com.douzone.mysite.vo.UserVo;

public class BoardReplyModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		long rpNum = 0;
		
		if(request.getParameter("rp_num") != null && request.getParameter("rp_num").trim().length() > 0) {
			
			rpNum = Long.parseLong(request.getParameter("rp_num"));
			
		};
	
		
		String boNum = request.getParameter("re_boRef");
		String boWriterNum = request.getParameter("bo_writer");
		String content = request.getParameter("reply_content");
		
		
	
		HttpSession session = request.getSession();
		UserVo user = (UserVo)session.getAttribute("authuser");
		
		if(user == null || user.getNo() == 0 || Long.parseLong(boWriterNum) != user.getNo()) {
			
			WebUtils.redirect(request, response, request.getContextPath() + "/board?a=selectOneForm&boNum="+ boNum +"&writerNum=" + boWriterNum + "&status=rpNologin");
			return;
			
		}
		
		
		
		ReplyDao dao = new ReplyDao();
		ReplyVo vo = new ReplyVo();
		vo.setRp_num(rpNum);
		vo.setRp_content(content);
		boolean isSuccess = dao.update(vo);
		if(isSuccess) {
			WebUtils.redirect(request, response,  request.getContextPath() + "/board?a=selectOneForm&boNum="+ boNum +"&writerNum=" + boWriterNum + "&status=rpModSuccess");

		}
		else {
			WebUtils.redirect(request, response,  request.getContextPath() + "/board?a=selectOneForm&boNum="+ boNum +"&writerNum=" + boWriterNum + "&status=rpModFail");

		}
		
	}

	
	
	
}
