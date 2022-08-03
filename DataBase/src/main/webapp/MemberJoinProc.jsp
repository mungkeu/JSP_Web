<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="model.MemberDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>

<%
	request.setCharacterEncoding("UTF-8");
	
	// 취미 부분은 별도로 읽어들어 다시 빈 클래스에 저장한다.
	String[] hobby = request.getParameterValues("hobby");
	// 배열에 있는 내용을 하나의 스트링으로 저장
	String texthobby="";
	
	for(int i=0; i<hobby.length; i++){
		texthobby += hobby[i] + " ";
	}

%>
	<!-- useBean을 이용하여 한꺼번에 데이터를 받아온다. -->
	<jsp:useBean id="mbean" class="model.MemberBean">
		<jsp:setProperty name="mbean" property="*" /> <!-- 맵핑 시키시오 -->
	</jsp:useBean>
<%
	//기존 취미는 배열의 첫 데이터만 저장되어 위에서 스트링으로 다시 저장한 변수를 넣어준다.
	mbean.setHobby(texthobby);

	// 데이터 베이스클래스 객체 생성
	MemberDAO mdao = new MemberDAO();
	mdao.insertMember(mbean);
	
	// 회원 가입이 되었다면 회원 정보를 보여주는 페이지로 이동
	response.sendRedirect("MemberList.jsp");
	
%>

	오라클 완료 ~
</body>
</html>