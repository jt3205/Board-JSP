<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="header.jsp" %>
<title>로그인</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1>로그인 페이지</h1>
				<form action="/user/login" method="POST">
					<div class="form-group">
						<label for="uid">ID</label> <input type="text"
							class="form-control" id="uid" name="uid" placeholder="아이디를 입력하세요">
					</div>
					<div class="form-group">
						<label for="pass1">비밀번호</label> <input type="password"
							class="form-control" id="pass1" name="pass1"
							placeholder="비밀번호 입력">
					</div>
					<button type="submit" class="btn btn-primary">로그인</button>
					<a href="/" class="btn btn-primary">메인 페이지로</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
