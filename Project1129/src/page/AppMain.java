package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame {
	JPanel p_north, p_center; // 네비게이션 버튼을 올려놓을 북쪽 패널 ,
	Menu[] menu = new Menu[4]; // 공간확보

	ImageManager imageManager; // 다른 패키지에 있는거 불러오기 임포트 하기

	// 규칙을 만들면 활용도가 높으므로, 모든 페이지들을 상위 자료형인 JPanel로 묶자
	JPanel[] pageArr = new JPanel[4];

	// 모든 페이지의 너비와 높이의 기준을 정한 상수 정의
	public static final int PAGE_WIDTH = 800;
	public static final int PAGE_HEIGHT = 500;

	// DB관련 정보
	FileInputStream fis; // 파일을 대상으로 한 입력스트림
	Properties props; // Map의 손자다!! key-value
	String driver;
	String url2;
	String user;
	String pass;
	Connection con;
	boolean loginFlag; // false 가 디폴트임(최조엔 로그인 안했으므로)

	public AppMain() {
		loadInfo();
		connect();

		p_north = new JPanel();
		p_north.setBackground(Color.YELLOW); // 1
		imageManager = new ImageManager();

		String[] path = { "res/app/company.png", "res/app/login.png", "res/app/member.png", "res/app/notice.png" };

		// 우리가 사용할 페이지들이 공존할수 있도록,FlowLayou을 적용할 영역때문에.. 프레임의 센터에 부착될것임!!(대왕만하게)

		// 아이콘이 있는 버튼들을 만들자 5
		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Menu(this, imageManager.getIcon(path[i], 80, 60), 60, 50, i);
			p_north.add(menu[i]);

			// 내부익명클래스는 외부클래스의 멤버변수는 맘대로 접근이 가능하지만
			// 메서드의 지역변수는 final로 선언된 변수를 읽기만 가능..
			/*
			 * menu[i].addMouseListener(new MouseAdapter() { public void
			 * mouseClicked(MouseEvent e) { System.out.println("나 누름?"); } });
			 */
		}

		// 센터에 붙을 패널 생성
		p_center = new JPanel();
		p_center.setBackground(Color.CYAN);

		// 4개의 페이지를 미리 화면에 생성하여 붙이기
		pageArr[0] = new CompanyPage();
		pageArr[1] = new MemberPage(this);
		pageArr[2] = new LoginPage(this);
		pageArr[3] = new NoticePage();

		// 생성된 페이지들을 센터 p_center 패널에 붙이자!!
		for (int i = 0; i < pageArr.length; i++) {
			p_center.add(pageArr[i]);
		}

		add(p_north, BorderLayout.NORTH); // 3
		add(p_center);

		setSize(800, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); // 2

		showHide(0);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});

	}

	// showHide 대신 쓸 메서드,, 페이지를 보여주기 전 로그인 완료 여부를 확인
	public void checkLogin(int currentPage) {
		// 회원가입과 로그인의 경우 , 검사하지 않는다.
		if (currentPage == 1 || currentPage == 2) {
			showHide(currentPage);
		} else { // 검증이 필요한 나머지 것들
			if (loginFlag == false) {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스 입니다");
			} else {
				showHide(currentPage);
			}
		}
	}

	// 페이지 보여주는 메서드; 메뉴를 눌렀을때, 해당 페이지 보여주기!
	public void showHide(int currentPage) {
		for (int i = 0; i < pageArr.length; i++) {
			if (currentPage == i) {
				pageArr[i].setVisible(true);
			} else {
				pageArr[i].setVisible(false);
			}
		}
	}

	public void loadInfo() {
		// fis=new
		// 우리가 스트림을 생성할 자원이 package경로에 있으므로,
		URL url = this.getClass().getClassLoader().getResource("res/db/db.properties");
		try {
			URI uri = url.toURI();
			File file = new File(uri);
			fis = new FileInputStream(file);
			props = new Properties(); // Map의 손자 생성!!
			props.load(fis); // 프로퍼티스와 fis 연계 //파일을 인식한 맵이 되는 순간임!!
			driver = props.getProperty("driver");
			url2 = props.getProperty("url");
			user = props.getProperty("user");
			pass = props.getProperty("pass");

			/*
			 * int data = -1; while (true) { data = fis.read(); if(data==-1)break;
			 * System.out.println((char)data); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void connect() {
		try {
			// 드라이버 로드
			Class.forName(driver);
			con = DriverManager.getConnection(url2, user, pass);
			if (con != null) {
				setTitle("접속성공");
			} else {
				setTitle("접속실패");
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
		new AppMain();
	}
}
