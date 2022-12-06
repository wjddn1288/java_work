<<<<<<< HEAD
package boardmodel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class BoardMain2 extends JFrame implements ActionListener {
	JPanel p_north;
	JComboBox combo; // awt Choice
	JTextField t_keyword; // 검색어 입력 부분
	JButton bt_search; // 검색버튼

	JPanel p_west;
	JTextField t_title, t_writer;
	JTextArea t_content; // 내용
	JButton bt_regist; // 등록버튼

	JPanel p_east;
	JTextField t_title2, t_writer2;
	JTextArea t_content2;
	JButton bt_edit, bt_del;

	JTable table;
	JScrollPane scroll;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "javase";
	String pass = "1234";

	Connection con;
	TableModel model2;
	int selected_id;

	public BoardMain2() {
		connect();
		// 북쪽
		p_north = new JPanel();
		combo = new JComboBox();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");

		p_north.add(combo);
		p_north.add(t_keyword);
		p_north.add(bt_search);
		add(p_north, BorderLayout.NORTH);

		combo.addItem("제목");
		combo.addItem("작성자");
		combo.addItem("내용");

		// 서쪽
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

		// 센터
		table = new JTable();
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextArea();
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		p_east.setPreferredSize(new Dimension(150, 500));
		t_content2.setPreferredSize(new Dimension(140, 150));

		p_east.add(t_title2);
		p_east.add(t_writer);
		p_east.add(t_content2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});

		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				System.out.println("선택된 층수는" + row);
				
				BoardModel2 boardModel2=(BoardModel2)model2;
				String[] record=boardModel2.data[row];
				System.out.println(record[1]);
				
				getDetail(record);
			}
		});
	}

	public void connect() {
		// 1) 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2)접속
			try {
				con = DriverManager.getConnection(url, user, pass);
				if (con != null) {
					this.setTitle(user + "로접속성공");
				} else {
					this.setTitle("접속실패");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

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
	}

	public void regist() {
		BoardModel2 boardModel2 = (BoardModel2) model2;
		int result = boardModel2.insert(t_title.getText(), t_writer.getText(), t_content.getText());

		if (result > 0) {
			boardModel2.select(null, null);
			table.updateUI();
		}
	}

	public void getDetail(String[] record) {
		selected_id = Integer.parseInt(record[0]);
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content.setText(record[3]);

	}

	public void del() {
		if (JOptionPane.showConfirmDialog(this, "삭제하시겠어요?") == JOptionPane.OK_OPTION) {
			BoardModel2 boardModel2 = (BoardModel2) model2;
			int result = boardModel2.delete(selected_id);
			if (result != 0) {
				boardModel2.select(null, null);
				table.updateUI();
			}
		}
	}


	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_regist) {
			regist();
		} else if (obj == bt_search) {
			getSearchList();
		} else if (obj == bt_edit) {
			edit();
		} else if (obj == bt_del) {
			del();
		}
	}

	public void edit() {
		if (JOptionPane.showConfirmDialog(this, "삭제하시겠어요?") == JOptionPane.OK_OPTION) {
			BoardModel2 boardModel2 = (BoardModel2) model2;
			int result = boardModel2.update(t_title2.getText(), t_writer.getText(), t_content.getText(), selected_id);
			if (result > 0) {
				boardModel2.select(null, null);
				table.updateUI();
			}
		}
	}

	public void getSearchList() {
		BoardModel2 boardModel2 = (BoardModel2) model2;
		int index = combo.getSelectedIndex() + 1;
		String category = boardModel2.column[index];
		boardModel2.select(category, t_keyword.getText());
		table.updateUI();
	}

	public static void main(String[] args) {
		new BoardMain2();
	}

}
=======
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
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
