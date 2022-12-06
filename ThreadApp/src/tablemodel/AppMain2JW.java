<<<<<<< HEAD
package tablemodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppMain2JW extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	Connection con;
	
	public AppMain2JW() {
		connect();
		table= new JTable();
		scroll=new JScrollPane(table);
		
		add(scroll);
		
		setSize(600,400);
		setVisible(true);
		
	}
	
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection(url, user, pass);
			
			if(con==null) {
				this.setTitle("접속 실패");
			}else {
				this.setTitle("접속 성공");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new AppMain2JW();
	}
}
=======
package tablemodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppMain2JW extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	Connection con;
	
	public AppMain2JW() {
		connect();
		table= new JTable();
		scroll=new JScrollPane(table);
		
		add(scroll);
		
		setSize(600,400);
		setVisible(true);
		
	}
	
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection(url, user, pass);
			
			if(con==null) {
				this.setTitle("접속 실패");
			}else {
				this.setTitle("접속 성공");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new AppMain2JW();
	}
}
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
