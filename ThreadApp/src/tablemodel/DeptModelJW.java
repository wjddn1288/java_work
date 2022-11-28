package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class DeptModelJW extends AbstractTableModel {
	String[] column = { "DEPTNO", "DNAME", "LOC" };
	String[][] data;
	AppMain2JW appMain2JW;

	public DeptModelJW(AppMain2JW appMainJW) {
		this.appMain2JW = appMain2JW;
		select();
	}

	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from dept order by deptno asc";

		try {
			pstmt = appMain2JW.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();

			rs.last();
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("DEPTNO"));
				data[i][1] = rs.getString("DNAME");
				data[i][2] = rs.getString("LOC");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			appMain2JW.release(pstmt, rs);
		}

	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}

}
