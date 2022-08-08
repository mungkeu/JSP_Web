<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
	request.setCharacterEncoding("UTF-8");	
%>
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*"/>
</jsp:useBean>
<%
	BoardDAO bdao = new BoardDAO();
	String pass = bdao.getPass(boardbean.getNum());
	
	// 기존 패스워드값과 입력한 패스워드값을 비교
	if(pass.equals(boardbean.getPassword())){
		// 데이터를 수정하는 메서드 호출
		bdao.updateBoard(boardbean);
		// 수정이 완료되면 전체 게시글 보
		response.sendRedirect("BoardList.jsp");
	}else{
%>
	<script type="text/javascript">
		alert("패스워드가 일치 하지 않습니다. 다시 확인 후 수정해주세요.");
		history.go(-1);
	</script>
<% 
	}
%>
</body>
</html>