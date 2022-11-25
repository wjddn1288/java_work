package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//오라클에 접속하여 emp테이블의 레코드를 가져와보자!!
public class SelectTest {

	// throws 란? try~catch 블럭으로 해당 예외를 개발자가 처리하기 싫을때, 이 예외가 발생된
	// 메서드를 호출한, 호출자에게 예외처리를 전가시킨다는 뜻
	// throws ClassNotFoundException
	// resultset 서버에서 결과를 개발자에게 보여주기 위해 담는 바구니?
	public static void main(String[] args) {

		/*
		 * Java DataBase Connectivity : 자바기반의 데이터베이스 연동 기술(개념), java.sql패키지에서 지원함
		 * JDBC기반의 DB 연동 절차 1) 드라이버 로드(사용하고자 하는 DBMS 제품에 맞는..) Java언어가 해당 DB제품을 핸들링 하기
		 * 위한 라이브러리 2) 접속 3) 쿼리실행 4) Db관련 자원해제(스트림과 DB는 개발자가 반드시 닫아야함(무조건))
		 */
		// 인스턴스를 올리는 것이 아니라, static영역으로 지정한 드라이버 클래스를 Load한다!!
		Connection con = null; // java.sql 패키지
		PreparedStatement pstmt = null; // 쿼리수행 객체
		ResultSet rs = null; // java.sql 결과표를 표현한 객체
		// 접속이 성공되면, Connectuin 객체가 채워진다!! 따라서 con이 null이면 접속 실패임!
		// Connection 안에는 뭐가 들어가있을까? 접속정보가 들어있다.. 접속을 끊을때 중요한 역할을 함...

		// 1) 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 시스템이 메모리 영역으로 올리는데 이 코드는 개발자가 직접 메모리로 올리는 것이다.
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		// 2) 접속시도
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "java";
		String pass = "1234";

		try {
			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				System.out.println("접속성공");
			} else {
				System.out.println("접속실패");
			}

			String sql = "select * from emp order by empno";
			pstmt = con.prepareStatement(sql); // 쿼리수행객체 생성
			rs = pstmt.executeQuery(); // 쿼리수행 및 표 생성!! rs를 표 자체로 보면 된다.

			// rs의 레코드를 사용하기 위해서는, 커서를 이동시켜야하는데,
			// rs 생성된 초기에는, 그 어떤 레코드도 가리키지 않고 제일 위로 올라와 있다..

			while (rs.next()) { // next()가 참인 동안..
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println(empno + "\t" + ename + "\t" + job);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
