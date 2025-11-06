<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
	Connection conn = null;

	try{
		String url = "jdbc:mysql://localhost:3306/embed";
		String user = "root";
		String password = "1111";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		
	}catch(Exception e){
		out.println("데이터베이스 연결이 실패되었습니다.<br>");
		e.printStackTrace();
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MySQL</title>
</head>
<body>

</body>
</html>