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
public class EchoServer_BACK extends JFrame {
	JPanel p_north; // 북쪽 패널
	JTextField t_port;
	JButton bt_start; // 접속버튼
	JTextArea area;
	JScrollPane scroll;

	int port = 9999;

	public EchoServer_BACK() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 6); // port가 int니까 형변환시켜줌
		bt_start = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		// 조립
		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new EchoServer_BACK();
	}

}
