package page;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Menu extends JLabel {
	/*
	 * int index;
	 * public Menu(Icon icon,int index) { super(icon);//JLable 생성자에 (icon)넣어준것 =>
	 * JLabel j = new JLabel(icon); => JLabel이 생성될때 icon을 붙인 라벨이 생성
	 * this.index=index;
	 */
	int index;
	
	//AppMain을 제어할 것이기 때문에 주소값을 받아오려고 선언함
	AppMain appMain;
	
	public Menu(AppMain appMain,ImageIcon icon, int width, int height, int index) {
		this.appMain=appMain;
		this.index=index;
		this.setIcon(icon); //라벨에 사용할 아이콘
		
		//라벨의 크기
		//this.setPreferredSize(new Dimension(width,height));
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("저는"+index+"메뉴예요");
				
				appMain.showHide(index);
			}
		});
	}

}
