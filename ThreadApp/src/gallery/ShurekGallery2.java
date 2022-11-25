package gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShurekGallery2 extends JFrame {
	// 버튼 7개 생성예정(우리가 커스텀한 버튼..) new를7번해야되서 .java로 가야됨
	ArrayList<CustomButton> btnList;
	JPanel p;
	JPanel p_south; // 버튼 7개가 올려질 남쪽 패널
	ArrayList<Image> imageList = new ArrayList<Image>(); // size 0

	// 제너릭 컴파일 시점부터 미리 정하는 것

	public ShurekGallery2() {
		super("슈렉겔러리");
		btnList = new ArrayList<CustomButton>(); // size 0
		// 버튼생성
		for (int i = 0; i < 7; i++) {
			// List는 자바스크립트의 배열과 거의 동일!!(크기 유동적 측면)
			btnList.add(new CustomButton(30, 30));
		}
		
		//그림을 그리기 전에 미리, 이미지를 준비하자!!
		createImage();
		
		p = new JPanel() {
			// paintComponent() 재정의 예정
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(imageList.get(0),0,0,500,340,ShurekGallery2.this);
			}
		};
		p.setPreferredSize(new Dimension(500, 340));
		p.setBackground(Color.YELLOW);

		p_south = new JPanel();
		p_south.setPreferredSize(new Dimension(500, 60));
		p_south.setBackground(Color.GREEN);

		/*
		 * 우리가 사용학도 있는 모든 GUI컴포넌트는 다음과 같이 2가지 유형으로 분류된다. *GUI 컴포넌트란? : 사용자 입력을 받는 모든
		 * 컨트롤... 버튼,체크박스, 텍스트박스....
		 * 
		 * 1)컨테이너류 : 남을 포함할 수 있는 자(者) Frame,Panel.. 컨테이너는 남을 포함해야 하기 때문에 배치관리자가 지원된다...
		 * 
		 * 2) 비주얼컴포넌트 : 컨테이너에게 포함되는 자(者) Button, TextField...
		 */

		// 남쪽 패널에 버튼 7개 넣기!!
		// p_south는 디폴트 레이아웃이 FlowLayout으로 적용되어 있음

		// 개선된 for문은 Collection 형에 최적화되어 있기 때문에 내부적으로
		// 반복 횟수도 알고 있다.
		for (CustomButton bt : btnList) { // 향상된 포문
			p_south.add(bt); // 패널에 부착!!
		}

		// 조립
		add(p);
		add(p_south, BorderLayout.SOUTH);

		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// 클래스 패스에 존재하는 이미지들을 대상으로, 이미지객체 생성하기!!
	public void createImage() {
		Class myClass = this.getClass(); // 클래스에 대해 정보추출!!
		for (int i = 0; i<btnList.size(); i++) {
			URL url = myClass.getClassLoader().getResource("res/shurek/img" + i + ".jpg");
			// BufferedImage도 Image의 자식이므로 Image형이다.
			try {
				Image image = ImageIO.read(url);
				imageList.add(image); // 리스트에 추가!!
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("생성된 이미지는 총" + imageList.size() + "개");
	}

	public static void main(String[] args) {
		new ShurekGallery2();
	}
}
