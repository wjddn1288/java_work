package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//JTable이 사용할 Dept 테이블에 대한 정보를 가진 객체
public class DeptModel extends AbstractTableModel {
	String[] column = { "DEPTNO", "DNAME", "LOC", };
	String[][] data;
	AppMain2 appMain2; // con이 없어서 가져와야댐!

	public DeptModel(AppMain2 appMain2) { // 생성
		this.appMain2 = appMain2;
		select();
	}

	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from dept order by deptno asc";

		try {
			pstmt = appMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행 후 표 반환

			rs.last();
			int total = rs.getRow();// 총 레코드 수 얻어옴

			// 2차원 배열 생성하기
			data = new String[total][column.length];

			// rs 커서위치 원상복귀
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("deptno"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("LOC");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			appMain2.release(pstmt, rs);
		}

	}

	@Override
	public int getRowCount() { // 층수
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

	@Override
	public String getColumnName(int col) {
		return column[col];
	}
}
