package util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

//구글이 제공해주는 구글메일서버를 이용한, 자바 메일 발송 프로그램
public class MailSender {
	String myAccountMail = "wjddn7728@gmail.com";// 내가 사용중인 Gmail
	String password = "rwqtdcolkfdsvdzo";

	public void sendMail(String to) {
		String subject = "주문해주셔서 감사합니다."; // 메일제목
		String from = "wjddn7728@naver.com"; // 보낸 사람 이메일
		String fromName = "쇼핑몰 관리자";// 보낸사람
		//String to = "wjddn7728@gmail.com"; // 받을 사람 이메일,(콤마로 다수의 메일 가능)

		StringBuffer content = new StringBuffer(); // 문자열을 더 쌓을꺼기 때문에!!
		content.append("<h1>고객님 안녕하세요, 쇼핑닷컴입니다.</h1>");
		content.append("<p>가상계좌 입금이 확인되었습니다.</p>");

		// 메일 설정 걍 암기해야댐
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // mail.smtp 보낼때 쓰는거 Gmail 서버 사용
		props.put("mail.smtp.port", "587");// 포트번호 설정
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // TLS 사용

		// 구글 메일을 사용하기 위한 인증절차
		// java 메일 api에서 지원하는 Session 객체 생성
		Session mailSession = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, password);
			}
		});

		// 보내기 위한 셋팅
		MimeMessage message = new MimeMessage(mailSession);

		// 보내는 사람 받는 사람 정보 셋팅
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); // 메일 제목 설정
			//HTML 형식으로 보냄 
			message.setContent(content.toString(),"text/html;charset=UTF-8");
			message.setSentDate(new Date()); //보낸 날짜
			
			//전송하기
			Transport trans=mailSession.getTransport("smtp");
			trans.connect(myAccountMail,password);
			trans.send(message,message.getAllRecipients());
			trans.close();
			System.out.println("메일 발송 성공 ^.^");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	/*
	 * public static void main(String[] args) { MailSender sender=new MailSender();
	 * sender.sendMail(); }
	 */
}
