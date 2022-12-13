package network.mult.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.http.WebSocket.Listener;

//클라이언트 메인 쓰레드가 실시간 청취를 위해 무한루프에 빠지는것을 방지해주기 위한 쓰레드
//앞으로 메시지 주고받는 것은 이 객체한테 맡기자!!
public class ClientMessageThread extends Thread {
	Socket socket;

	InputStream is; // 바이트기반 스트림
	InputStreamReader reader; // 문자기반 입력 스트림
	BufferedReader buffr; // 버퍼처리된 문자기반 입력스트림

	OutputStream out; // 바이트기반 출력스트림
	OutputStreamWriter writer; // 문자기반 출력스트림
	BufferedWriter buffw;// 버퍼처리된 문자기반 출력스트림
	MultiClient multiClient;

	public ClientMessageThread(MultiClient multiClient, Socket socket) {
		this.multiClient = multiClient;
		this.socket = socket;

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

	// 서버로부터 전송되어 온 메시지 듣기(입력)
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine();
			// 로그 남기기
			multiClient.area.append(msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 서버에 메시지 전송하기 (출력)
	public void sendMsg(String msg) {

		try {
			buffw.write(msg + "\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			listen(); // 듣는건 실시간으로 계속 해야댐
		}
	}
}
