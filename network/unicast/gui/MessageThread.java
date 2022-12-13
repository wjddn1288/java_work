package network.unicast.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//유니캐스팅 이상의 서버에서는, 다수의 클라이언트가 접속을 하므로, 서버측에 생성된 Socket,BufferedReader,BufferedWriter
// 등의 객체는 만일 5사람이 접속하면 이 객체들도 5쌍이 존재하고 보존되어야 한다. 따라서 이를 해결하려면, 접속한 클라이언트 마다 각각
//서버측에 객체를 생성하여, 인스턴스 안에 보관하고 인스턴스내에서 메세지를 주고 받는다... 또한 이 객체들간 서로 간섭받지 않고 독립적으로 
//수행되어야 하므로, 쓰레드로 정의하기에 딱 좋다 !!
public class MessageThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;

	// 메시지 쓰레드 객체는 태어날때, 서버로부터 소켓을 전달받아 메시지 주고받는데 사용하면 된다!!
	public MessageThread(Socket socket) {
		this.socket = socket;

		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 빨대 2개 동시에
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 실 2가닥!! 뽑기!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 듣고
	public void listen() {
		String msg=null;
		try { //클라이언트가 메시지를 전송을하면 서버로 날라가서 쓰레드로 들어온상태!!
			msg=buffr.readLine(); //듣기
			sendMsg(msg); //말하기
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n"); //버퍼처리된 string은 개행이 필요
			buffw.flush(); //버퍼처리된 string 물 내려줘야된다!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() { 
		while(true) { //절대 죽지말아야해 !!
			listen();
		}
	}
}
