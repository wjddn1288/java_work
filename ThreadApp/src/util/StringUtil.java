package util;

public class StringUtil {
	// 넘겨받은 숫자가 1자리수이면, 앞에0 붙이기
	// 누군가가 이 메서드를 호출하면, 처리결과를 반환받는다.
	public static String getNumString(int num) {
		String str = null;
		if (num < 10) { // 한자리수
			str = "0" + num; // 05  str=문자열 , 문자열="0"+ num(int) = 문자열 앞에 0을 강제로 붙여줌  
		} else { // 두자리수
			str = Integer.toString(num); // Wrapper 적용 문자열= int형 그래서 문자열로 바꿔줘야한다.
		}
		return str;
	}
	/*
	 * public static void main(String[] args) { String result=getNumString(12);
	 * System.out.println(result); }
	 */
}
