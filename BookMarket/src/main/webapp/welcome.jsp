<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>   
   <div class="container py-4">
       <%@ include file="menu.jsp" %>
       <%!
          String greeting = "도서 쇼핑몰에 오신 것을 환영합니다.";
          String tagline = "Welcome to Web Market!";
       %>
      
      <div class="p-5 mb-4 bg-body-tertiary ronnded-3">
         <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold"><%=greeting %></h1>
            <p class="col-md-8 fs-4">BookMarket</p>
         </div>
      </div>
      
      <div class="row align-items-md-stretch text-center">
         <div class="col-md-12">
            <div class="h-100 p-5">
               <h3><%=tagline %></h3>
               
               <%
               		response.setIntHeader("Refresh", 3);
               		Date day = new Date();
               		String am_pm;
               		int hour = day.getHours();
               		int minute = day.getMinutes();
               		int second = day.getSeconds();
               		
               		if(hour/12 == 0){
               			am_pm = "AM";
               		}else {
               			am_pm = "PM";
               			hour = hour - 12;
               		}
               		
               		String ct = hour + ":" + minute + ":" + second + " " + am_pm;
               		out.println("현재접속 시각 : " + ct + "\n");
               %>
               
            </div>
         </div>
      </div>
      
      <%@ include file="footer.jsp" %>
   </div>
</body>
</html>










