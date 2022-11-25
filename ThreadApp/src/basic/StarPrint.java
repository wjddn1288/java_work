package basic;

/*쓰레드를 2개이상 생성하여 실행하는 프로그램 작성하기 (코드의 재활용성 때문에)
 * 2개 이상의 쓰레드를 정의한다면, .java로 만들 필요가 있다.. 따라서 쓰레드를 별도의 클래스로 정의하자!!*/
public class StarPrint {
	public static void main(String[] args) {
		StarThread st1 = new StarThread("★"); // 쓰레드 1개 생성
		StarThread st2 = new StarThread("☆");

		st1.start(); // Runnable로 진입시키자!!
		st2.start();
	}
}
