<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- 게시글 작성한 데이터를 한번에 읽어들인다. -->
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*"/>
</jsp:useBean>

<%
	// 데이터 베이스 쪽으로 빈 클래스를 넘겨준다.
	BoardDAO bdao = new BoardDAO();
	
	// 데이터 저장 메서드를 호출
	bdao.insertBoard(boardbean);
	
	// 게시글 저장 후 전체 게시글 보기
	response.sendRedirect("BoardList.jsp");
%>

</body>
</html>