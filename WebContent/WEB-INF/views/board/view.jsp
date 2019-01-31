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
.replySendContent {
	width: 70%;
	min-height: 60px;
	height: 80px;
	padding: 10px;
	border: 3px solid gray;
	vertical-align: bottom;
}

.replyForm {
	position: inline;
}

.replyBtn {
	width: 20%;
	height: 100px;
	min-height: 60px;
	background-color: gray;
	border: 1px solid black;
	margin-left: 10px;
	vertical-align: bottom;
}

.replyBtn:hover {
	background-color: silver;
}

.replyImg {
	border-radius: 50%;
	width : 60px;
	height : 60px;
}

.replyCard{

border: 2px solid lime;
border-radius : 10px;
padding : 8px;
margin : 20px;
height : 100px;
min-height : 80px;


}

</style>

</head>

<body>
	<div id="container">

		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.bo_title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${vo.bo_content}</div>
						</td>
					</tr>
				</table>

				<div id="reply-form">

					<div style="width: 100%">

		<c:choose>
			<c:when test="${replyStatus eq 'modReply'}">
						
				<form action="${pageContext.request.contextPath}/reply" method="post" class="replyForm">
								<input type="hidden" name="a" value="modify">
								<input type="hidden" name="re_boRef" value="${vo.bo_num}">
								<input type="hidden" name="bo_writer" value="${vo.bo_writer.no}">
								<input type="hidden" name="rp_num" value="${singleReplyVo.rp_num}">
								
								
								<textarea name="reply_content" rows="5" placeholder="댓글 입력.."
									class="replySendContent"> ${singleReplyVo.rp_content} </textarea>
								<button type="submit" class="replyBtn">수정</button>
							</form>		
						
						
	</c:when>

	<c:otherwise>
	
		<form action="${pageContext.request.contextPath}/reply" method="post" class="replyForm">
								<input type="hidden" name="a" value="insert">
								<input type="hidden" name="re_boRef" value="${vo.bo_num}">
								<input type="hidden" name="bo_writer" value="${vo.bo_writer.no}">
								
								
								<textarea name="reply_content" rows="5" placeholder="댓글 입력.."
									class="replySendContent"></textarea>
								<button type="submit" class="replyBtn">작성</button>
							</form>
					
							</c:otherwise>
						
						</c:choose>
						</div>

							<c:forEach var="reply" items="${replies}">
						
							<div class="replyCard">
								
								<div class="replyTitle">${reply.rp_writer.name}</div>
								<div class="replyContent">${reply.rp_content}</div>
								<div class="replyDate">${reply.rp_timestamp}</div>
								<div class="replyBottom">
								
								<c:if test="${reply.rp_writer.no eq sessionScope.authuser.no}">
									<a href="${pageContext.request.contextPath}/board?a=selectOneForm&boNum=${vo.bo_num}&writerNum=${vo.bo_writer.no}&reNum=${reply.rp_num}&replyStatus=modReply">수정</a>
									<a href="${pageContext.request.contextPath}/reply?a=delete&boNum=${vo.bo_num}&writerNum=${vo.bo_writer.no}&rpNum=${reply.rp_num}">삭제</a>
								</c:if>
								</div>
								
							</div>
							
							</c:forEach>


				</div>




				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board">글목록</a>
					<c:choose>
						<c:when test="${vo.bo_ref eq 0 }">
							<a
								href="${pageContext.request.contextPath}/board?a=insertForm&refNum=${vo.bo_num}&orderNum=${vo.bo_order}">답글
								달기</a>
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/board?a=insertForm&refNum=${vo.bo_ref}&orderNum=${vo.bo_order}">답글
								달기</a>
						</c:otherwise>

					</c:choose>

					<c:if test="${sessionScope.authuser.no eq vo.bo_writer.no}">
						<a
							href="${pageContext.request.contextPath}/board?a=modifyForm&boNum=${vo.bo_num}&writerNo=${vo.bo_writer.no}">글수정</a>

					</c:if>
				</div>
			</div>
		</div>
		
		<script>
		
		console.log('${status}');
		
		</script>
		
		<c:choose>
		
			<c:when test="${param.status eq 'rpInsertSs'}"><script>alert('댓글 등록 성공하였습니다.');</script></c:when>
			<c:when test="${param.status eq 'rpInsertFl'}"><script>alert('댓글 등록에 실패하였습니다.');</script></c:when>
			<c:when test="${param.status eq 'rpNologin'}"><script>alert('로그인 후에 사용 가능한 서비스 입니다. 먼저 로그인 해 주세요.');</script></c:when>
			<c:when test="${param.status eq 'rpModSuccess'}"><script>alert('댓글 수정 성공하였습니다.');</script></c:when>
			<c:when test="${param.status eq 'rpModFail'}"><script>alert('댓글 수정 실패하였습니다.');</script></c:when>
			<c:when test="${param.status eq 'rpDelFail'}"><script>alert('댓글 삭제 실패하였습니다.');</script></c:when>
			<c:when test="${param.status eq 'rpDelSuccess'}"><script>alert('댓글 삭제 성공하였습니다.');</script></c:when>
			
		</c:choose>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />

		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>