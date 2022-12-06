package com.edu.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//이 클래스는 앱을 이루는 모든 클래스들이
//new 연산자로 인스턴스를 중복생성하지 않도록 안전장치를 두기 위한 SingleTon 패턴으로 정의하자!
public class DBManager {
	private static DBManager instance; //null
	private Connection connection;
	
	private DBManager() { // 생성자 선언(private -> new 못하고 getter메소드 써야 함)
		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//접속
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="javase";
			String pass="1234";
			
			connection = DriverManager.getConnection(url,user,pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static DBManager getInstance() {
		if(instance==null) {
			instance=new DBManager();
		}
		return instance;
	}
	
	//필요한 자가 이 메서드를 호출하여 Connection을 얻어 감 : 쿼리문도 날리고 접근해지도 가능하고
	public Connection getConnection() {
		return connection;
	}
	
	//윈도우와 리스너 연결시 사용
	public void release(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DML인 경우 사용 (update, delete문)인 경우에만 사용
	public void release(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//select인 경우 사용
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
