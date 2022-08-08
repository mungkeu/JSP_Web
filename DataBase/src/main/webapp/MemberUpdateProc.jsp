<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="mbean" class="model.MemberBean">
	<jsp:setProperty property="*" name="mbean"/>
</jsp:useBean>
<%
	MemberDAO mdao = new MemberDAO();
	
	// 스트링타입으로 저장되어있는 패스워드를 가져온다.
	String pass = mdao.getPass(mbean.getId());
	// 수정하기 위해 입력한 비밀번호와 DB의 비밀번호를 비교한다.
	if(mbean.getPass1().equals(pass)){
		// MemberDao 클래스의 회원수정 메소드를 호출
		mdao.updateMember(mbean);
		response.sendRedirect("MemberList.jsp");
	}else{
%>
		// 다르다면 이전페이지로 이동.
		<script type="text/javascript">
			alert("패스워드가 맞지 않습니다. 다시 확인해 주세요.");
			history.go(-1);
		</script>
<%	}
		
%>

</body>
</html>