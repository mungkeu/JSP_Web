<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
	// Center 값을 변경해주기 위해서 request 객체를 이용하여 center값을 받아온다.
	String center = request.getParameter("center");
	// 처음 SessionMain.jsp를 실행하면 null 값이 실행되기에 null 처리를 해준다.
	if(center==null){
		center = "Center.jsp";
	}
%>
<center>
	<table border="1" width="800">
		<!-- top -->
		<tr height="150">
			<td align="center" colspan="2">
				<jsp:include page="Top.jsp"/>
			</td>
		</tr>
		<!-- Left -->
		<tr height="400">
			<td align="center" width="200">
				<jsp:include page="Left.jsp"></jsp:include>
			</td>
		<!-- Center -->
			<td align="center" width="600">
				<jsp:include page="<%=center %>"></jsp:include>
			</td>
		</tr>
		<!-- Bottom -->
		<tr height="100">
			<td align="center" colspan="2">
				<jsp:include page="bottom.jsp"></jsp:include>
			</td>
		</tr>
	</table>
</center>
</body>
</html>