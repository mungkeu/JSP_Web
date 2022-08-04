package model;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


// 오라클 데이터 베이스에 연결하고 select, insert, update, delete 작업을 실행해주는 클래스
public class MemberDAO {

	// 오라클에 접속하는 소스를 작성
	//String id = "system";
	//String pass="oracle";
	//String url="jdbc:oracle:thin:@localhost:1521:XE"; // 접속 URL
	
	Connection con; // 데이터베이스에 접근할 수 있도록 설정
	PreparedStatement pstmt; // 데이터 베이스에서 쿼리를 실행시켜주는 객체
	ResultSet rs; // 데이터베이스 테이블의 결과를 리턴받아 자바에 저장해주는 객체
	
	// 데이터 베이스에 접근할수 있도록 도와주는 메서드
	public void getCon() {
		// 커넥션풀을 이용하여 데이터 베이스에 접근
		try {
			// 외부에서 데이터를 읽어들인다.
			Context initctx = new InitialContext();
			// 톰캣 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");
			// 데이터 소스 객체를 선언
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			// 데이터 소스를 기준으로 커넥션을 연결한다.
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			// 1. 해당 데이터 베이스를 사용한다고 선언(클래스를 등록=오라클용을 사용)
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			// 2. 해당 데이터 베이스에 접속
//			con = DriverManager.getConnection(url,id,pass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	// 데이터 베이스에 한사람의 회원 정보를 저장해주는 메서드
	public void insertMember(MemberBean mbean) {
		try{
			getCon();
			// 3. 접속 후 쿼리를 준비
			String sql="INSERT INTO MEMBER values(?,?,?,?,?,?,?,?)";
			// 4. 쿼리를 사용하도록 설정
			pstmt = con.prepareStatement(sql);
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
	}
	
	// 모든 회원의 정보를 반환하는 메서
	public Vector<MemberBean> allSelectMember(){
		Vector<MemberBean> v = new Vector<>();
		
		try {
			// 커넥션 연결
			getCon();
			// 쿼리 준비
			String sql = "SELECT * FROM MEMBER";
			// 쿼리를 실행시켜주는 객체 선언
			pstmt = con.prepareStatement(sql);
			// 쿼리를 실행 시킨 결과를 리턴해서 받아준다.(오라클 테이블의 검색된 결과를 자바객체에 저장)
			rs = pstmt.executeQuery();
			// 반복문을 사용해서 rs에 저장된 데이터를 추출해 놓아야 한다.
			while(rs.next()) { // 저장된 데이터 만큼만 반복한다.
				MemberBean bean = new MemberBean();
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
				// 패키징된 memberbean클래스를 벡터에 저장
				v.add(bean);
			}
			// 자원 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 다 저장된 벡터를 리턴 
		return v; 
	}
	// 한 사람에 대한 정보를 리턴하는 메서드 작성
	public MemberBean oneSelectMember(String id) {
		MemberBean bean = new MemberBean();
		
		try {
			// 커넥션 연결 
			getCon();
			// 쿼리 준비 
			String sql="SELECT * FROM MEMBER WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 맵핑 
			pstmt.setString(1, id);
			// 쿼리 실행 
			rs = pstmt.executeQuery();
			if(rs.next()) { // 레코드가 있다면 사실상 있다. 아이디클릭해서 넘어가기 때문
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return bean;
	}
	
	// 한 회원의 패스워드값을 리턴하는 메서드 작성
	public String getPass(String id) {
		String pass = "";
		
		try {
			getCon();
			String sql = "SELECT pass1 FROM MEMBER WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pass = rs.getString(1);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	// 한 회원의 정보를 수정하는 메서드
	public void updateMember(MemberBean bean) {
		getCon();
		try {
			String sql = "UPDATE MEMBER SET email=?, tel=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  bean.getEmail());
			pstmt.setString(2,  bean.getTel());
			pstmt.setString(3,  bean.getId());
			
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 한 회원을 삭제하는 메서드 작성
	public void deleteMember(String id)
	{
		getCon();
		
		try {
			String sql = "DELETE FROM MEMBER WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
