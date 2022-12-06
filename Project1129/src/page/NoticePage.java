package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//공지사항의 내용을 보여줄 페이지
public class NoticePage extends JPanel {
	public NoticePage() {
		setBackground(Color.PINK);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH,AppMain.PAGE_HEIGHT));
	}
}
