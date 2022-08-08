<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
	String id = request.getParameter("id");
	MemberDAO mdao = new MemberDAO();
	MemberBean mbean = mdao.oneSelectMember(id);
%>

<center>
<h2>회원 정보 수정 하기</h2>
	<table width="400" border="1">
	<form action="MemberUpdateProc.jsp" nethod="post">
	<tr height="50">
		<td align="center" width="150">아이디</td>
		<td width="250"><%=mbean.getId() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">이메일</td>
		<td width="250"><input type="email" name="email" value="<%=mbean.getEmail() %>"></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">전화</td>
		<td width="250"><input type="tel" name="tel" value="<%=mbean.getTel() %>"></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">패스워드</td>
		<td width="250"><input type="password" name="pass1"></td>
	</tr>
	<tr height="50">
		<td align="center" colspan="2">
		<input type="hidden" name="id" value="<%=mbean.getId()%>">
		<input type="submit" value="회원 수정하기">&nbsp;&nbsp;</form>
		<button onclick="location.href='MemberList.jsp'">회원 전체 보기</button>
	</tr>
	</table>
</center>
</body>
</html>