package network.mult.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//텔넷과 웹브라우저는 접속까지는 가능하지만
//대화를 나누기 위한 전용툴이 아니므로 자바로 직접 개발해본다
public class MultiServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 시작버튼
	JTextArea area;
	JScrollPane scroll;
	int port = 9999;
	ServerSocket server;
	Thread serverThread; // 메인 쓰레드가 대기상태에 빠지는 것을 방지하기 위한 대체 쓰레드가 필요한 것임

	// 접속한 클라이언트측 메시지 전용쓰레드를 모아놓을 컬렉션 //서버메시지 쓰레드만 담을꺼야!!
	Vector<ServerMessageThread> vec = new Vector(); // 게임이 아니라서 안정성떄문에 vector를 쓴다

	public MultiServer() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 6);
		bt_start = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 쓰레드 생성
		serverThread = new Thread() {
			public void run() {
				startServer();

			}
		};

		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
				bt_start.setEnabled(false);
			}
		});

	}

	public void startServer() {
		// 서버를 가동한다는 것은 ServerSocket을 생성하고 accept() 동작시키는 것임
		try {
			server = new ServerSocket(port);
			area.append("서버 가동\n"); // 서버가동은 한번만!

			while (true) { // 무한반복문 여러명 들어올수 있게!!
				Socket socket = server.accept(); // 클라이언트가 접속할때까지 대기 상태에 빠진다
				// 소켓은 곧 소멸되므로, 소멸되기 전에 특정 클래스의 인스턴스를 생성하여,
				String ip = socket.getInetAddress().getHostAddress();
				area.append(ip + "님 접속 \n");

				ServerMessageThread smt = new ServerMessageThread(this, socket);
				smt.start(); // 쓰레드 시작

				vec.add(smt); // 출생명부에 기록!!
				area.append("현재" + vec.size() + "명이 채팅에 참여중\n");
				// 클라이언트가 접속할때까지 대기 상태에 빠진다
				// 소켓은 곧 소멸되므로, 소멸되기 전에 특정 클래스의 인스턴스를 생성하여 그 안에 보관해 두자
				// 특히 이 인스턴스는 각각 독립적으로 실행되기 위해 쓰레드로 정의한다!
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 채팅 참여자가 나갈 경우, 서버측 메시지 전용 쓰레드 또한 소멸시켜야하므로, 별도의 메서드로 처리해주자!
	public void removeClient(ServerMessageThread smt) {
		// 누구를 죽일지 매개변수로 받을꺼임!!
		// 넘겨받은 smt를 벡터에서 제거하자!!
		vec.remove(smt);
		area.append("현재 채팅 참여자 수는" + vec.size() + "\n");
	}

	public static void main(String[] args) {
		new MultiServer();
	}

}