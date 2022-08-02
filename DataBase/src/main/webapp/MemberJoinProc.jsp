<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
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

	// 오라클에 접속하는 소스를 작
	String id = "system";
	String pass="oracle";
	String url="jdbc:oracle:thin:@localhost:1521:XE"; // 접속 URL

	try{
		// 1. 해당 데이터 베이스를 사용한다고 선언(클래스를 등록=오라클용을 사용)
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. 해당 데이터 베이스에 접속
		Connection con = DriverManager.getConnection(url,id,pass);
		// 3. 접속 후 쿼리를 준비
		String sql="INSERT INTO MEMBER values(?,?,?,?,?,?,?,?)";
		// 4. 쿼리를 사용하도록 설정
		PreparedStatement pstmt = con.prepareStatement(sql);
		// 5. ?에 맞게 데이터를 맵핑
		pstmt.setString(1, mbean.getId());
		pstmt.setString(2, mbean.getPass1());
		pstmt.setString(3, mbean.getEmail());
		pstmt.setString(4, mbean.getTel());
		pstmt.setString(5, mbean.getHobby());
		pstmt.setString(6, mbean.getJob());
		pstmt.setString(7, mbean.getAge());
		pstmt.setString(8, mbean.getInfo());
		// 6. 오라클에서 쿼리를 실행시키시오.
		pstmt.executeUpdate(); // insert, update, delete 시 사용하는 메소
		// 7. 자원 반납
		con.close();
	} catch(Exception e){
		e.printStackTrace();
	}
%>

	오라클 완료 ~
</body>
</html>