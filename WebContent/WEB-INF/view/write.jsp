<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
	integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
	integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
	crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 작성</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1>글 작성</h1>
				<form action="/board/write" method="POST">
					<div class="form-group">
						<label for="title">글제목</label> <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요">
					</div>
					<div class="form-group">
						<label for="content">글내용</label> 
							<textarea rows="10" class="form-control" id="content" name="content" placeholder="글 내용 입력"></textarea>
					</div>
					<button type="submit" class="btn btn-primary">글작성</button>
					<a href="/" class="btn btn-primary">메인 페이지로</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
