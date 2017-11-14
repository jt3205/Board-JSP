<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
	integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
	integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
	crossorigin="anonymous"></script>
<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		alert(msg);
	</script>
	<c:set var="msg" value=""/>
</c:if>
<title>회원가입</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1>회원가입 페이지</h1>
				<form action="/user/join" method="POST">
					<div class="form-group">
						<label for="uid">ID</label> <input
							type="text" class="form-control" id="uid" name="uid"
							placeholder="아이디를 입력하세요">
					</div>
					<div class="form-group">
						<label for="uname">이름</label> <input
							type="text" class="form-control" id="uname" name="uname"
							placeholder="이름을 입력하세요">
					</div>
					<div class="form-group">
						<label for="pass1">비밀번호</label> <input
							type="password" class="form-control" id="pass1" name="pass1"
							placeholder="비밀번호 입력">
					</div>
					<div class="form-group">
						<label for="pass2">비밀번호 확인</label> <input
							type="password" class="form-control" id="pass2" name="pass2"
							placeholder="비밀번호 확인 입력">
					</div>
					<button type="submit" class="btn btn-primary">회원 가입</button>
					<a href="/" class="btn btn-primary">메인 페이지로</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
