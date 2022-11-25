package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//회원가입의 내용을 보여줄 페이지
public class MemberPage extends JPanel {
	public MemberPage() {
		setBackground(Color.YELLOW);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH,AppMain.PAGE_HEIGHT));
	}
}
