package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.AppUtils;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;

public class ListBoardAction implements Action {

	
	public final long DEFAULT_LIMIT = 10L;
	public final long PAGE_COLUMN_SIZE = 10L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		long prefix = 0;
		long pageNum = 0;
		//총 게시글 개수
		long totalCount = 0;
		// 단위 선택, 10개, 30개, 50개
		long limit = 0; 
		
		long lastPageNum = 0;
		
		long startPoint = 0;
		long endPoint = 0;
	
		long backToBeforePage = 0;
		long forwardToAfterPage = 0;
		
		String keyword = request.getParameter("keyword");
		
		List<String> keywords = new ArrayList<>();
		
		if(keyword != null && keyword.trim().length() > 0) {
			
			String[] temp = keyword.split(" ");
			keywords = Arrays.asList(temp);
		}
		
		BoardDao dao = new BoardDao();
		totalCount = dao.getTotalBoardNum(keywords);

		System.out.println("total count.. " + totalCount);
		String[] limitSelector = (String[])request.getParameterValues("limitSelector");



		
		//parameter가 정상적으로 넘어오면 넘어온 값으로 초기화
		if(!AppUtils.isNullParameter(request, "pageNum") &&
				!AppUtils.isNullParameter(request, "limit"))  {
			
			pageNum = Long.parseLong(request.getParameter("pageNum"));
			limit = Long.parseLong(request.getParameter("limit"));
			
			lastPageNum = totalCount/limit + 1;
			prefix = (pageNum-1) * limit;
			
			
			
		//비정상적으로 넘어오면 기본값으로 초기화
		}else {
		
			pageNum = 1;
			lastPageNum = totalCount/DEFAULT_LIMIT + 1;
			prefix = 0;
			limit = DEFAULT_LIMIT;
			
				if(limitSelector != null) {
	
					long selectLimit = Long.parseLong(limitSelector[0]);
					lastPageNum = totalCount/selectLimit + 1;
					limit = selectLimit;
					
				}
			
			
		}
		
		//10으로 나누어 떨어지는 수인 경우에, 아직 다음 단위의 페이지로 넘기면 안되므로 -1을 해준다.
		// 예를 들어 10을 선택했는데 페이저가 20 ~ 30으로 넘어가면 안되므로 -1을 빼주는 것.
		
		if(pageNum % PAGE_COLUMN_SIZE == 0) {
			
			startPoint = (((pageNum -1) / PAGE_COLUMN_SIZE) * PAGE_COLUMN_SIZE ) + 1;
		}else {
			
			startPoint = ((pageNum / PAGE_COLUMN_SIZE) * PAGE_COLUMN_SIZE )+ 1;
			
		}
		
		endPoint = startPoint + PAGE_COLUMN_SIZE - 1;

		// 10 단위로 앞, 뒤 페이지로 이동한다. 
		forwardToAfterPage = startPoint + limit;
		backToBeforePage = startPoint - PAGE_COLUMN_SIZE;
		
		//만약 뒤로 간 페이지가 1보다 작으면 1로 세팅. (페이지는 1부터 시작한다)
		if(backToBeforePage < 1) {
			
			backToBeforePage = 1;
			
		}
		
		//만약 앞으로 간 페이지가 마지막 페이지보다 크면 마지막 페이지 번호로 세팅.
		if(forwardToAfterPage >= lastPageNum) {
			
			forwardToAfterPage = lastPageNum;
		}

				
		//만약 현재 페이지가 마지막 페이지와 10으로 나눈 자리수가 같을 경우, 끝나는 번호를 마지막 페이지 번호를 성정한다.
		// 예를 들어 마지막 페이지 번호가 48이면, 40~50까지 페이저를 세팅하지 않고 40~48까지만 페이저를 세팅한다.
		if( pageNum /PAGE_COLUMN_SIZE == lastPageNum / PAGE_COLUMN_SIZE ) {
			
			endPoint = lastPageNum;
		}
		
		List<BoardVo> boards = dao.getAllBoard(prefix, limit, keywords);


		
		request.setAttribute("boards", boards);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.setAttribute("limit", limit);
		request.setAttribute("startPoint", startPoint);
		request.setAttribute("endPoint", endPoint);
		request.setAttribute("backToBeforePage", backToBeforePage);
		request.setAttribute("forwardToAfterPage", forwardToAfterPage);
		request.setAttribute("keyword", keyword);


		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		
	}

	
	
	
}
