<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");

	// 프로젝트내에 만들어질 폴더를 저장할 이름 변수선언
	String realfolder="";
	// 실제 만들어질 폴더명을 설정
	String savefolder="/upload";
	// 한글 설정
	String encType="UTF-8";
	// 저장될 파일 사이즈를 설정
	int maxSize = 1024*1024*5; // 5m
	
	// 파일이 저장될 경로값을 읽어오는 객체
	ServletContext context = getServletContext();
	realfolder = context.getRealPath(savefolder);
	
	try{
		// 클라이언트로부터 넘어온 데이터를 저장해주는 객체
		MultipartRequest multi = new MultipartRequest(request, realfolder, maxSize, encType, 
				new DefaultFileRenamePolicy()); // new DefaultFileRenamePolicy()는 이름 중복시 이름을 바꾸어서 저장
%>
	당신의 이름은 <%=multi.getParameter("name") %>
<% 
	out.println(realfolder);
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>