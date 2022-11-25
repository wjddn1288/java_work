/*
 * package basic;
 * 
 * import javax.swing.JButton; import javax.swing.JFrame; import
 * javax.swing.JPanel;
 * 
 * public class SoftMove extends JFrame { JButton bt; JPanel p_north; JPanel
 * p_center; int x; int y;
 * 
 * public void SoftMove() { bt= new JButton("움직이기"); p_north= new JPanel();
 * p_center= new JPanel() { protected void paintComponent(Graphics g) {
 * Graphics2D g2= (Graphics2D) g;o g2.clearRect(0, 0, 600, 600);
 * g2.setColor(Color.RED); g2.fillOval(x,y,30,30); } };
 * 
 * p_north.add(bt); p_center.setBackground(Color.YELLOW);
 * add(p_north,BorderLayout.NORTH);
 * 
 * setSize(600,600); setVisible(true); setDefaultCloseOperation(EXIT_ON_CLOSE);
 * 
 * bt.addActionListener(new ActionListener(){ public void
 * actionPerformed(ActionEvent e) { move(); } });
 * 
 * public void move() {
 * 
 * } public static void main(String[]args) { new SoftMove(); } }
 */

package basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoftMove extends JFrame {
	JButton bt;
	JPanel p;
	Thread thread; // 게임 루프용 쓰레드
	double x;
	double y=50;
	double a=0.08;
	int targetX; //목적지
	int targetY;

	public SoftMove() {
		bt = new JButton("이동");
		p = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2= (Graphics2D)g;

				g2.setColor(Color.YELLOW); //색 선택
				g2.fillRect(0,0,600,600); //지우고 새로 깔때 나오는 배경색/fill 붙은건 setColor 붙는다고 생각하면 편한다!

				g2.setColor(Color.RED); //빨간색으로 변경
				g2.fillOval((int)x,(int)y, 20, 20);
			}
		};
		
		thread= new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						Thread.sleep(10); //Non-Runnable 영역으로 지정한 시간동안 머물다가,다시 Runnable 영역으로 기어올라오게함
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			}
		};
		thread.start(); //Runnable..

		p.setPreferredSize(new Dimension(600, 550));

		setLayout(new FlowLayout());
		add(bt);
		add(p);

		setSize(600,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//마우스 리스너와 연결
		//이벤트의 리스너의 재정할 메서드가 3개이상 될 경우, 어댑터 존재여부를 확인할 필요가 있다.
		p.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				targetX=e.getPoint().x;
				targetY=e.getPoint().y;
			}
		});
	}
	//물리량 변화코드
	public void tick() {
		//나의위치= 현재나의위치 +a*(목적지 - 현재나의위치)
		x= x+a*(targetX-x);
		y= y+a*(targetY-y);
	}
	
	public void render() {
		p.repaint(); //다시 그림
	}
	
	public void gameLoop() {
		System.out.println("gameLoop() called...");
		tick();
		render();
	}
	
	public static void main(String[] args) {
		new SoftMove();
	}

}
