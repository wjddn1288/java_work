package boardmodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class BoardModel2 extends AbstractTableModel {
	BoardMain2 boardMain2;
	String[] column = { "board_id", "title", "writer", "content", "regdate", "hit" };
	String[][] data;

	public BoardModel2(BoardMain2 boardMain2) {
		this.boardMain2 = boardMain2;
		select(null, null);
	}

	public void select(String category, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;
		if (category == null && keyword == null) {
			sql = "select * from board order by board_id desc";
		} else {
			sql = "select * from board where " + category + "like '%" + keyword + "%'";
		}
		System.out.println(sql);

		try {
			pstmt = boardMain2.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			data = new String[total][column.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("board_id"));
				data[i][1] = rs.getString("title");
				data[i][2] = rs.getString("writer");
				data[i][3] = rs.getString("content");
				data[i][4] = rs.getString("regdate");
				data[i][5] = Integer.toString(rs.getInt("hit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain2.release(pstmt, rs);
		}
	}

	public int insert(String title, String writer, String content) {
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "insert into board(board_id,title,writer,content)";
		sql += " values(seq_board.nextval,'" + title + "','" + writer + "','" + content + "')";
		System.out.println(sql);

		try {
			pstmt = boardMain2.con.prepareStatement(sql);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain2.release(pstmt);
		}
		return result;
	}

	public int delete(int board_id) {
		PreparedStatement pstmt = null;
		String sql = "delete from board where board_id=" + board_id;
		int result = 0;

		try {
			pstmt = boardMain2.con.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain2.release(pstmt);
		}
		System.out.println(sql);
		return result;
	}

	public int update(String title, String writer, String content, int board_id) {
		PreparedStatement pstmt = null;
		String sql = "update board set title='" + title + "'',writer=" + writer + "'',content" + content + "'";
		sql += " where board_id=" + board_id;
		int result = 0;

		try {
			pstmt = boardMain2.con.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain2.release(pstmt);
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
