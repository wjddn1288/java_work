package com.edu.shop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	// 넘겨받은 숫자가 1자리 수이면, 앞에 0 붙이기
	// 누군가가 이 메서드를 호출하면, 처리결과를 반환받는다
	public static String getNumString(int num) {
		String str = null;
		
		if (num < 10) { // 한자리수
			str = "0" + num; // 05
			
		} else { // 두자리수
			str = Integer.toString(num); // int를 String로 변환, Wrapper적용
		}
		
		return str;
	}

	/*--------------------------------------------------------------------
	 * 확장자 추출하여 반환받기
	 * -------------------------------------------------------------------*/
	
	public static String getExtend(String filename) {
		//String filename = "a.a...a..aaaa..a.png";

		int lastIndex = filename.lastIndexOf(".");
		//System.out.println(lastIndex);
		
		// substring : 지정한 범위의 문자열을 반환하는 메소드
		return filename.substring(lastIndex+1, filename.length());
		// split 안쓰는 이유? : .이 여러개일 수 있으니까
		
	}
	
	/*-------------------------------------------------------------
	 *파일명 생성하기 
	 * ------------------------------------------------------------*/
	//파일명 반환하기
	public static String createFilename(String url) {
		//파일명 만들기
		long time=System.currentTimeMillis();
		//확장자 구하기
		String ext=StringUtil.getExtend(url);
		
		return time+"."+ext;
	}
	
	/*-------------------------------------------------------------------
	 * 비밀번호 암호화하기
	 * ------------------------------------------------------------------*/
	
	//자바의 보안과 관련된 기능을 지원하는 api 모여있는 패키지가 java.security이다
	public static String getConvertedPassWord(String pass) {
		StringBuffer hexString=null;
		//암호화 객체
		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-256");
			byte[] hash=digest.digest(pass.getBytes("UTF-8")); //암호화대상이 와야함
			
			//String은 불변이다!! 따라서 그 값이 변경될 수 없다
			//String 객체는 반복문 횟수가 클 때는 절대 누적식을 사용해서는 안된다!!
			//해결책 : 변경가능한 문자열 객체를 지원하는 StringBuffer나 StringBuilder 등을 활용하자
			hexString=new StringBuffer(); //String이 아니라 일반클래스이다 
			for(int i=0;i<hash.length;i++) {
				String hex=Integer.toHexString(0xff& hash[i]); //자릿수를 옮겨서 16진수로 만들어줌
				//System.out.println(hash[i]);
				//System.out.println(hex);
				if(hex.length()==1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
			//System.out.println(hexString.toString());
			//System.out.println(hexString.length());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
//	  public static void main(String[] args) {
//		  String result=getConvertedPassWord("minzino");
//		  System.out.println(result.length());
//	  }
	 
}
