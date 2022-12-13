package network.multi.katalk;

import java.net.http.WebSocket.Listener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import network.domain.ChatMember;
import network.util.DBManager;

public class MysqlChatMemberDAO implements ChatMemberDAO {

	public int insert(ChatMember chatMember) {
		return 0;
	}

	DBManager dbManager = DBManager.getInstance(); // 싱글턴

	public int update(ChatMember chatMember) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into chatmember(chatmember_idx, id, pass, name values(?, ?, ?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, chatMember.getId());
			pstmt.setString(2, chatMember.getPass());
			pstmt.setString(3, chatMember.getName());
			result = pstmt.executeUpdate(); // 쿼리실행

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int delete(int chatmember_idx) {
		return 0;
	}

	public Listener selectAll() {
		return null;
	}

	public ChatMember select(int chatmember_idx) {
		return null;
	}

}
