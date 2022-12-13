package network.multi.katalk;

import java.net.http.WebSocket.Listener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import network.domain.ChatMember;
import network.util.DBManager;

//오라클의 chatmember 테이블에 대한 CRUD 전담객체
public class OracleChatMemberDAO implements ChatMemberDAO {

	DBManager dbManager = DBManager.getInstance(); // 싱글턴이다!!

	public int insert(ChatMember chatMember) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into chatmember(chatmember_idx, id, pass, name)";
		sql += " values(seq_chatmember.nextval, ?, ?, ?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, chatMember.getId()); // 바인드 변수
			pstmt.setString(2, chatMember.getPass());
			pstmt.setString(3, chatMember.getName());
			result = pstmt.executeUpdate(); // 쿼리실행

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int update(ChatMember chatMember) {
		return 0;
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
