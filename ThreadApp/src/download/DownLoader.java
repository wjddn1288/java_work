package download;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class DownLoader extends JFrame {
	JProgressBar bar;
	JButton bt_open, bt_down;
	JFileChooser chooser; // 파일탐색기 관리 객체
	FileInputStream fis = null; // 유저가 선택한 파일과 연결된 입력스트림

	public DownLoader() {
		bar = new JProgressBar();
		bt_open = new JButton("파일찾기");
		bt_down = new JButton("다운로드");
		chooser = new JFileChooser("C:/Users/admin/Downloads");

		// 스타일 적용
		bar.setPreferredSize(new Dimension(580, 90));
		bar.setStringPainted(true);
		bar.setFont(new Font("Dotum",Font.BOLD,45));

		setLayout(new FlowLayout());
		add(bar);
		add(bt_open);
		add(bt_down);

		setSize(600, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openfile();
			}
		});

		bt_down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 다운로드 건 by 건으로 쓰레드를 생성하여 실행하자
				// 이유? main쓰레드를 루프나, 대기상태에 두는건 금지 사항이다.
				Thread thread = new Thread() {
					public void run() {
						download();
					}
				};
				thread.start();
			}
		});
		// 윈도우와 리스너연결
		this.addWindowListener(new WindowAdapter() {
			// 유저가 윈도우창을 닫을때, 프로그램만 종료시키면 되는게 아니라,
			// 스트림도 닫아야 한다.
			public void windowClosing(WindowEvent e) {
				closeStream();
			}
		});

	}

	public void openfile() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// 유저가 선택한 파일을 대상으로, 입력스트림 생성하기!

			File file = chooser.getSelectedFile();// 사용자가 선택한 바로 그 파일 !!!
			try {
				fis = new FileInputStream(file);
				this.setTitle(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// 탐색기에서 선택한 파일과 연결된 입력스트림을 이용하여, 데이터를 읽어
	// 다시 비어 있는 파일에 내려쓰자!!(출력)
	public void download() {

		// 진행상황을 출력하기 위한 정보를 수집하자!!
		// 현재까지 받은 바이트 수/총 바이트*100= 퍼센트
		double total = 0;
		try {
			total = fis.getChannel().size();
			System.out.println("선택한 파일의 총 바이트 수" + total);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int data = -1;
		int readByte = 0; // 현재까지 읽혀진 바이트 수를 누적할 변수

		while (true) {
			try {
				data = fis.read();
				if (data == -1)
					break;
				readByte++;
				
				double ratio=(readByte/total)*100;
				System.out.println(ratio);
				
				//바에 출력
				bar.setValue((int)ratio);
				bar.setString((int)ratio+"% is downloading...");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		bar.setString("다운로드 완료!");
		closeStream();
	}

	// 프로그램에서 사용된 스트림은 모두 닫자!
	public void closeStream() {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new DownLoader();
	}
}
