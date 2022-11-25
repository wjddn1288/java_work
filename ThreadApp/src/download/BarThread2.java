package download;

import javax.swing.JProgressBar;

public class BarThread2 extends Thread {
	JProgressBar jprogressBar;
	boolean flag=true;
	int n;
	int velX;
	int time;
	
	public BarThread2(JProgressBar jprogressBar,int velX,int time) {
		this.jprogressBar = jprogressBar;
		this.velX=velX;
		this.time=time;
	}
	public void printValue() {
		n+= velX;
		jprogressBar.setValue(n);
		if(n>=100) {
			flag=false;
		}
	}
	
	public void run() {
		while(flag) {
			printValue();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	public static void main(String[]args) {
		
	}
}
