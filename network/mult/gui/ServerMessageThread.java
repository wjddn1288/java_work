package network.mult.gui;

import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

//소켓을 보관하고, 메시지를 주고 받기 위한 스트림을 보유한다~@!!! 그래야 주고 받으니까
public class ServerMessageThread extends Thread {
	Socket socket;
	InputStream is; // 바이트기반 스트림
	InputStreamReader reader; // 문자기반 입력 스트림
	BufferedReader buffr; // 버퍼처리된 문자기반 입력스트림

	OutputStream out; // 바이트기반 출력스트림
	OutputStreamWriter writer; // 문자기반 출력스트림
	BufferedWriter buffw;// 버퍼처리된 문자기반 출력스트림
	MultiServer multiServer;
	
	boolean flag = true;

	public ServerMessageThread(MultiServer multiServer, Socket socket) {
		this.multiServer = multiServer;
		// 넘겨받은 소켓을 이용하여, 스트림 뽑기!
		this.socket = socket; // 소켓 보관해두기
		try {
			// 듣기용 스트림
			is = socket.getInputStream();
			reader = new InputStreamReader(is);
			buffr = new BufferedReader(reader);

			// 말하기용 스트림
			out = socket.getOutputStream();
			writer = new OutputStreamWriter(out);
			buffw = new BufferedWriter(writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 듣는 메서드
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine();

			for (int i = 0; i < multiServer.vec.size(); i++) {// 들어온 사람
				ServerMessageThread smt = multiServer.vec.get(i);
				smt.sendMsg(msg); // 방금 수신한 메시지를 다시 클라이언트에게 보내기(출력) //나도 반목문에 포함
				// 다른 스레드의 sendMsg(msg)도 호출하자!! 다른 스레든 vector에 보관되어 있으므로, 벡터를 접근해보자!!
			}

			// 서버에 로그 출력
			multiServer.area.append(msg + "\n");
		} catch (IOException e) {
			flag = false; // 쓰레드 소멸시키기 위해서...
			multiServer.removeClient(this);
			// e.printStackTrace();
		}

	}

	// 말하는 메서드
	public void sendMsg(String msg) { // 위에 msg가 매개변수로 날라옴!
		try {
			buffw.write(msg + "\n"); // 개행문자라 \를 주지 않으면 무한루프에 빠지게 된다!
			buffw.flush(); // 버퍼 처리된 출력스트림의 경우 초기화
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 개발자는 독립수행하고자 하는 코드를 run에 명시해야 한다
	public void run() {
		while (flag) { // 스레드 소멸을 방지 하기 위함
			listen();
		}
	}
}
