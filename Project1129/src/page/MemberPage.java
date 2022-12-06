package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.MailSender;
import util.StringUtil;

//회원가입의 내용을 보여줄 페이지
public class MemberPage extends JPanel {
	JPanel p_form;// 입력 양식을 묶을 패널
	JLabel la_id, la_pass, la_email;
	JTextField t_id;
	JTextField t_email;
	JPasswordField t_pass;
	JButton bt_regist;
	AppMain appMain;
	MailSender mailSender;

	public MemberPage(AppMain appMain) {
		this.appMain = appMain;
		mailSender = new MailSender();
		
		setBackground(Color.YELLOW);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));
		p_form = new JPanel();
		la_id = new JLabel("ID");
		la_pass = new JLabel("Pass");
		la_email = new JLabel("Email");
		t_id = new JTextField();
		t_email = new JTextField();
		t_pass = new JPasswordField();
		bt_regist = new JButton("회원가입");

		p_form.setPreferredSize(new Dimension(500, 160));
		Dimension d = new Dimension(230, 25);

		la_id.setPreferredSize(d);
		t_id.setPreferredSize(d);
		t_email.setPreferredSize(d);
		la_pass.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		la_email.setPreferredSize(d);

		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_pass);
		p_form.add(t_pass);
		p_form.add(la_email);
		p_form.add(t_email);
		p_form.add(bt_regist);

		add(p_form);

		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result=regist();
				if(result>0) {
					JOptionPane.showMessageDialog(MemberPage.this, "등록성공");
					//이메일 발송 !!!(유저가 존재하는 메일을 넣었야 함)
					mailSender.sendMail(t_email.getText());
				}
			}
		});
	}

	public int regist() {
		PreparedStatement pstmt = null;

		// ? 바인드 변수 처리하고 싶을때 사용하는 기호
		String sql = "insert into member(member_idx,id,pass,email)";
		sql += " values(seq_member.nextval,?,?,?)";
		int result = 0;
		try {
			pstmt = appMain.con.prepareStatement(sql);
			// 물음표의 값을 먼저 지정해 준 후 쿼리를 수행해야 한다
			// 바인드 변수의 순번은 1부터 시작한다.
			pstmt.setString(1, t_id.getText());// id
			pstmt.setString(2, StringUtil.getConertedpaseeword(new String(t_pass.getPassword())));// pass
			pstmt.setString(3, t_email.getText());// email
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			appMain.release(pstmt);
		}
		return result;
	}
}
