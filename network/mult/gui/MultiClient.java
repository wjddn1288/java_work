package network.mult.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//텔넷과 웹브라우저는 접속까지는 가능하지만, 대화를 나누기 위한 전용툴이 아니므로
//자바로 직접 개발해본다!!
public class MultiClient extends JFrame {
	JPanel p_north; // 북쪽 패널
	JComboBox<String> box_ip;
	JTextField t_port;
	JButton bt_connect; // 접속버튼
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south; // 남쪽 패널
	JTextField t_input; // 메시지 입력창
	JButton bt_send; // 전송버튼

	int port = 9999;
	ClientMessageThread cmt;

	public MultiClient() {
		p_north = new JPanel();
		box_ip = new JComboBox<>();
		t_port = new JTextField(Integer.toString(port), 6);
		bt_connect = new JButton("접속");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15);
		bt_send = new JButton("전송");

		// 조립
		p_north.add(box_ip);
		p_north.add(t_port);
		p_north.add(bt_connect);

		add(p_north, BorderLayout.NORTH);
		add(scroll);

		p_south.add(t_input);
		p_south.add(bt_send);

		add(p_south, BorderLayout.SOUTH);

		createIp();

		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();

			}
		});

		// 입력창과 리스너연결
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmt.sendMsg(t_input.getText());
					t_input.setText(""); // 입력값 없애기(초기화)
				}
			}
		});
	}

	public void createIp() {
		for (int i = 31; i <= 34; i++) {
			box_ip.addItem("172.30.1." + i);
		}
	}

	// 서버에 접속하기
	public void connect() {
		String ip = (String) box_ip.getSelectedItem();
		int port = Integer.parseInt(t_port.getText());

		try {
			Socket socket = new Socket(ip, port);
			cmt = new ClientMessageThread(this, socket);
			cmt.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiClient();
	}

}
