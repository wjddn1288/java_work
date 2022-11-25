package basic;

public class StartPrint2 {
	public static void main(String[] args) {
		StarThread st1= new StarThread("★");
		StarThread st2= new StarThread("☆");
		
		st1.start();
		st2.start();
	}
}
