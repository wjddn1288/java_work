package page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLTest {

	public static void main(String[] args) {
		// Mysql용 드라이버를 로드하자!!
		try {
			Class.forName("com.mysql.jdbc.Driver"); //클래스로드, static 원본코드
			System.out.println("mysql용 드라이버 로드 성공");

			// 접속시도
			String url = "jdbc:mysql://localhost:3306/java";
			String user = "root";
			String pass = "1234";

			Connection con=DriverManager.getConnection(url, user, pass);
			if(con!=null) {
				System.out.println("접속 성공");
			}
			String sql="insert into emp2(empno,ename) values(8000,'JW')";
			PreparedStatement pstmt=con.prepareStatement(sql); //임폴트 할때 자바껄로 임폴트 하자
			int result=pstmt.executeUpdate(); //반환형을 먼저 적어주면 알아서 찾아준다.!!
			if(result>0) {
				System.out.println("성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 	
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
