package basic;

public class StopWatchThread extends Thread {
	int sec; // 멤버변수라 0으로 자동초기화

	public void run() {
		sec++;
	}
}
