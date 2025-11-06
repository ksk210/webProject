<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, org.embed.dto.*, org.embed.dao.*" %>

<%
	String id = request.getParameter("id");
	if(id == null || id.trim().equals("")){
		response.sendRedirect("books.jsp");
	}
	
	BookRepasitory dao = BookRepasitory.getInstance();
	
	Book book = dao.getBookById(id);
	if(book == null){
		response.sendRedirect("exceptionNoBookId.jsp");
	}
	
	ArrayList<Book> cartList = (ArrayList<Book>)session.getAttribute("cartlist");
	Book goodsQnt = new Book();
	for(int i = 0; i < cartList.size(); i++){
		goodsQnt = cartList.get(i);
		if(goodsQnt.getBookId().equals(id)){
			cartList.remove(goodsQnt);
		}
	}
	
	response.sendRedirect("cart.jsp");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개별 도서 삭제</title>
</head>
<body>

</body>
</html>