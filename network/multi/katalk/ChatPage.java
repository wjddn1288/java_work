package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPage extends Page {
	JTextArea area;
	JScrollPane scroll;

	JPanel p_south;
	JTextField t_input;
	JButton bt_send;

	public ChatPage(ClientMain clientMain) {
		super(clientMain);

		// 가운데
		area = new JTextArea();
		scroll = new JScrollPane(area);

		// 남쪽
		p_south = new JPanel();
		t_input = new JTextField();
		bt_send = new JButton("전송");

		scroll.setPreferredSize(new Dimension(380, 400));
		t_input.setPreferredSize(new Dimension(300, 35));

		p_south.add(t_input);
		p_south.add(bt_send);

		add(scroll);
		add(p_south, BorderLayout.SOUTH);

		setVisible(true);
		setPreferredSize(new Dimension(400, 500));

	}
}
