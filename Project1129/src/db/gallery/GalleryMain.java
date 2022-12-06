<<<<<<< HEAD
package db.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.StringUtil;

public class GalleryMain extends JFrame implements ActionListener {
	// 서쪽 영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextArea t_content;
	JPanel p_sign; // 싸인처리 할 패널..
	JPanel p_preview; // 이미지 미리보기 패널
	JButton bt_open; // 첨부 이미지 찾기 버튼
	JButton bt_regist;

	// 센터영역
	JTable table;
	JScrollPane scroll;

	// 동쪽 영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextArea t_content2;
	JPanel p_preview2; // 이미지 미리보기 패널
	JButton bt_edit, bt_del;

	JFileChooser chooser;
	Image image; // 패널이 그릴수 있도록 멤버변수로 선언함
	Image detailImage; // 우측 패널에서 그려질 이미지
	File file; // 유저가 탐색기를 통해 선택한 바로 그 파일
	String dir = "C://java_workspace2//data//project1129//iamges";
	String filename; // 개발자가 새롭게 정의한 파일명

	// DB관련
	Connection con;
	GalleryModel model;
	ArrayList<int[]> history = new ArrayList<int[]>();
	SignModel signModel;

	public GalleryMain() {
		connect();

		signModel = new SignModel(this);

		// 서쪽 영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextArea();
		p_sign = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) (g);
				g2.clearRect(0, 0, 140, 70);
				g2.setColor(Color.RED);

