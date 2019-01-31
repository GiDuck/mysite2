package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.AppUtils;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.exception.ParameterNotFoundException;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.ReplyDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.ReplyVo;
import com.douzone.mysite.vo.UserVo;

public class SelectOneFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		BoardDao dao = new BoardDao();

		long boardNum = AppUtils.getLongParameter(request, "boNum");
		long writerNum = AppUtils.getLongParameter(request, "writerNo");
		String status = null;


		BoardVo vo = dao.getSelectedBoard(boardNum);
		request.setAttribute("vo", vo);
		

		UserVo authVo = new UserVo();
		try {
			authVo = (UserVo) AppUtils.getSessionValue(request, "authuser");
		} catch (ParameterNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (authVo == null || authVo.getNo() == 0 || authVo.getNo() != writerNum) {

			dao.counting(boardNum);
		}
		
		
		ReplyDao reDao = new ReplyDao();
		List<ReplyVo> replies = reDao.select(boardNum);
		request.setAttribute("replies", replies);
		
		
		
		
		if(request.getParameter("replyStatus")!=null && request.getParameter("replyStatus").trim().length() > 0) {
			
			String token = request.getParameter("replyStatus");
			if("modReply".equals(token)) {
				
				if(!AppUtils.isNullParameter(request, "reNum"))
				{
					long reNum = Long.parseLong(request.getParameter("reNum"));
					ReplyVo reply = reDao.selectSingleReply(reNum);
					request.setAttribute("replyStatus", "modReply");
					request.setAttribute("singleReplyVo", reply);
					
					
				}
					
				
			}
			
			
		}
		
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
		
		
	}


	
}
