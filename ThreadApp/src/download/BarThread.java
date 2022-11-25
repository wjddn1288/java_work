package download;

import javax.swing.JProgressBar;

//하나의 프로그램 내에서 각각 틀린방식으로, 동작할 프로그래스바를 구한하기 위해 재사용 가능성이 예상되므로,
//별도의 .java로 정의함
public class BarThread extends Thread {
	JProgressBar jProgressBar;
	boolean flag = true; // 쓰레드 종료 여부를 결정짓는 변수
	int n;
	int velX; // 바의 증가속도를 결정짓는 변수
	int time; // 쓰레드의 sleep() 속도를 결정짓는 변수

	// 생성시, 이 바 쓰레드가 얼마의 속도로 진행할지 여부를 결정짓는 매개변수
	public BarThread(JProgressBar jProgressBar, int velX, int time) {
		this.jProgressBar = jProgressBar;
		this.velX = velX;
		this.time = time;
	}

	// 바에 데이터를 출력하는 메서드
	public void printValue() {
		n += velX;
		jProgressBar.setValue(n);
		if (n >= 100) {
			flag = false; // 쓰레드 사망!!
		}
	}

	// 바를 증가시키자!!
	public void run() {
		while (flag) {
			printValue();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public static void main(String[] args) {

	}
}
