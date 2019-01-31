<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">

	<style>
	
	/*
   게시판 리스트 페이징
*/
div.pager {
   width:100%;
   text-align:center;
}
div.pager  ul {
   height:20px;
   margin:10px auto;
}
div.pager  ul li          { color:#ddd; display:inline-block; margin:5px 0; width:20px ; font-weight:bold; }
div.pager  ul li.selected   { font-size:16px; text-decoration: underline; color:#f40808 }
div.pager  ul li a,
div.pager  ul li a:visited,
div.pager  ul li a:link,
div.pager  ul li a:active   { text-decoration: none; color:#555 }
div.pager  ul li a:hover   { text-decoration: none; color:#f00 }

	</style>
	
</head>
<body>
	<div id="container">

		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="get">
					<input type="hidden" name="pageNum" value="">
					<input type="hidden" name="limit" value="${limit}">
					<input type="text" name="keyword" value=""> 
					<input type="submit" value="찾기">
					
				</form>
				
					<form action="${pageContext.request.contextPath}/board" method="post">
					<input type="hidden" name="a" value=""> 
					<div style="width : 100%; text-align : right;">
					
						<select name="limitSelector">
						
						<c:choose>
						
							<c:when test="${limit eq 10}">
							
							<option value="10" selected>10개씩 보기</option>
							<option value="50">50개씩 보기</option>
							<option value="100">100개씩 보기</option>
							
							</c:when>
							<c:when test="${limit eq 50}">
							
							<option value="10">10개씩 보기</option>
							<option value="50" selected>50개씩 보기</option>
							<option value="100">100개씩 보기</option>
							
							</c:when>
							<c:when test="${limit eq 100}">
							
							<option value="10">10개씩 보기</option>
							<option value="20">50개씩 보기</option>
							<option value="100" selected>100개씩 보기</option>
							
							</c:when>
						
						</c:choose>

						
						</select>
					
					<input type="submit" value="변경">
					
					</div>
					</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach var="board" items="${boards}">
						<tr>
							<td>${board.bo_num}</td>
							<td><a href="${pageContext.request.contextPath}/board?a=selectOneForm&boNum=${board.bo_num}&writerNum=${board.bo_writer.no}">
							
							<c:forEach var="space" begin="1" end="${board.bo_order}">&nbsp</c:forEach>
							<c:if test="${board.bo_order > 0}"><img src="${pageContext.servletContext.contextPath}/assets/images/icon.png" width="20" height="20"></c:if>${board.bo_title} </a></td>
							<td>${board.bo_writer.name}</td>
							<td>${board.bo_count}</td>
							<td>${board.bo_timestamp}</td>
							<c:if test ="${sessionScope.authuser.no eq board.bo_writer.no}">
								<td><a href="${pageContext.request.contextPath}/board?a=delete&boNum=${board.bo_num}&boWriter=${board.bo_writer.no}" >삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>

				</table>
				<div class="pager">
				
				<ul>
				
				<li><a href="${pageContext.request.contextPath}/board?a=&pageNum=${backToBeforePage}&limit=${limit}"> < </a></li>
				
				<c:forEach var="i" begin = "${startPoint}" end = "${endPoint}">
			
					<c:choose>
					
						<c:when test="${pageNum eq i}">
					<li><a style="color:red;" href="${pageContext.request.contextPath}/board?a=&pageNum=${i}&limit=${limit}">${i}</a></li>
						
						</c:when>
						
						<c:otherwise>
				   <li><a href="${pageContext.request.contextPath}/board?a=&pageNum=${i}&limit=${limit}">${i}</a></li>
						
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<li><a href="${pageContext.request.contextPath}/board?a=&pageNum=${forwardToAfterPage}&limit=${limit}"> > </a></li>
				
				</ul>
				
				</div>
				
				<div class="bottom">
					<a
						href="${pageContext.servletContext.contextPath }/board?a=insertForm"
						id="new-book">글쓰기</a>
				</div>

			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>

	<c:choose>

		<c:when test="${ 'nologin' eq param.status }">
			<script>
				alert("로그인이 필요합니다. 로그인 후 이용해 주십시오.");
			</script>

		</c:when>


		<c:when test="${ 'delok' eq param.status }">
			<script>
				alert("게시글 삭제가 성공적으로 이루어졌습니다.");
			</script>

		</c:when>
		
		<c:when test="${ 'delfail' eq param.status }">
			<script>
				alert("게시글 삭제에 실패하였습니다.");
			</script>

		</c:when>
		


	</c:choose>


</body>
</html>



