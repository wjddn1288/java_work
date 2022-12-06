package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//Dept 테이블에 대한 CRUD 및 JTable에 정보제공
public class DeptModel extends AbstractTableModel {
	String[] column = { "dept", "dname", "loc" };
	String[][] data;
	EmpMain empMain; // EmpMain에 Connection 이 있으므로.,.

	public DeptModel(EmpMain empMain) {
		this.empMain = empMain;
		selectAll();
	}

	// CRUD 중 select
	public void selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from dept order by deptno asc";
		try {
			pstmt = empMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행 및 테이블 반환
			rs.last();
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst(); // 원상복귀

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("deptno"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("loc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs); // 해제
		}
	}

	// 한건 등록
	public int insert(String dname, String loc) {
		PreparedStatement pstmt=null;
		ResultSet rs=null; //시퀀스를 담기 위해...
		
		String sql = "insert into dept(deptno,dname,loc)";
		sql += " values(seq_dept.nextval,'"+dname+"','"+loc+"')";
		int deptno=0;
		
		try {
			pstmt=empMain.con.prepareStatement(sql);
			int result=pstmt.executeUpdate(); //한건 등록 실행!!
			
			if(result>0){
				sql="select seq_dept.currval as deptno from dual";
				//PreparedStatement, ResultSet은 쿼리문 마다 1:1 생성하여 사용하면 된다.
				pstmt=empMain.con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				rs.next(); //커서 한칸 전진!! 스크롤 기능 필요없다.
				deptno=rs.getInt("deptno");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain.release(pstmt,rs);
		}
		return deptno;
	}

	@Override
	public int getRowCount() {
		return data.length; // 층수
	}

	@Override
	public int getColumnCount() {
		return column.length; // 호수
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public String getColumnName(int col) {
		return column[col];
	}

}
