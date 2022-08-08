<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<h2> 세션 로그인 처리1 </h2>
<%
	// 세션을 이용하여 데이터를 불러온다.
	String id = (String)session.getAttribute("id");
	String pass = (String)session.getAttribute("pass"); 
%>
<h2>당신의 아이디는 <%=id %>입니다.</h2>
<h2>당신의 비밀번호는 <%=pass %>입니다.</h2>
</body>
</html>