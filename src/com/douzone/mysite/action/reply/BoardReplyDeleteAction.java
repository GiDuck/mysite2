package com.douzone.mysite.action.reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.ReplyDao;

public class BoardReplyDeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String rp_num = request.getParameter("rpNum");
		String bo_num = request.getParameter("boNum");
		String writerNum = request.getParameter("writerNum");

		if( rp_num != null && rp_num.trim().length() > 0) {
		
			
			ReplyDao dao = new ReplyDao();
			boolean isSuccess = dao.delete(Long.parseLong(rp_num));
			
			String url = request.getContextPath() + "/board?a=selectOneForm&boNum="+ bo_num +"&writerNum="+writerNum;
			
			if(isSuccess) {
				
				url += "&status=rpDelSuccess";
				
			}else {
			
				url += "&status=rpDelFail";
				
			}
			
			WebUtils.redirect(request, response, url);
			
			
			
		}
		
		
		
	}
	
	

}
