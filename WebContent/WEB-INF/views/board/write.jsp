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
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />

		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.request.contextPath}/board">
					<input type="hidden" name="a" value="write">
					<input type="hidden" name="refNum" value="${param.refNum}">
					<input type="hidden" name="orderNum" value="${param.orderNum}">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td><textarea id="content" name="content"></textarea></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />

		<c:import url="/WEB-INF/views/includes/footer.jsp" />

	</div>
</body>
</html>