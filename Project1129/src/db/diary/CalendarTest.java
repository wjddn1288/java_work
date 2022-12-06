package db.diary;

import java.util.Calendar;

public class CalendarTest {
	public CalendarTest() {
		
	}
	public static void main(String[] args) {
		//날짜 객체는 생성시, 디폴트로 현재 날짜를 정보를 가진다
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.MONTH, 10); //11월
		cal.set(Calendar.DATE, 25); //날짜 조작 
		
		int yy=cal.get(Calendar.YEAR); //현재 연도..
		System.out.println("연도는" + yy);
		
		//현재 월
		int mm=cal.get(Calendar.MONTH);
		System.out.println("월은"+ mm);
		
		//현재 날짜
		int dd=cal.get(Calendar.DATE);
		System.out.println("일은"+dd);
		
		//요일
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek+"요일");
	}
}
