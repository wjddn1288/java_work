package basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import util.StringUtil;

public class StopWatch2 extends JFrame {
	JButton bt;
	JLabel la;
	Thread thread;
	boolean flag = false;
	int sec;
	int min;

	public StopWatch2() {
		bt = new JButton("Start");
		la = new JLabel("00:00", SwingConstants.CENTER);
		thread = new Thread() {
			public void run() {
				while (true) {
					printTime();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();

					}
				}
			}
		};
		thread.start();

		la.setPreferredSize(new Dimension(480, 250));
		la.setFont(new Font("vernada", Font.BOLD, 120));

		setLayout(new FlowLayout());
		add(bt);
		add(la);
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = !flag;
				String title = (flag) ? "Stop" : "Start";
				bt.setText(title);
			}
		});
	}

	public void printTime() {
		if (flag) {
			sec++;
			if (sec >= 60) {
				sec = 0;
				min++;
			}
			la.setText(StringUtil.getNumString(min) + ":" + StringUtil.getNumString(sec));
		}
	}

	public static void main(String[] args) {
		new StopWatch2();
	}
}
