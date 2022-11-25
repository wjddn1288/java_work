package basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*지금까지 흔히 이야기해왔던 자바의 실해부는 사실 시스템에 의해 제공되는 메인쓰레드였다.. 
		즉 자바는 쓰레드기반 언어다. 메인쓰레드는 개발자가 정의하는 객체가 아니라서 동작에 제한이 있다.
		메인쓰레드(public main)의 목적은  프로그램의 운영에 있으므로, 절대 하지 말아야 할것
		1) 무한루프에 빠지게 해서는 안된다!!
		2) 대기상태에 빠지게 해서는 안된다!!
		특히 메인쓰레드는 GUI프로그램에서 이벤트를 감지하거나, 화면에 그래픽 처리를 담당하는 등의 
		역할을 수행하므로, 개발자가 정의한 쓰레드로 이벤트나 ,GUI처리를 하는것은 다른 언어에서는 금지임!
		ex) 아이폰, 안드로이드에서는 아예 에러사항이다!
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import util.StringUtil;

//초를 카운트하는 스탑워치 구현하기!!
public class StopWatch extends JFrame {
	JButton bt;
	JLabel la;
	Thread thread; // 내부익명클래스
	int sec; // 초를 증가시킬 인스턴스 변수
	int min; // 분을 증가시킬 인스턴스 변수
	boolean flag = false; // 스타워치의 동작 여부를 결정하는 논리값

	public StopWatch() {
		bt = new JButton("Start");
		la = new JLabel("00:00", SwingConstants.CENTER); // 센터,, 가운데로 오게 하기
		thread = new Thread() {
			public void run() {
				while (true) { // 언제나 무한루프
					printTime();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start(); // 쓰레드를 Runnable 상태로 진입!!

		la.setPreferredSize(new Dimension(480, 250));
		la.setFont(new Font("verdana", Font.BOLD, 120));

		setLayout(new FlowLayout());
		add(bt);
		add(la);
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 버튼과 리스너연결
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = !flag;
				// 버튼의 텍스트를 전환하기!!(Stop,Start 왔다갔다 하기)
				String title = (flag) ? "Stop" : "Start";
				bt.setText(title); // 삼항연산자로

			}
		});

	}

	public void printTime() {
		if (flag) {
			sec++;
			if (sec >= 60) {
				sec = 0;
				min++; // 분 증가
			}
			la.setText(StringUtil.getNumString(min) + ":" + StringUtil.getNumString(sec)); // 05
		}
	}

	public static void main(String[] args) {
		new StopWatch();
	}
}
