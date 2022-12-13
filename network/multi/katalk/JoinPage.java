package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import network.domain.ChatMember;

public class JoinPage extends Page {
	JLabel la_title;
	JTextField t_id;
	JTextField t_pass;
	JTextField t_name;
	JButton bt_login;
	JButton bt_join;

	// 모든 DAO의 최상위 객체
	ChatMemberDAO chatMemberDAO;

	public JoinPage(ClientMain clientMain) {
		super(clientMain);
		chatMemberDAO = new OracleChatMemberDAO();// 나는 오라클 연동할래~ mysql연동하고 싶음 new 뒤만 바꿔주면 개꿀
		la_title = new JLabel("Sign Up");
		t_id = new JTextField();
		t_pass = new JTextField();
		t_name = new JTextField();

		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");

		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Verdana", Font.BOLD, 45));
		la_title.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬

		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);

		setPreferredSize(new Dimension(400, 500));

		add(la_title);
		add(t_id);
		add(t_pass);
		add(t_name);

		add(bt_login);
		add(bt_join);

		setBackground(Color.YELLOW);

		// 회원가입 버튼과 리스너 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
	}

	public void regist() {
		// 낱개로 넘기지 말고, DTO로 넘기자!!
		ChatMember chatMember = new ChatMember(); // empty 상태인 DTO
		chatMember.setId(t_id.getText());
		chatMember.setPass(t_pass.getText());
		chatMember.setName(t_name.getText());

		// ChatMemberDAO 인터페이스를 상속받은 객체들이므로,dao가 교체된다고 하여 아래의 메서드가 변경되는것이 아니다.
		// 따라서 유지보수성이 극대화 된다!! 유지보수는 돈이다!!
		int result = chatMemberDAO.insert(chatMember);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "가입 성공!!");
		}

	}

}
