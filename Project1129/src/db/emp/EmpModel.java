package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//Emp 테이블에 대한 CRUD 및 JTable에 정보제공 
public class EmpModel extends AbstractTableModel {
	String[] column = { "deptno", "dname", "loc", "empno", "ename", "sal", "job" }; // inner join을 하기 때문에 다 넣어야됨
	String[][] data;
	EmpMain empMain;

	public EmpModel(EmpMain empMain) {
		this.empMain = empMain;

		select();// select 는 항상 맨 마지막에 있어야 한다. 접근순서 문제!
	}

	// 부서와 사원 테이블을 조인하여 가져오기
	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select d.deptno as deptno, dname, loc, empno, ename, sal, job";
		sql += " from emp e inner join dept d";
		sql += " on e.deptno=d.deptno";
		sql += " order by deptno asc";

		try {
			pstmt = empMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rs.last();// Scroll이 가능한 옵션을 주어야 가능하다
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst(); // 원상복귀
			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("deptno")); // 부서번호
				data[i][1] = rs.getString("dname");// 부서명
				data[i][2] = rs.getString("loc");// 부서위치
				data[i][3] = Integer.toString(rs.getInt("empno"));// 사원번호
				data[i][4] = rs.getString("ename"); // 사원명
				data[i][5] = Integer.toString(rs.getInt("sal"));// ,급여
				data[i][6] = rs.getString("job");// 업무
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
		}
	}

	public int insert(int deptno, String ename, int sal, String job) {
		PreparedStatement pstmt = null;

		String sql = "insert into emp(empno,deptno,ename,sal,job)";
		sql += " values(seq_emp.nextval," + deptno + ",'" + ename + "'," + sal + ",'" + job + "')";
		int result = 0;
		try {
			pstmt = empMain.con.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt);
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
		return column[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
