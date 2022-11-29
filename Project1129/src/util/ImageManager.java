package util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {
	// 이미지에 대한 경로를 전달하면, 이미지 객체를 반환하는 메서드 정의
	public Image[] createImages(String[] imgName) {
		Class myClass = this.getClass();// 현재 클래스에 대한 정보를 가진 클래스를 얻는다.. 패키지경로를 통해 얻는다.
		Image[] images = new Image[imgName.length];

		// 자원의 위치를 의미, 패키지경로도 처리가능...
		for (int i = 0; i < imgName.length; i++) {
			URL url = myClass.getClassLoader().getResource(imgName[i]);
			try {
				// 생성된 이미지를 배열에 담기!!!
				images[i] = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}

	/*---------------------------------------------------------------------------------------------------------
	 * path : 클래스 패스내의 이미지 경로
	 * width,height : 크기를 조정하고 싶은 값
	 * ---------------------------------------------------------------------------------------------------------*/
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass = this.getClass(); // 경로가져오기

		// this.getClass로 현재 클래스에 대한 정보를 가져왔고,
		// myClass.getLoader를 통해서 클래스가 위치한 패키지 경로를 갖고있는 객체를 만들었다
		ClassLoader loader = myClass.getClassLoader();
		URL url = loader.getResource(path); // 풀경로 가져오기

		Image scaledImg = null;
		try {
			Image img = ImageIO.read(url);
			scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // api참조
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(scaledImg);

		return icon;
	}
}
