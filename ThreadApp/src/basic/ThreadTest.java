/*쓰레드(Thread)란?
 * - 하나의 프로세스(꼭 나와야되는 말)내(자바프로그램)에서 독립(꼭 나와야댐)적으로 동작 할 수 있는 하위 실행단위
 * - 쓰레드 프로그래밍 순서 
 * 1) 쓰레드 클래스를 정의한다.
 * 		방법1) Thread 클래스를 상속받는 방법
 * 		방법2) 이미 특정클래스를 상속받아 놓은 경우, 다중상속이 불가능 하므로 Runnable 인터페이스를 구현(상속)하면 된다.
 * 		방법3) 내부익명클래스로 쓰레드를 사용한다.
 * 2) 원하는 로직을 run()에 작성해 놓아야 한다.
 * 3) 시스템 즉 JVM에게 수행을 맡긴다. (start() 호출)
 */
package basic;

//카운터 증가시키기
public class ThreadTest extends Thread {
	int count;

	// 쓰레드로 실행하고픈 코드를 run()에 작성해놓으면 JVM이 알아서 실행여부를 결정해준다.(개발자는 맡기기만 하면 됨)
	// 쓰레드의 생명주기에서 run()의 닫는 브레이스를 만나면 쓰레드는 소멸한다.. 이때 내부적으로 destory()를 호출하며 죽는다.
	public void run() {
		while (true) {
			count++;
			System.out.println(count);
			try {
				Thread.sleep(1000); // 1000분의 1초까지 표현가능 강제예외사항 =트라이 캐치 해주자
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ThreadTest t = new ThreadTest(); // 쓰레드 생성
		t.start(); // JVM에게 맡기기(Runnable 영역으로 진입)
	}
}
