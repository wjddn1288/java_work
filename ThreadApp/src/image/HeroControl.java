package image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

//자바에서 이미지를 얻는 방법은 번거로운 Toolkit만 있는것이 아니다!!
//사용중인 OS 외 디렉토리를 명시하는 것보다, 클래스패스를 통한 자원접근이 더 자바스럽고,
//플랫폼에 중립적이다!! 따라서 자원들도 package에 담을수있다!!
public class HeroControl extends JFrame {
	JPanel p;
	Image image;
	ImageManager imageManager;
	// 패키지에는 꼭 클래스만 넣을 수 있는게 아니다.!!!
	// 만일 패키지에 넣은 파일이 클래스인 경우 원래대로 패키지와 패키지구분을 .점을 찍어서 구분하고,
	// 만일 패키지에 넣은 파일이 클래스 이외의 파일인 경우, 클래스가 아니므로 디렉토리 취급한다.
	// 따라서, .점이 /로 구분해야 한다...
	String[] imgName = { "res/hero/image1.png", "res/hero/image2.png", "res/hero/image3.png", "res/hero/image4.png",
			"res/hero/image5.png", "res/hero/image6.png", "res/hero/image7.png", "res/hero/image8.png",
			"res/hero/image9.png", "res/hero/image10.png", "res/hero/image11.png", "res/hero/image12.png",
			"res/hero/image13.png", "res/hero/image14.png", "res/hero/image15.png", "res/hero/image16.png",
			"res/hero/image17.png", "res/hero/image18.png" };
	Image[] images; // 이미지 배열 준비하기!!
	int index; // 이미지 배열의 몇번째를 보여줄지를 결정짓는 index
	Thread thread; // 게임 쓰레드!!

	public HeroControl() {
		/*
		 * 클래스에 대한 정보를 가진 클래스!! Class Class Class myClass=this.getClass(); Method[]
		 * methods=myClass.getMethods(); for(Method m:methods) {
		 * System.out.println(m.getName()); }
		 */

		imageManager = new ImageManager();
		images = imageManager.createImages(imgName);

		p = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 800, 600);
				
				g2.drawImage(images[index], 50, 50, 200, 200, HeroControl.this); // 히어로클래스의 this이다.
				if(index>= images.length-1) {
					index=0;
				}
			}
		};

		thread = new Thread() {
			public void run() {
				while(true) {
					gameLoop(); //게임루프 호출 
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		add(p);

		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면중앙에 프레임놓기
	}
	public void tick() {
		index++;
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		tick();
		render();
	}
	
	public static void main(String[] args) {
		new HeroControl();
	}
}
