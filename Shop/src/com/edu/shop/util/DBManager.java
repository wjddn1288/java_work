package com.edu.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//이 클래스는 앱을 이루는 모든 클래스들이, new 연산자로 인스턴스를 중복 생성ㅊ하지 않도록 안전장치를 두기 위한 SingleTon 패턴으로 정의하자.
public class DBManager {
	private static DBManager instanse;
	private Connection connection;
	
	private DBManager() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String user = "javase";
			String pass="1234";
			connection = DriverManager.getConnection(url, user, pass);
			if(connection != null) {
				System.out.println("접속성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBManager getInstanse() {
		if(instanse == null) {
			instanse = new DBManager(); 
		}
		return instanse;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void release(Connection con) {
		if(con !=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt) {
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt,ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
