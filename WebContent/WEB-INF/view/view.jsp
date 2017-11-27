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
			<h1>글보기</h1>
			<div class="card">
				<div class="card-header">
					${board.id} 글쓴이<span class="badge badge-success">${board.writer}</span><span class="badge badge-info">${board.date}</span>
				</div>
				<div class="card-body">
					<h4 class="card-title">${board.title}</h4>
					<p class="card-text">${board.content }</p>
				</div>
			</div>
			<a href="/board/list" class="btn btn-success">목록으로</a>
		</div>
	</div>
</div>
</body>
</html>
