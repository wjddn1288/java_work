package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//JButton 현재 우리의 상황에 맞이 않기 때문에, 우리가 원하는 버튼으로 그림을 재정의하기 위함!!
public class CustomButton extends JButton {
	int width;
	int height;
	int index; // 내가 몇번째 버튼인지 태어날때부터 알수있는 기준값
	ShurekGallery shurekGallery; //얻어오는건 매개변수 밖에 없다.

	public CustomButton(ShurekGallery shurekGallery, int index, int width, int height) {
		this.shurekGallery=shurekGallery; //기존 슈렉겔러리를 가져오기 
		this.width = width; // 매개변수로 만들어 놓고 다른곳에서 쓸려고!!
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));

		// 버튼에 리스너 연결
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shurekGallery.targetX=index*-500; 
				System.out.println((index*-500)+"부터 그릴꺼야!");
			}
		});
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.RED);
		g.fillOval(0, 0, width, height);
	}
} 
