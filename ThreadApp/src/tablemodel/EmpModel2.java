package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class EmpModel2 extends AbstractTableModel {
	AppMain2JW appMain2JW;
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO", };
	String[][] data = null;

	public EmpModel2(AppMain2JW appMain2JW) {
		this.appMain2JW = appMain2JW;
		
		select();
	}

	public void select() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from emp order by empno asc";
		
		try {
			pstmt=appMain2JW.con.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			rs.last();
			int total=rs.getRow();
			
			data=new String[total][column.length];
			
			rs.beforeFirst();
			for(int i=0; i<total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("empno"));
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
		}finally {
			appMain2JW.release(pstmt,rs);
		}
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
		System.out.println(col+"번째 컬럼명을 알고 싶어요");
		return column[col];
	}
}
