package gallery;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

//JButton 현재 우리의 상황에 맞이 않기 때문에, 우리가 원하는 버튼으로 그림을 재정의하기 위함!!
public class CustomButton extends JButton{
	int width;
	int height;
	
	public CustomButton(int width, int height) {
		this.width=width; //매개변수로 만들어 놓고 다른곳에서 쓸려고!!
		this.height=height;
		
		this.setPreferredSize(new Dimension(width,height));
		
		/*this.addActionListener(new actionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		}); */
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.RED);
		g.fillOval(0, 0, width, height);
	}
}
