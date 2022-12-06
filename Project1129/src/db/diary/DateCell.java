package db.diary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//날짜에 사용하기 위한 셀
public class DateCell extends Cell {
	Color color = Color.WHITE;
	DiaryMain diaryMain; // 왼쪽에 콤보박스를 제어하려고

	public DateCell(DiaryMain diaryMain, String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
		this.diaryMain = diaryMain;

		Border border = new LineBorder(Color.DARK_GRAY);
		setBorder(border);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (color == Color.WHITE) {
					color = Color.YELLOW;
					//콤보박스에 날짜채워넣기!
					diaryMain.setDateInfo(DateCell.this.title);
				} else {
					color = Color.white;
				}
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 120, 120);
		// 배경색 적용하기
		g2.setColor(color);
		g2.fillRect(0, 0, 100, 100);

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(title, x, y); // 날짜 그리기
		g2.drawString(content, x - 30, y + 20); // 내용 그리기

	}
}
