package basic;
//메인 쓰레드는 프로그램 운영을 담당한다. 개발자가 생성한 것이 아니라, 시스템이 이미 지원하는 실행부이다.
//GUI 프로그램에서는 만일 메인(main)쓰레드를 무한루프나 대기상태에 두면 이벤트나 그래픽처리가 동작하지 않는다.
//일반적인 응용프로그래밍 분야에서는, 아예 금지사항이다!!

public class ThreadTest2 {
	public static void main(String[] args) {
		//동기방식
		for(int i=0; i<100; i++) {
			System.out.println(i);
		}
		//개발자가 정의한 쓰레드 생성 및 실행
		//비동기 Asyncro~~~
		 Thread t= new Thread() {
			 public void run() {
				 System.out.println("A"); //개발자 쓰레드 실행
			 }
		 };
		 t.start();
		 System.out.println("b"); //메인 쓰레드 실행
		 /*쓰레드의 방식 : 
		  * t.run 을 실행하면 일반 호출이 됨 그렇게 되면 main쓰레드는 개발자 생성메서드로 이동하게됨
		  * 실행부가 2개 만들어진 꼴이다.
		  * 순서를 지키는 방식이 동기방식이다. 쓰레드는 비동기 방식이다. 
		  * 쓰레드 정의: 하나의 프로세스내에서 독립적으로 동작하는 세부(하위) 실행단위*/
	}
	
}
