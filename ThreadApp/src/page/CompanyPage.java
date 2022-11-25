package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//회사소개의 내용을 보여줄 페이지
public class CompanyPage extends JPanel{
	public CompanyPage () {
		setBackground(Color.RED);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH,AppMain.PAGE_HEIGHT));
	}
}