				for (int i = 0; i < history.size(); i++) {
					int[] dot = history.get(i); // 하나의 점을 꺼낸다
					g2.fillOval(dot[0], dot[1], 3, 3);
				}
			}
		};

		p_preview = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.YELLOW);
				g2.fillRect(0, 0, 150, 130);
				g2.setColor(Color.BLUE);
				g2.drawString("choose your file", 20, 50);

				if (image != null) {
					g2.drawImage(image, 0, 0, 150, 130, GalleryMain.this);
				}
			}
		};

		bt_open = new JButton("첨부");
		bt_regist = new JButton("등록");
		chooser = new JFileChooser();

		p_west.setPreferredSize(new Dimension(150, 500));
		p_sign.setPreferredSize(new Dimension(140, 70));
		t_content.setPreferredSize(new Dimension(140, 150));
		p_preview.setPreferredSize(new Dimension(140, 150));
		p_preview.setBackground(Color.GREEN);

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_sign);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);

		// 센터영역
		table = new JTable(model = new GalleryModel(this));
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextArea();
		p_preview2 = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLUE);
				g2.drawString("choose your file", 20, 50);
				g2.fillRect(0, 0, 150, 130);
				g2.drawImage(detailImage, 0, 0, 150, 130, GalleryMain.this);

			}
		};
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		p_east.setPreferredSize(new Dimension(150, 500));
		t_content2.setPreferredSize(new Dimension(140, 150));
		p_preview2.setPreferredSize(new Dimension(140, 150));

		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(p_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

		// 윈도우와 리스너 연결
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});

		// 테이블과 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				table.setSelectionBackground(Color.YELLOW); // 선택한 줄 노랑색으로 나오게 하기!

				// 일차원 배열 추출하기
				int row = table.getSelectedRow(); // 유저가 선택한 row
				String[] record = model.data[row];// 1차 함수 추출 /하나의 게시물 추출

				getDetail(record); // 상세보기 구현
			}
		});

		// p_sign과 마우스 리스너 연결
		p_sign.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				System.out.println("드래그중");
				int x = e.getX();
				int y = e.getY();
				// p_sign.repaint();
				System.out.println("x=" + x + ",y=" + y);
				int[] dot = { x, y }; // 한 점을 표현할 배열
				history.add(dot); // 리스트에 추가!! 흔적을 남기기 위해

				p_sign.repaint();
			}
		});
	}

	public void openFile() {
		// 파일 탐색기를 띄우고, 사용자가 선택한 이미지 파일을 미리보게하자
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// 유저가 선택한 파일 반환받기
			file = chooser.getSelectedFile();
			System.out.println("당신이 선택한 파일은" + file.getName());
			try {
				image = ImageIO.read(file);
				p_preview.repaint();

				// 이미지명에 사용할 현재시간 (밀리세컨드까지...)
				long time = System.currentTimeMillis();
				filename = time + "." + StringUtil.getExtend(file.getName());
				System.out.println(filename);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copy() {
		// 이미지 복사+ 오라클에 등록
		FileInputStream fis = null; // 파일을 대상으로 한 입력스트림
		FileOutputStream fos = null; // 파일을 대상으로 한 출력스트림

		try {
			fis = new FileInputStream(file);

			// empty 상태 빈파일 생성
			fos = new FileOutputStream(dir + "/" + filename);

			int data = -1; // 읽혀지지 않았다는 초기값

			byte[] buff = new byte[1024]; // 등록속도가 1바이트씩 읽혀져 빠르게 읽어보자!

			while (true) {
				data = fis.read(buff); // 데이터는 지표다!
				if (data == -1)
					break;
				// break문 아래쪽은 break문을 만나지 않은 유효한 영역이다.
				fos.write(buff); // 출력
				System.out.println(data); // 읽혀진 데이터
			}
			System.out.println("복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void regist() {
		copy(); // 이미지복사
		int result = model.insert(t_title.getText(), t_writer.getText(), t_content.getText(), filename);// 오라클 등록

		// 싸인 정보 입력
		for (int i = 0; i < history.size(); i++) {
			int[] dot = history.get(i);
			signModel.insert(dot[0], dot[1], result);
		}

		if (result > 0) {
			model.selectAll();
			table.updateUI();
		}
	}

	public void getDetail(String[] record) {
		// 동쪽 영역에 사용자가 선택한 레코드 1건을 출력한다!!
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);

		// 이미지 처리
		File file = new File(dir + "/" + record[4]);
		try {
			detailImage = ImageIO.read(file);
			p_preview2.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == bt_open) {
			openFile();
		} else if (obj == bt_regist) {
			regist();
		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {

		}
	}

	public void connect() {
		try {
			// 1.드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.접속
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "javase";
			String pass = "1234";

			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				this.setTitle("오라클 접속 성공됨");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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

	// DML인 경우 사용
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// select 인 경우
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new GalleryMain();
	}
}
=======
package db.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.StringUtil;

public class GalleryMain extends JFrame implements ActionListener {
	// 서쪽 영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextArea t_content;
	JPanel p_sign; // 싸인처리 할 패널..
	JPanel p_preview; // 이미지 미리보기 패널
	JButton bt_open; // 첨부 이미지 찾기 버튼
	JButton bt_regist;

	// 센터영역
	JTable table;
	JScrollPane scroll;

	// 동쪽 영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextArea t_content2;
	JPanel p_preview2; // 이미지 미리보기 패널
	JButton bt_edit, bt_del;

	JFileChooser chooser;
	Image image; // 패널이 그릴수 있도록 멤버변수로 선언함
	Image detailImage; // 우측 패널에서 그려질 이미지
	File file; // 유저가 탐색기를 통해 선택한 바로 그 파일
	String dir = "C://java_workspace2//data//project1129//iamges";
	String filename; // 개발자가 새롭게 정의한 파일명

	// DB관련
	Connection con;
	GalleryModel model;
	ArrayList<int[]> history = new ArrayList<int[]>();
	SignModel signModel;

	public GalleryMain() {
		connect();

		signModel = new SignModel(this);

		// 서쪽 영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextArea();
		p_sign = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) (g);
				g2.clearRect(0, 0, 140, 70);
				g2.setColor(Color.RED);

				for (int i = 0; i < history.size(); i++) {
					int[] dot = history.get(i); // 하나의 점을 꺼낸다
					g2.fillOval(dot[0], dot[1], 3, 3);
				}
			}
		};

		p_preview = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.YELLOW);
				g2.fillRect(0, 0, 150, 130);
				g2.setColor(Color.BLUE);
				g2.drawString("choose your file", 20, 50);

				if (image != null) {
					g2.drawImage(image, 0, 0, 150, 130, GalleryMain.this);
				}
			}
		};

		bt_open = new JButton("첨부");
		bt_regist = new JButton("등록");
		chooser = new JFileChooser();

		p_west.setPreferredSize(new Dimension(150, 500));
		p_sign.setPreferredSize(new Dimension(140, 70));
		t_content.setPreferredSize(new Dimension(140, 150));
		p_preview.setPreferredSize(new Dimension(140, 150));
		p_preview.setBackground(Color.GREEN);

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_sign);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);

		// 센터영역
		table = new JTable(model = new GalleryModel(this));
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextArea();
		p_preview2 = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLUE);
				g2.drawString("choose your file", 20, 50);
				g2.fillRect(0, 0, 150, 130);
				g2.drawImage(detailImage, 0, 0, 150, 130, GalleryMain.this);

			}
		};
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		p_east.setPreferredSize(new Dimension(150, 500));
		t_content2.setPreferredSize(new Dimension(140, 150));
		p_preview2.setPreferredSize(new Dimension(140, 150));

		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(p_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

		// 윈도우와 리스너 연결
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});

		// 테이블과 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				table.setSelectionBackground(Color.YELLOW); // 선택한 줄 노랑색으로 나오게 하기!

				// 일차원 배열 추출하기
				int row = table.getSelectedRow(); // 유저가 선택한 row
				String[] record = model.data[row];// 1차 함수 추출 /하나의 게시물 추출

				getDetail(record); // 상세보기 구현
			}
		});

		// p_sign과 마우스 리스너 연결
		p_sign.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				System.out.println("드래그중");
				int x = e.getX();
				int y = e.getY();
				// p_sign.repaint();
				System.out.println("x=" + x + ",y=" + y);
				int[] dot = { x, y }; // 한 점을 표현할 배열
				history.add(dot); // 리스트에 추가!! 흔적을 남기기 위해

				p_sign.repaint();
			}
		});
	}

	public void openFile() {
		// 파일 탐색기를 띄우고, 사용자가 선택한 이미지 파일을 미리보게하자
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// 유저가 선택한 파일 반환받기
			file = chooser.getSelectedFile();
			System.out.println("당신이 선택한 파일은" + file.getName());
			try {
				image = ImageIO.read(file);
				p_preview.repaint();

				// 이미지명에 사용할 현재시간 (밀리세컨드까지...)
				long time = System.currentTimeMillis();
				filename = time + "." + StringUtil.getExtend(file.getName());
				System.out.println(filename);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copy() {
		// 이미지 복사+ 오라클에 등록
		FileInputStream fis = null; // 파일을 대상으로 한 입력스트림
		FileOutputStream fos = null; // 파일을 대상으로 한 출력스트림

		try {
			fis = new FileInputStream(file);

			// empty 상태 빈파일 생성
			fos = new FileOutputStream(dir + "/" + filename);

			int data = -1; // 읽혀지지 않았다는 초기값

			byte[] buff = new byte[1024]; // 등록속도가 1바이트씩 읽혀져 빠르게 읽어보자!

			while (true) {
				data = fis.read(buff); // 데이터는 지표다!
				if (data == -1)
					break;
				// break문 아래쪽은 break문을 만나지 않은 유효한 영역이다.
				fos.write(buff); // 출력
				System.out.println(data); // 읽혀진 데이터
			}
			System.out.println("복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void regist() {
		copy(); // 이미지복사
		int result = model.insert(t_title.getText(), t_writer.getText(), t_content.getText(), filename);// 오라클 등록

		// 싸인 정보 입력
		for (int i = 0; i < history.size(); i++) {
			int[] dot = history.get(i);
			signModel.insert(dot[0], dot[1], result);
		}

		if (result > 0) {
			model.selectAll();
			table.updateUI();
		}
	}

	public void getDetail(String[] record) {
		// 동쪽 영역에 사용자가 선택한 레코드 1건을 출력한다!!
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);

		// 이미지 처리
		File file = new File(dir + "/" + record[4]);
		try {
			detailImage = ImageIO.read(file);
			p_preview2.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == bt_open) {
			openFile();
		} else if (obj == bt_regist) {
			regist();
		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {

		}
	}

	public void connect() {
		try {
			// 1.드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.접속
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "javase";
			String pass = "1234";

			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				this.setTitle("오라클 접속 성공됨");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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

	// DML인 경우 사용
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// select 인 경우
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new GalleryMain();
	}
}
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
