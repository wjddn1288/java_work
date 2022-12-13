package network.echo.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//텔넷과 웹브라우저는 접속까지는 가능하지만, 대화를 나누기 위한 전용툴이 아니므로
//자바로 직접 개발해본다!!
public class EchoClient_BACK extends JFrame {
	JPanel p_north; // 북쪽 패널
	JComboBox<String> box_ip;
	JTextField t_port;
	JButton bt_connect; // 접속버튼
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south; // 남쪽 패널
	JTextField t_input; // 메시지 입력창
	JButton bt_send; // 전송버튼
	int port=9999;
	
	public EchoClient_BACK() {
		p_north = new JPanel();
		box_ip = new JComboBox<>();
		t_port = new JTextField(Integer.toString(port),6);
		bt_connect = new JButton("접속");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15);
		bt_send = new JButton("전송");

		// 조립
		p_north.add(box_ip);
		p_north.add(t_port);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		p_south.add(t_input);
		p_south.add(bt_send);

		add(p_south, BorderLayout.SOUTH);
		
		createIp();
		
		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void createIp() {
		for (int i = 3; i <= 100; i++) {
			box_ip.addItem("172.30.1." + i);
		}
	}

	public static void main(String[] args) {
		new EchoClient_BACK();
	}

}
