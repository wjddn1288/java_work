package network.multi.katalk;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMain extends JFrame {
	JPanel container;// 전환될 페이지들을 안고 있을 최상위 패널

	// 프로그램에서 사용할 페이지들 생성
	// 0은 직관적이지 않음으로 상수로 선엄함
	Page[] page = new Page[3];
	public static final int LOGINPAGE = 0;
	public static final int JOINPAGE = 1;
	public static final int CHATPAGE = 2;

	public ClientMain() {
		container = new JPanel();
		page[0] = new LoginPage(this);
		page[1] = new JoinPage(this);
		page[2] = new ChatPage(this);

		for (int i = 0; i < page.length; i++) {
			container.add(page[i]); // 루트 컨테이너에 페이지들 부착@@
		}

		add(container);

		// 원하는 페이지 번호
		showHide(LOGINPAGE); // 0은 직관적이지 않음으로 상수로 선엄함

		setSize(400, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// 보여주고 싶은 페이지 넘버를 넘기면 된다!!
	public void showHide(int n) {
		for (int i = 0; i < page.length; i++) {
			if (n == i) {
				page[i].setVisible(true);
			} else {
				page[i].setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		new ClientMain();
	}
}
