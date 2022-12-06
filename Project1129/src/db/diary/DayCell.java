package db.diary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

//요일을 처리하기 위한 셀 
public class DayCell extends Cell {

	// DayCell 생성시 요일을 넘겨받아 보관해 놓자!!
	// paintComponent 메서드가 그려야 하므로, 변수는 멤버변수로 존재해야한다..

	public DayCell(String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.CYAN);
		g2.fillRect(0, 0, 100, 80);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Verdana", Font.BOLD, fontSize));
		g2.drawString(title, x, y);

	}
}
