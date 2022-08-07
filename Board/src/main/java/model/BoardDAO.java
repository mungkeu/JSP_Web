package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import sun.jvm.hotspot.ui.treetable.AbstractTreeTableModel;

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
			String sql = "insert into board(writer, email, subject, password, reg_date, ref, re_step, re_level, readcount, content) "
					+ "values(?,?,?,?,now(),?,?,?,0,?)";
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
	
	// 모든 게시글을 가져오는 메서드
	public Vector<BoardBean> getAllBoard(int start, int end){
		
		// 리턴할 객체 생성
		Vector<BoardBean> v = new Vector<>();
		getCon();
		
		try {
			String sql = "SET @ROWNUM:=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.executeUpdate();
			
			sql = "SELECT * FROM (SELECT A.*, @ROWNUM := @ROWNUM + 1 AS Rnum FROM (SELECT * FROM BOARD ORDER BY ref desc, Re_step asc)A)B WHERE Rnum >= ? AND Rnum <= ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				v.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	// BoardInfo 하나의 게시글을 리턴하는 메서드
	public BoardBean getOneBoard(int num) {
		
		// 리턴타입 선언
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			// 조회수 증가 쿼리
			String readsql = "UPDATE BOARD SET readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			String sql = "SELECT * FROM BOARD WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	// 답변글이 저장되는 메서드
	public void reWriteBoard(BoardBean bean) {
		// 부모 글그룹과 글레벨 글스텝을 읽어들인다.
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		getCon();
		try {
			// 부모 글보다 큰 re_level의 값을 전부 1씩 증가시킨다.
			String levelsql="update board set re_level=re_level+1 where ref=? and re_level > ?";
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1,  ref);
			pstmt.setInt(2,  re_level);
			pstmt.executeUpdate();
			
			// 답변글 데이터를 저장
			String sql = "insert into board(writer, email, subject, password, reg_date, ref, re_step, re_level, readcount, content) "
					+ "values(?,?,?,?,now(),?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5,  ref); // 부모의 ref값을 넣어준다.
			pstmt.setInt(6,  re_step+1); // 답글이기에 부모 글의 re_step 1을 더해준다.
			pstmt.setInt(7,  re_level+1);
			pstmt.setString(8, bean.getContent());
			pstmt.executeUpdate();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// boardupdate 하나의 게시글을 리턴
	public BoardBean getOneUpdateBoard(int num) {
		
		// 리턴타입 선언
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			String sql = "SELECT * FROM BOARD WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	// update와 delete시 필요한 패스워드 값을  리턴해주는 메서드
	public String getPass(int num) {
		String pass = "";
		getCon();
		try {
			String sql = "SELECT password FROM BOARD WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  num);
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

	// 하나의 게시글을 수정하는 메서드
	public void updateBoard(BoardBean bean) {
		getCon();
		try {
			String sql = "update board set subject=?, content=? where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getNum());
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 하나의 게시글을 삭제하는 메서드
	public void deleteBoard(int num) {
		getCon();
		try {
			String sql = "delete from board where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 전체 글의 갯수를 리턴하는 메서드
	public int getAllCount(){
		getCon();
		// 게시글 전체수를 저장하는 변수
		int count = 0;
		try {
			String sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1); // 전체 게시글 수
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}