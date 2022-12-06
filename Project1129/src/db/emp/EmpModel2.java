package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class EmpModel2 extends AbstractTableModel {
	EmpMain2 empMain2;
	String[] column = { "deptno", "dname", "loc", "empno", "ename", "sal", "job" };
	String[][] data;

	public EmpModel2(EmpMain2 empMain2) {
		this.empMain2 = empMain2;

		select();
	}

	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select d.deptno as deptno, dname, loc, empno, ename, sal,job ";
		sql += " from emp e inner join dept d";
		sql += "on e.deptno=d.deptno"; // n*n이 안되게 할려고
		sql += " order by deptno asc";

		try {
			pstmt = empMain2.con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst();
			for (int i = 0; i < total; i++) {
				rs.next();
				// "deptno","dname","loc","empno","ename","sal","job"
				data[i][0] = Integer.toString(rs.getInt("deptno"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("loc");
				data[i][3] = Integer.toString(rs.getInt("empno"));
				data[i][4] = rs.getString("ename");
				data[i][5] = Integer.toString(rs.getInt("sal"));
				data[i][6] = rs.getString("job");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain2.release(pstmt, rs);
		}
	}

	public int insert(int deptno, String ename, int sal, String job) {
		PreparedStatement pstmt = null;

		String sql = "insert into emp(empno, deptno,ename,sal,job)";
		sql += " values(seq_emp.nextval," + deptno + "," + ename + "," + sal + "," + job + ")";
		int result = 0;

		try {
			pstmt = empMain2.con.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain2.release(pstmt);
		}
		return result;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	public String getColumnName(int col) {
		return column[0];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
