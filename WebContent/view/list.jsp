<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/custom.css"/>
</head>
<body>
<h1 class="page-header">글 목록 보기</h1>
<a href="/board/write.jsp" class='btn'>글 쓰기</a>
<div>
	<div class="text-panel">
		<div class="panel-heading">
			<div class="item item-left">
				<span class="left">글쓴이 : 아무개</span>
			</div>
			<div class="item item-right">
				<span class="right">글번호: 2</span>
			</div>
		</div>
		<div class="panel-body">
			방명록의 글 내용입니다.
		</div>	
	</div>
	<div class="text-panel">
		<div class="panel-heading">
			<div class="item item-left">
				<span class="left">글쓴이 : 아무개</span>
			</div>
			<div class="item item-right">
				<span class="right">글번호 : 1</span>
			</div>
		</div>
		<div class="panel-body">
			방명록의 글 내용입니다.
		</div>	
	</div>
</div>

</body>
</html>
