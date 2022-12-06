package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class DeptModel2 extends AbstractTableModel {
	String[] column = { "dept", "dname", "loc" };
	String[][] data;
	EmpMain2 empMain2;

	public DeptModel2(EmpMain2 empMain2) {
		this.empMain2 = empMain2;
		selectAll();
	}

	public void selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from dept ordery by deptno asc";
		try {
			pstmt = empMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next(); // {"dept","dname","loc"}
				data[i][0] = Integer.toString(rs.getInt("dept"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("loc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain2.release(pstmt, rs);
		}
	}

	public int insert(String dname, String loc) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "insert into dept(deptno,dname,loc)";
		sql += " values(seq_dept.nextval,'" + dname + "','" + loc + "')";
		int deptno = 0;

		try {
			pstmt = empMain2.con.prepareStatement(sql);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				sql = "select seq_dept.currval as deptno from dual";
				pstmt = empMain2.con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next();
				deptno = rs.getInt("deptno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain2.release(pstmt, rs);
		}
		return deptno;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public String getColumnName(int col) {
		return column[col];
	}

}
