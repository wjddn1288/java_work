package basic;

//쓰레드를 정의한다!!
//쓰레드 임포트 불필요! java.lang에 기본으로 경로가 이미 있음 
public class StarThread extends Thread {
	String star;

	public StarThread(String star) { // 이미 생성자를 만들어 놔서 안쓸 이유가 없음
		this.star = star;
	}

	// 개발자는 독립실행을 원하는 코드를 run()에 작성해놓기만 하면 된다.
	public void run() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + star);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
