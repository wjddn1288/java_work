package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
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

	public AppMain() {
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
		pageArr[1] = new MemberPage();
		pageArr[2] = new LoginPage();
		pageArr[3] = new NoticePage();

		// 생성된 페이지들을 센터 p_center 패널에 붙이자!!
		for (int i = 0; i < pageArr.length; i++) {
			p_center.add(pageArr[i]);
		}

		add(p_north, BorderLayout.NORTH); // 3
		add(p_center);

		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 2

		showHide(0);
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

	public static void main(String[] args) {
		new AppMain();
	}
}
