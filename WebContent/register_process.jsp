<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	boolean success = true;
	String msg = "성공적으로 입력되었습니다.";
	
	String uid = request.getParameter("uid");
	String pass = request.getParameter("pass");
	String name = request.getParameter("uname");
	
	if(uid.isEmpty() || pass.isEmpty() || name.isEmpty()){
		success = false;
		msg = "값이 비어있습니다. 확인하세요.";
	}
	
	if(success){
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			String jdbcDriver = "jdbc:mysql://gmsgondr:3306/?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
			String dbUser = "";
			String dbPass = "";
			
			conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
			pstmt = conn.prepareStatement("insert into users values(?, ?,password(?))");
			pstmt.setString(1, uid);
			pstmt.setString(2, name);
			pstmt.setString(3, pass);
			
			pstmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			success = false;
			msg = "SQL 입력중 에러 발생";
		} finally {
			if (conn != null){
				try {
					pstmt.close();
				} catch(SQLException e) {
					
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch(SQLException e) {
					
				}
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>	
	웹사이트의 메시지<br>
	<%= msg %>
</body>
</html>