package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//로그인의 내용을 보여줄 페이지
public class LoginPage extends JPanel {
	public LoginPage() {
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH,AppMain.PAGE_HEIGHT));
	}
}
