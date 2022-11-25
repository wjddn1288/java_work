package page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//자바언어에서 DBMS를 연동해보는 실습
public class OracleTest {
	public static void main(String[] args) {
		// 알맞는 드라이버를 메모리에 로드하자!!
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 지정한 문자열이 경로의 클래스를 메모리에 로드시킴
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e1) {
			System.out.println("드라이버가 존재하지 않습니다.");
			e1.printStackTrace();
		}

		// 켜져있는 오라클서버에 접속해보자!!

		// 접속 url은 정해져 있는 규칙을 따른다...
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "java";
		String pass = "1234";
		Connection con = null; // finally 닫기 위해
		PreparedStatement pstmt = null; // finally 닫기 위해

		try {
			// 접속시도 후, 성공일 경우만 Connection 객체가 반환된다.
			// 따라서 Connection 객체가 만일 null이라면, 접속을 실패이다.
			con = DriverManager.getConnection(url, user, pass);
			if (con == null) {
				System.out.println("접속실패");
			} else {
				System.out.println("접속성공");
			}
			// 접속을 성공했으니, insert 문 실행해보자
			// 쿼리문을 수행시키키 위해서는, 쿼리담당 객체를 사용해야 한다.
			// 이떄 사용되는 객체명은 PreparedStatement이다.
			String sql = "insert into emp2(ename,job,sal) values('xman','none',4000)";
			pstmt = con.prepareStatement(sql);
			int result=pstmt.executeUpdate(); // 쿼리 실행!!
			if(result>0) { //반영된 레코드수가 0이 아니면 성공!!
				System.out.println("입력성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// db관련된 모든 자원 해제~!!
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(pstmt!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

		/*
		 * try { //DriverManager.getConnection(url, user, pass);
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */
	}

