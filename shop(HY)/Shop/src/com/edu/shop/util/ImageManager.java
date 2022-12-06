package com.edu.shop.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageManager {
	
	//이미지에 대한 경로를 전달하면, 이미지 객체를 반환하는 메서드 정의
	// 메소드 선언 : 이미지 경로를 전달하면(=매개변수) 이에 대칭되는 이미지 객체를 반환함(=return)
	// 매개변수 : 이미지경로, 가져올 것을 배열로 선언해줄 예정이니 배열. String형. imgName : 이미지 경로 넣을 예정
	// 반환형 : 복수의 이미지, 배열로 반환해줄 예정이니 배열. Image형
	public Image[] createImages (String[] imgName) {
		
		 //현재 클래스에 대한 정보를 가진 클래스를 얻는다
		// final class Class<T> : 클래스 클래스. 클래스와 인터페이스의 정보를 지님.
		// Object 클래스를 상속받기 때문에 Object 클래스가 지닌 getClass() 사용 가능함.
		// public final Class<T> getClass() : 클래스의 모든 정보(그중에 경로도 포함됨)를 가져옴.
		Class myClass=this.getClass(); // this = ImageManager
		// this를 찾기 어려우면 가장 가까이 정의되어 있는 클래스(익명객체도)를 확인해 보자. {}도 함께 확인해 보자. this 아닐수도 있지만
		// 확률이 높다.

		// 경로 배열에 넣기
		// 자바에서 배열 선언 시에는 길이를 반드시 정해줘야 함. 길이만 먼저 정해주고 값은 나중에 넣어주겠다.
		Image[] images=new Image[imgName.length];
		
		for(int i=0;i<imgName.length;i++) {
			//URL url=null; //자원의 위치를 의미, 패키지경로도 처리가능
			// 배열에서 경로 빼기(myclass에서 정보 빼기)
			// public ClassLoader getClassLoader() : class loader를 반환함
			// public URL getResource(String name) : 매개변수의 리소스(이미지, 오디오, 텍스트 등)를 반환함
			
			URL url=myClass.getClassLoader().getResource(imgName[i]);
			// 메소드 뒤에 메소드가 올 수 있는 이유 myClass.getClassLoader()의 리턴값은 클래스 그래서 클래스 뒤에
			// getResource(imgName[i])가 왔다고 생각할 수 있음

			try {
				//생성된 이미지를 배열에 담기
				// 이미지 인스턴스 생성하기
				// final class ImageIO
				// public static BufferedImage read(URL input) : 이미지판독기(ImageReader)로 전달된 이미지를
				// 반환하여 생성함

				images[i]=ImageIO.read(url); //이미지얻기,이미지 생성하여 배열에 담기. trycatch문 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
	
	/*------------------------------------------------------------
	 path : 클래스 패스내의 이미지 경로
	 width, height : 크기를 조정하고 싶은 값
	 -------------------------------------------------------------*/
	//버튼메뉴에 사용할 아이콘 생성하기
	// 매개변수 : String path(클래스 패스 내의 이미지 경로), int width, int height(크기를 조정하고 싶은 값)
	public ImageIcon getIcon(String path, int width, int height) {
		// 이미지 경로로 얻기
		Class myClass = this.getClass();
		ClassLoader loader=myClass.getClassLoader(); //패키지경로를 얻어올 수 있는 객체
		URL url=loader.getResource(path);
		
		// 이미지 경로로 아이콘 생성하기
		Image scaledImg=null; // {}밖에서도 쓸 수 있도록 빼줌
		try {
			//이미지 크기를 조절하기
			Image img=ImageIO.read(url); // 이미지 생성하기
			scaledImg=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon icon=new ImageIcon(scaledImg);
		
		return icon;
		}
		
		/*-------------------------------------------------------------
		 * 이미지 얻어오기
		 * Image.SCALE_SMOOTH : 부드러움 강조
		 * -------------------------------------------------------------*/
		public Image getImage(String path, int width, int height) { // 메소드 선언 : 이미지 얻어오기(Image로 반환)
			// path에 담긴 정보를 URL에 담기
			URL url=this.getClass().getClassLoader().getResource(path);
			Image image=null;
			try {
				// URL에 담긴 정보를 바탕으로 이미지 생성하기
				image=ImageIO.read(url);
				// 이미지 크기 조절하기
				image=image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 크기까지 조절된 이미지 반환하기
			return image;
		}
		

}
