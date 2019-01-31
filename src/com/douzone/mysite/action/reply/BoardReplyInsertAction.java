package com.douzone.mysite.action.reply;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.ReplyDao;
import com.douzone.mysite.vo.ReplyVo;
import com.douzone.mysite.vo.UserVo;

public class BoardReplyInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		long bo_ref = 0;
		long re_ref = 0;
		long bo_writer = 0;
		String reply_content = "";

		HttpSession session = request.getSession();
		UserVo user = (UserVo) session.getAttribute("authuser");

		if (request.getParameter("re_boRef") != null && request.getParameter("re_boRef").trim().length() > 0) {

			bo_ref = Long.parseLong(request.getParameter("re_boRef"));

		} else {
			WebUtils.redirect(request, response, request.getContextPath() + "/board?a=selectOneForm&boNum=" + bo_ref
					+ "&writerNum=" + bo_writer + "&status=rpInsertFl");

			return;

		}

		if (user == null || user.getNo() == 0) {
			WebUtils.redirect(request, response, request.getContextPath() + "/board?a=selectOneForm&boNum=" + bo_ref
					+ "&writerNum=" + bo_writer + "&status=rpNologin");
			return;

		}

		if (request.getParameter("re_ref") != null && request.getParameter("re_ref").trim().length() > 0) {

			re_ref = Long.parseLong(request.getParameter("re_ref"));

		}

		if (request.getParameter("reply_content") != null) {

			reply_content = request.getParameter("reply_content");

		}

		ReplyVo vo = new ReplyVo();
		vo.setRp_num(0);
		vo.setRp_refBo(bo_ref);
		vo.setRp_ref(re_ref);
		vo.setRp_writer(user);
		vo.setRp_timestamp(new Timestamp(new Date().getTime()));
		vo.setRp_content(reply_content);

		ReplyDao dao = new ReplyDao();
		System.out.println(vo.toString());
		boolean isSuccess = dao.insert(vo);

		if (isSuccess) {

			WebUtils.redirect(request, response, request.getContextPath() + "/board?a=selectOneForm&boNum=" + bo_ref
					+ "&writerNum=" + bo_writer + "&status=rpInsertSs");

		} else {

			WebUtils.redirect(request, response, request.getContextPath() + "/board?a=selectOneForm&boNum=" + bo_ref
					+ "&writerNum=" + bo_writer + "&status=rpInsertFl");

		}

	}

}
