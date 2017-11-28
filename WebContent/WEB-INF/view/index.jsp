<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.jsp"%>
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
				<c:forEach items="${boardList}" var="board">
					<tr>
						<td>${board.id}</td>
						<td><a href="/board/view?id=${board.id}">${board.title}</a></td>
						<td>${board.writer}</td>
						<td>${board.date}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
		<nav>
			<ul class="pagination justify-content-center">
				<c:if test="${pager.prev}">
					<li class="page-item">
						<a class="page-link" href="/board/list?page=${pager.startPage-1}" aria-label="Previous"> 
						<spanaria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>
				
				<c:forEach begin="${pager.startPage}" end = "${pager.endPage}" var="i">
					<li class="page-item"><a class="page-link" href="/board/list?page=${i}">${i}</a></li>
				</c:forEach>
				<c:if test="${pager.next}">
					<li class="page-item">
						<a class="page-link" href="/board/list?page=${pager.endPage+1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span></a>
					</li>
				</c:if>		
			</ul>
		</nav>
		</div>
	</div>
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
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