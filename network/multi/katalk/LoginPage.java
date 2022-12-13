package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPage extends Page {
	JLabel la_title;
	JTextField t_id;
	JTextField t_pass;
	JButton bt_login;
	JButton bt_join;

	public LoginPage(ClientMain clientMain) {
		super(clientMain);

		la_title = new JLabel("KaKaoTalk");
		t_id = new JTextField();
		t_pass = new JTextField();
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");

		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Verdana", Font.BOLD, 45));
		la_title.setHorizontalAlignment(SwingConstants.CENTER); // setHorizontalAlignment 가운데 정렬

		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);

		add(la_title);
		add(t_id);
		add(t_pass);

		add(bt_login);
		add(bt_join);

		setBackground(Color.YELLOW);

		// 회원가입 버튼과 리스너 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientMain.showHide(ClientMain.JOINPAGE); // 클라이언트 메인꺼임 page 클래스에 매겨변수 각 페이지에 super호출
			}
		});
	}
}
