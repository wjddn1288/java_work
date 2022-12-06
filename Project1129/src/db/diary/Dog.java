package db.diary;

public class Dog {
	String name="치와와";
	private static Dog instance; //인스턴스를 반드시 적어줘야되는건 아니지만 적어주는게 관습이다.
	
	private Dog() {
	}
	
	public static Dog getInstance() {
		//만일 instance 변수가 null이면 
		//여기서 new를 해주자!!
		if(instance==null) {
			//나는 접근이 가능하므로 인스턴스 생성해버리자!!
			instance=new Dog();
		}
		return instance; 
	}
}
