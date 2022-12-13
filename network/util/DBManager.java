package network.util;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//오라클과 mysql 둘 다 처리하는 DB매니저 정의
public class DBManager {

	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
	String oracle_user = "javase";
	String oracle_pass = "1234";

	/*
	 * mysql String mysql_driver="com.mysql.jdbc.Driver"; String
	 * mysql_url="jdbc:mysql://localhost:3306/javase?characaterEncoding=utf-8";
	 * String mysql_user="root"; String mysql_pass="1234";
	 */

	private static DBManager instance; // null
	private Connection connection;

	private DBManager() {
		connectOracle();
	}

	public Connection getConnection() {
		return connection;
	}

	// 오라클 접속
	public void connectOracle() {
		try {
			// 드라이버 로드
			Class.forName(oracle_driver);

			// 접속!
			connection = DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}

		return instance;
	}

	public void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
