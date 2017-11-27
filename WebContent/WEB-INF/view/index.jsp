<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="header.jsp" %>
</head>
<body>
<div class="container">

	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<h1>게시판</h1>
			<table class="table table-striped">
				<tr>
					<th>글번호</th><th width="70%">제목</th><th>글쓴이</th><th>날짜</th>
				</tr>
			</table>
			<c:if test="${not empty login}">
				<a href="/board/write" class="btn btn-primary">글쓰기</a>
			</c:if>
			
			<c:if test="${empty login}">
				<a href="/user/login" class="btn btn-success">로그인</a>
				<a href="/user/join" class="btn btn-success">회원가입</a>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>
