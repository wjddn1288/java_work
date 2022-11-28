package tablemodel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

//JTable 에서 TableModel 사용이 상당히 큰 비중을 차지하는데, 왜 사용해야하는지를 이해하기 위한 예제..
//사용했을경우와 사용하지 않았을 경우를 비교 해보자 !!

/*  JDBC 프로그래밍 절차 (자바의 DB연동 프로그램 절차)
 * 1) 드라이버 로드
 * 2) 접속
 * 3) 쿼리문
 * 4) 자원해제 
 * 순으로 이루어진다. 잊지 말기!!  */
public class AppMain2 extends JFrame {

	JTable table;
	JScrollPane scroll; // 스크롤을 이용해야 컬럼제목이 노출된다.
	/*
	 * String[][] data; // 2차원 배열 선언 emp연동 예정 String[] column = { "EMPNO", "ENAME",
	 * "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" }; 앞으로 안씀 지움
	 */
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection con; // 멤버변수로 선언한 이유는? 원할때 접속을 끊기위해...
	TableModel model;// emp 테이블에 대한 데이터를 가진 객체,
						// 유연하게 가기 위해 상위개념으로 해줌! 유지보수할때 table 만 바꿔주면 됨!!

	public AppMain2() {
		// 값이 null이라서 오라클 연동해줘야됨
		connect(); // 오라클 접속
		// select();// 레코드 가져오기

		table = new JTable(model = new DeptModel(this));
		scroll = new JScrollPane(table);

		add(scroll);

		setSize(600, 400);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); DB연동할때는 무조건 쓰면 안된다. System.exit(0)이게
		// 있으니까

		// 윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con); // 오라클 연결해제
				System.exit(0); // 프로세스 종료
			}
		});
	}

	public void connect() {
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 접속
			con = DriverManager.getConnection(url, user, pass);

			if (con == null) {
				this.setTitle("접속 실패");
			}

			else {
				this.setTitle("접속 성공");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// emp에서 레코드 가져오기
//	public void select() {
//		// 아래의 두 객체는 쿼리문 마다 1:1대응하여 생성해야 하므로, 쿼리문 수행후 객체를 닫는다. 따라서 지역변수로 선언함
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String sql = "select * from emp order by empno asc";
//
//		try {
//			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리수행 객체
//																												// 생성만
//																												// 한것임.
//			rs = pstmt.executeQuery(); // select문 수행시 사용할 메서드
//			// 현재 rs에는 emp 테이블이 들어 있다. 따라서 커서를 이동해가면서 원하는 레코드에 접근 할 수 있다.
//			// JTable의 생성자가 rs가 아닌, 이차원배열을 원하므로, rs에 들어있는 데이터들을 이차원배열로 전환하자!!
//			rs.last();
//			int total = rs.getRow();
//			System.out.println("사원수는" + total);
//
//			data = new String[total][column.length];
//
//			// 커서를 다시 복귀시킨다!!
//			rs.beforeFirst(); // 첫번째 레코드 보다도 위쪽!!(아무것도 가리키지 않음)
//
//			for (int i = 0; i < total; i++) {
//				rs.next(); // 커서 한 칸 전진
//				// {"EMPNO","ENAME","JOB","MGR","HIREDATE","SAL","COMM","DEPTNO"};
//				data[i][0] = Integer.toString(rs.getInt("empno"));
//				data[i][1] = rs.getString("ENAME");
//				data[i][2] = rs.getString("JOB");
//				data[i][3] = Integer.toString(rs.getInt("MGR"));
//				data[i][4] = rs.getString("HIREDATE");
//				data[i][5] = Integer.toString(rs.getInt("SAL"));
//				data[i][6] = Integer.toString(rs.getInt("COMM"));
//				data[i][7] = Integer.toString(rs.getInt("DEPTNO"));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	// 접속을 해제
	public void release(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt, ResultSet rs) {
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

	public static void main(String[] args) {
		new AppMain2();
	}
}
