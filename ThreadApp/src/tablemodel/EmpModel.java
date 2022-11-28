package tablemodel;
/*JTable은 유지보수성을 높이기 위해, 데이터와 디자인을 분리시키기 위한 
 * 방법을 제공해주며, 이때 사용되는 객체가 바로 TableModel이다...*/

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.AbstractTableModel;

public class EmpModel extends AbstractTableModel {
	// TableModel이 보유한 메서드들은 JTable 이 호출하여, 화면에 반영한다.
	AppMain2 appMain2; // 기존껄 가져와야되니까 new는 안됨 매겨변수와 생성자를 이용
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };
	String[][] data = null;

	// 태어날때 AppMain2를 넘겨받자
	public EmpModel(AppMain2 appMain2) {
		this.appMain2 = appMain2;

		select();
	}

	// EMP의 레코드 가져오기
	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from emp order by empno asc";

		try {
			pstmt = appMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행후 테이블 반환

			rs.last(); // 스크롤 가능한 rs이어야 한다...
			int total = rs.getRow();

			// 이 정보들을 이용하여 이차원 배열생성
			data = new String[total][column.length];

			rs.beforeFirst(); // 커서 다시 원상복귀!!
			for (int i = 0; i < total; i++) {
				rs.next(); // 커서 한 칸 전진
				// {"EMPNO","ENAME","JOB","MGR","HIREDATE","SAL","COMM","DEPTNO"};
				data[i][0] = Integer.toString(rs.getInt("empno"));
				data[i][1] = rs.getString("ENAME");
				data[i][2] = rs.getString("JOB");
				data[i][3] = Integer.toString(rs.getInt("MGR"));
				data[i][4] = rs.getString("HIREDATE");
				data[i][5] = Integer.toString(rs.getInt("SAL"));
				data[i][6] = Integer.toString(rs.getInt("COMM"));
				data[i][7] = Integer.toString(rs.getInt("DEPTNO"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			appMain2.release(pstmt, rs);
		}
	}

	@Override
	// 총 레코드 수
	public int getRowCount() { // 층수
		return data.length;
	}

	@Override
	// 컬럼 수
	public int getColumnCount() { // 호수
		return column.length;
	}

	@Override
	// 2차원 배열을 이루는 각 요소들의 들어있는 값 반환
	public Object getValueAt(int row, int col) {
		System.out.println("getValueAt(" + row + "," + col + " )호출");
		return data[row][col];
	}

	@Override
	// 위의 3가지 메서드 뿐 아니라, 추가적으로 필요한 메서드가 있다면 또 재정의하자!!
	public String getColumnName(int col) {
		System.out.println(col + "번째 컬럼명을 알고 싶어요");
		return column[col];
	}

}
