package tablemodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppMainJW extends JFrame {
	JTable table;
	JScrollPane scroll;
	String[][] data;
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };
	String[] column2 = { "DEPTNO", "DNAEM", "LOC" };

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection con;

	public AppMainJW() {
		connect();
		table = new JTable();
		scroll = new JScrollPane(table);

		add(table);

		setSize(600, 400);
		setVisible(true);
	}

	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				System.out.println("접속 성공");
			} else {
				System.out.println("접속 실패");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from emp order by empno asc";

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			System.out.println("사원수는" + total);

			data = new String[total][column.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				data[i][0] = Integer.toString(rs.getInt("EMPNO"));
				data[i][1] = rs.getString("ENAME");
				data[i][2] = rs.getString("JOB");
				data[i][3] = Integer.toString(rs.getInt("MGR"));
				data[i][4] = rs.getString("HIREDATE");
				data[i][5] = Integer.toString(rs.getInt("SAL"));
				data[i][6] = Integer.toString(rs.getInt("COMM"));
				data[i][7] = Integer.toString(rs.getInt("DEPTNO"));
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

	public void selectDept() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from dept order by deptno asc";
		
		try {
			pstmt=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs= pstmt.executeQuery();
			rs.last();
			
			int total=rs.getRow();
			data = new String[total][column.length];
			
			rs.beforeFirst();
			
			for(int i=0; i<total; i++) {
				rs.next();
				data[i][0] =Integer.toString(rs.getInt("DEPTNO"));
				data[i][1] =rs.getString("DNAME");
				data[i][2] =rs.getString("LOC");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs !=null) {
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
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AppMainJW();
	}
}
