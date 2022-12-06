package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	//넘겨받은 수가 한자리수이면 앞에 0을 붙이기
	public static String getNumString(int num) {
		String result;
		if(num < 10) {
			result = "0"+num;
			return result;
		}
		result = Integer.toString(num);
		return result;
	}
	
	public static String getExtend(String filename) { //파일 확장자 구하기
		int lastIndex = filename.lastIndexOf(".");
		filename.substring(lastIndex+1,filename.length());
		return filename.substring(lastIndex+1,filename.length());
	}
	
	public static String createFileName(String url) { //문자열로 파일명 리턴
		long time = System.currentTimeMillis();
		String ext = getExtend(url.toString());
		return time+"."+ext;
	}
	
	//비밀번호 암호화 하기
	public static String getConvertedPassword(String pass) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));
			for(int i=0;i<hash.length;i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1)sb.append('0');
				sb.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	/*
	public static void main(String[] args) {
		System.out.println(getConvertedPassword("minzino"));
	}*/
	
}

