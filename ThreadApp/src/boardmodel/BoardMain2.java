package boardmodel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//응용프로그램 개발시, 디자인 영역과, 데이터 및 그 처리영역(업무로직)은
//서로 분리시켜 개발해야 좋다(유지보수성==시간==돈)
public class BoardMain2 extends JFrame {
	JPanel p_north;
	JTextField t_keyword; // 검색어 입력
	JButton bt_search; // 검색버튼

	JPanel p_west;
	JTextField t_title; // 제목
	JTextField t_writer; // 작성자
	JTextArea t_content; // 내용
	JButton bt_regist; // 등록버튼

	JPanel p_east;
	JTextField t_title2; // 제목
	JTextField t_writer2; // 작성자
	JTextArea t_content2; // 내용
	JButton bt_edit, bt_del; // 수정,삭제버튼

	JTable table;
	JScrollPane scroll;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "javase";
	String pass = "1234";
	Connection con; // 원할때 닫기 위해...

	public BoardMain2() {
		connect();
		
		// 북쪽영역
		p_north = new JPanel();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");
		p_north.add(t_keyword);
		p_north.add(bt_search);
		add(p_north, BorderLayout.NORTH);

		// 서쪽영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextArea();
		bt_regist = new JButton("등록");
		p_west.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(140, 150));

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);

		// 센터영역
		table = new JTable(10, 6);
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextArea();
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		p_east.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(140, 150));

		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);
	}

	public static void main(String[] args) {
		new BoardMain2();

	}
}
