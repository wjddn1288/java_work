package basic;

public class StarThread2 extends Thread{
	String star;
	
	public StarThread2(String star) {
		this.star = star;
	}
	
	public void run() {
		while(true) {
			System.out.println(Thread.currentThread().getName()+star);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
