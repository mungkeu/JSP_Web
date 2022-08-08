<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<!-- 
	0. MemberList에서 넘기 id를 받아준다.
	1. 데이터 베이스에서  회원의 정보를 가져온다.
	2. table 태그를 이용하여 화면에 회원의 정보를 출 
-->
<%
	String id = request.getParameter("id");
	MemberDAO mdao = new MemberDAO();
	MemberBean mbean = mdao.oneSelectMember(id);
%>

<center>
<h2>회원 정보 보기</h2>
	<table width="400" border="1">
	<tr height="50">
		<td align="center" width="150">아이디</td>
		<td width="250"><%=mbean.getId() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">이메일</td>
		<td width="250"><%=mbean.getEmail() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">전화</td>
		<td width="250"><%=mbean.getTel() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">취미</td>
		<td width="250"><%=mbean.getHobby() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">직업</td>
		<td width="250"><%=mbean.getJob() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">나이</td>
		<td width="250"><%=mbean.getAge() %></td>
	</tr>
	<tr height="50">
		<td align="center" width="150">정보</td>
		<td width="250"><%=mbean.getInfo() %></td>
	</tr>
	<tr height="50">
		<td align="center" colspan="2">
		<button onclick="location.href='MemberUpdateForm.jsp?id=<%=mbean.getId()%>'"> 회원수정 </button>
		<button onclick="location.href='MemberDeleteForm.jsp?id=<%=mbean.getId()%>'"> 회원삭제 </button>
		<button onclick="location.href='MemberList.jsp?id=<%=mbean.getId()%>'"> 회원보기 </button>
		<button onclick="location.href='MemberJoin.jsp?id=<%=mbean.getId()%>'"> 회원가입 </button>
	</tr>
	</table>
</center>
</body>
</html>