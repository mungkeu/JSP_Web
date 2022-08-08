<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<center>
	<table border="1" width="800">
		<!-- top -->
		<tr height="150">
			<td align="center" colspan="2">
				<jsp:include page="Top.jsp">
					<jsp:param value="son" name="id"/>
				</jsp:include>
			</td>
		</tr>
		<!-- Left -->
		<tr height="400">
			<td align="center" width="200">
				<jsp:include page="Left.jsp"></jsp:include>
			</td>
		<!-- Center -->
			<td align="center" width="600">
				<jsp:include page="Center.jsp"></jsp:include>
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