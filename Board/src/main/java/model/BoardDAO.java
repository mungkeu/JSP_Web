package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 데이터 베이스의 커넥션풀을 사용하도록 설정하는 메서드
	public void getCon() {
		
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			con=ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 하나의 새로운 게시글이 넘어와서 저장되는 메서드
	public void insertBoard(BoardBean bean) {
		
		getCon();
		// 빈 클래스에서 넘어오지 않은 데이터를 초기화 해준다.
		int ref=0; // 글 그룹을 의미 = 쿼리를 실행시켜서 가장 큰 ref 값을 가져온 후 +1을 더해준다.
		int re_step=1; // 새글, 즉 부모글이므로 1을 준다.
		int re_level=1;
		
		try {
			// 가장 큰 ref값을 읽어오는 쿼리 준비
			String refsql = "select max(ref) from board";
			// 쿼리 실행 객체
			pstmt = con.prepareStatement(refsql);
			// 쿼리 실행 후 결과 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) { //결과 값이 있다면.
				ref = rs.getInt(1) + 1; // 최대값에 +1을 더해서 글그룹을 설정
			}
			// 실제 게시글 전체 값을 테이블에 저장
			String sql = "insert into board(writer, email, subject, password, reg_date, ref, re_step, re_level, readcount, content) values(?,?,?,?,now(),?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 맵핑
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5,  ref);
			pstmt.setInt(6,  re_step);
			pstmt.setInt(7,  re_level);
			pstmt.setString(8, bean.getContent());
			// 쿼리 실행
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
