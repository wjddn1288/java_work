<<<<<<< HEAD
package boardmodel;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//오라클의 Board 테이블에 관한 정보를 가진 모델 객체
public class BoardModel extends AbstractTableModel {
	BoardMain boardMain;
	String[] column = { "board_id", "title", "writer", "content", "regdate", "hit"};
	String[][] data;

	public BoardModel(BoardMain boardMain) { // 생성자를 안써서 아까우니까 생성해주면서 매개변수로 받는다.
		this.boardMain = boardMain;
		select(null, null);
	}

	public void select(String category, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;
		if (category == null && keyword == null) {
			// 검색하지 않을 경우
			sql = "select * from board order by board_id desc";
		} else {
			// 검색인 경우
			sql = "select * from board where " + category + " like '%" + keyword + "%'";
		}
		System.out.println(sql);

		try {
			pstmt = boardMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리
																														// 객체
			rs = pstmt.executeQuery();

			rs.last();
			int total = rs.getRow(); // 총 몇건인지 반환!!

			// rs는 곧 죽으므로, rs를 대신하여 데이터를 담게 될 이차원배열생성
			data = new String[total][column.length];
			rs.beforeFirst();// 커서위치 원상복귀!!

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("board_id"));
				data[i][1] = rs.getString("title"); // 제목
				data[i][2] = rs.getString("writer"); // 작성자
				data[i][3] = rs.getString("content"); // 내용
				data[i][4] = rs.getString("regdate"); // 날짜
				data[i][5] = Integer.toString(rs.getInt("hit")); // 조회수
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt, rs);
		}

	}

	// 레코드 한건 넣기!!(DML)
	public int insert(String title, String writer, String content) { // 사용자가 하는것이므로 매개변수로 뿌려줘야댐
		PreparedStatement pstmt = null;
		int result = 0; // 지역변수는 개발자가 초기화 해줘야 된다!!

		String sql = "insert into board(board_id,title,writer,content)";
		sql += " values(seq_board.nextval,'" + title + "','" + writer + "','" + content + "')"; // 한칸 띄워야함!! 계획만 세움

		System.out.println(sql);

		try {
			pstmt = boardMain.con.prepareStatement(sql);// 수행할 준비임

			// 반환값 int형의 의미: 이 DML에 의해 영향을 받은 레코드 수를 반환
			// 따라서 insert 의 경우 성공시 언제나 1, 실패는 0
			result = pstmt.executeUpdate(); // insert, update,delete용

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt);
		}
		return result;
	}

	// 레코드 1건 삭제하기
	// 이 메서드를 호출하는 사람은 board_id를 넘겨야 한다!!
	public int delete(int board_id) {
		PreparedStatement pstmt = null;
		String sql = "delete from board where board_id=" + board_id;
		int result = 0;

		try {
			pstmt = boardMain.con.prepareStatement(sql);
			result = pstmt.executeUpdate(); // DML 수행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt); // 자원해제
		}

		System.out.println(sql);
		return result;
	}

	// 한 건 수정
	public int update(String title, String writer, String content, int board_id) { // DML
		PreparedStatement pstmt = null;
		String sql = "update board set title='" + title + "',writer='" + writer + "',content='" + content + "'";
		sql += " where board_id=" + board_id; // 한칸띄고 시작

		int result = 0;
		try {
			pstmt = boardMain.con.prepareStatement(sql);
			result = pstmt.executeUpdate();// 만약 0이 나온다면 오류가 아니라 값이 안 들어간것이다.
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt);
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

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public String getColumnName(int col) {
		return column[col];
	}
}
=======
package boardmodel;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//오라클의 Board 테이블에 관한 정보를 가진 모델 객체
public class BoardModel extends AbstractTableModel {
	BoardMain boardMain;
	String[] column = { "board_id", "title", "writer", "content", "regdate", "hit", };
	String[][] data;

	public BoardModel(BoardMain boardMain) { // 생성자를 안써서 아까우니까 생성해주면서 매개변수로 받는다.
		this.boardMain = boardMain;
		select();
	}

	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board order by board_id desc";

		try {
			pstmt = boardMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리
																														// 객체
																														// 생성
			rs = pstmt.executeQuery();

			rs.last();
			int total = rs.getRow(); // 총 몇건인지 반환!!

			// rs는 곧 죽으므로, rs를 대신하여 데이터를 담게 될 이차원배열생성
			data = new String[total][column.length];
			rs.beforeFirst();// 커서위치 원상복귀!!

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("board_id"));
				data[i][1] = rs.getString("title"); // 제목
				data[i][2] = rs.getString("writer"); // 작성자
				data[i][3] = rs.getString("content"); // 내용
				data[i][4] = rs.getString("regdate"); // 날짜
				data[i][5] = Integer.toString(rs.getInt("hit")); // 조회수
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt, rs);
		}

	}

	// 레코드 한건 넣기!!(DML)
	public int insert(String title, String writer, String content) { // 사용자가 하는것이므로 매개변수로 뿌려줘야댐
		PreparedStatement pstmt = null;
		int result = 0; // 지역변수는 개발자가 초기화 해줘야 된다!!

		String sql = "insert into board(board_id,title,writer,content)";
		sql += " values(seq_board.nextval,'" + title + "','" + writer + "','" + content + "')"; // 한칸 띄워야함!! 계획만 세움

		System.out.println(sql);

		try {
			pstmt = boardMain.con.prepareStatement(sql);// 수행할 준비임

			// 반환값 int형의 의미: 이 DML에 의해 영향을 받은 레코드 수를 반환
			// 따라서 insert 의 경우 성공시 언제나 1, 실패는 0
			result = pstmt.executeUpdate(); // insert, update,delete용

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt);
		}
		return result;
	}

	// 레코드 1건 삭제하기
	// 이 메서드를 호출하는 사람은 board_id를 넘겨야 한다!!
	public int delete(int board_id) {
		PreparedStatement pstmt = null;
		String sql = "delete from board where board_id=" + board_id;
		int result = 0;

		try {
			pstmt = boardMain.con.prepareStatement(sql);
			result = pstmt.executeUpdate(); // DML 수행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt); // 자원해제
		}

		System.out.println(sql);
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

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public String getColumnName(int col) {
		return column[col];
	}
}
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
