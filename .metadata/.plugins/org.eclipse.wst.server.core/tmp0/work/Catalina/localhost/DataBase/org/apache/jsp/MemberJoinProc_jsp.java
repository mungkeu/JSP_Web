/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.52
 * Generated at: 2022-08-02 12:55:17 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;

public final class MemberJoinProc_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.sql.Connection");
    _jspx_imports_classes.add("java.sql.PreparedStatement");
    _jspx_imports_classes.add("java.sql.DriverManager");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<body>\n");
      out.write("\n");

	request.setCharacterEncoding("UTF-8");
	
	// 취미 부분은 별도로 읽어들어 다시 빈 클래스에 저장한다.
	String[] hobby = request.getParameterValues("hobby");
	// 배열에 있는 내용을 하나의 스트링으로 저장
	String texthobby="";
	
	for(int i=0; i<hobby.length; i++){
		texthobby += hobby[i] + " ";
	}


      out.write("\n");
      out.write("	<!-- useBean을 이용하여 한꺼번에 데이터를 받아온다. -->\n");
      out.write("	");
      model.MemberBean mbean = null;
      mbean = (model.MemberBean) _jspx_page_context.getAttribute("mbean", javax.servlet.jsp.PageContext.PAGE_SCOPE);
      if (mbean == null){
        mbean = new model.MemberBean();
        _jspx_page_context.setAttribute("mbean", mbean, javax.servlet.jsp.PageContext.PAGE_SCOPE);
        out.write('\n');
        out.write('	');
        out.write('	');
        org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("mbean"), request);
        out.write(" <!-- 맵핑 시키시오 -->\n");
        out.write("	");
      }
      out.write('\n');

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

      out.write("\n");
      out.write("\n");
      out.write("	오라클 완료 ~\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
